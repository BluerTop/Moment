package top.bluer.moment.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import lombok.SneakyThrows;
import top.bluer.moment.common.exception.BizException;
import top.bluer.moment.common.redis.CachePackage;
import top.bluer.moment.common.redis.MomentRedis;
import top.bluer.moment.mapper.MomentAccountMapper;
import top.bluer.moment.entity.MomentAccount;
import top.bluer.moment.entity.MomentSms;
import top.bluer.moment.mapper.MomentSmsMapper;
import top.bluer.moment.service.MomentSmsService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import top.bluer.moment.utils.JasyptUtil;
import top.bluer.moment.utils.RandomUtil;
import top.bluer.moment.utils.TemplateUtil;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

import static top.bluer.moment.common.redis.CachePackage.*;

/**
 * (MomentSms)表服务实现类
 *
 * @author bluer
 * @since 2021-11-26 17:21:02
 */
@Service("momentSmsService")
public class MomentSmsServiceImpl implements MomentSmsService {
    public static final String SEND_SUCCESS = "短信发送成功";
    public static final String SEND_FAIL = "短信发送失败";
    public static final String SEND_MUCH = "短信发送次数过多";
    public static final String VALIDATE_SUCCESS = "短信校验成功";
    public static final String VALIDATE_FAIL = "短信校验失败";

    // 短信类型
    public static final String SMS_C = "C";
    public static final String SMS_P = "P";
    public static final String SMS_M = "M";
    public static final String SMS_S = "S";

    @Resource
    private MomentSmsMapper momentSmsMapper;

    @Resource
    private MomentAccountMapper momentAccountMapper;

    @Resource
    private MomentRedis momentRedis;

    @Resource
    private JasyptUtil jasyptUtil;

    /**
     * @description: 通用短信发送
     * @date: 2021/11/26 19:46
     * codes: 扁鹊
     **/
    @Override
    @SneakyThrows
    public void sendMessages(String phone, String describe) {
        String value;
        // 短信时效校验
        this.smsSendAgingCheck(phone, describe);
        if (describe.equals(SMS_C)) {
            // 通用短信验证码发送
            String smsCaptcha = TemplateUtil.processTemplate(SMS_CAPTCHA, new HashMap<String, Object>(2) {{
                put("phone", phone);
                put("describe", describe);
            }});
            // 获取短信缓存
            String code = momentRedis.get(smsCaptcha);
            if (!Objects.isNull(code)) {
                return;
            }
            value = RandomUtil.getRandom(6);
            // code存入缓存
            momentRedis.set(CachePackage.builder().key(smsCaptcha).value(value).expire(Duration.ofMinutes(5L)).build());
        } else if (describe.equals(SMS_P)) {
            // 通用短信密码发送
            MomentAccount momentAccount = momentAccountMapper.queryByCondition(MomentAccount.builder().accountPhone(phone).build());
            // 密码解密
            value = jasyptUtil.decrypt(momentAccount.getAccountPassword());
        } else {
            return;
        }

        // 获取短信数据
        MomentSms momentSms = momentSmsMapper.queryByDescribe(describe);
        // 配置阿里云信息
        Config config = new Config()
                .setAccessKeyId(jasyptUtil.decrypt(momentSms.getAccessKeyId()))
                .setAccessKeySecret(jasyptUtil.decrypt(momentSms.getAccessKeySecret()));
        config.endpoint = momentSms.getEndpoint();
        Client client = new Client(config);
        // 发送短信
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(momentSms.getSignName())
                .setTemplateCode(momentSms.getTemplateCode())
                .setTemplateParam(TemplateUtil.processTemplate(momentSms.getTemplateParam(), new HashMap<String, Object>(1) {{
                    put(momentSms.getVariableName(), value);
                }}));
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        if (!sendSmsResponse.body.code.equals("OK")) {
            throw new BizException(SEND_FAIL);
        }

        String day = TemplateUtil.processTemplate(SMS_CAPTCHA_DAY, new HashMap<String, Object>(2) {{
            put("phone", phone);
            put("describe", describe);
        }});
        Integer days = momentRedis.get(day);
        String hour = TemplateUtil.processTemplate(SMS_CAPTCHA_HOUR, new HashMap<String, Object>(2) {{
            put("phone", phone);
            put("describe", describe);
        }});
        Integer hours = momentRedis.get(hour);
        momentRedis.set(CachePackage.builder().key(day).value(Objects.isNull(days) ? 0 : days + 1).expire(Duration.ofDays(1L)).build());
        momentRedis.set(CachePackage.builder().key(hour).value(Objects.isNull(hours) ? 0 : hours + 1).expire(Duration.ofHours(1L)).build());
    }

    /**
     * @description: 短信发送时效校验 同一手机号同一类型验证码，一天最多发送5次，一小时最多发送3次
     * @date: 2021/11/25 16:26
     * codes: 扁鹊
     **/
    private void smsSendAgingCheck(String phone, String describe) {
        // 校验一天发送次数
        String day = TemplateUtil.processTemplate(SMS_CAPTCHA_DAY, new HashMap<String, Object>(2) {{
            put("phone", phone);
            put("describe", describe);
        }});
        Integer days = momentRedis.get(day);
        if (!Objects.isNull(days) && days >= 5) {
            throw new BizException(SEND_MUCH);
        }
        // 校验一小时发送次数
        String hour = TemplateUtil.processTemplate(SMS_CAPTCHA_HOUR, new HashMap<String, Object>(2) {{
            put("phone", phone);
            put("describe", describe);
        }});
        Integer hours = momentRedis.get(hour);
        if (!Objects.isNull(hours) && hours >= 3) {
            throw new BizException(SEND_MUCH);
        }
    }

    /**
     * @description: 校验短信验证码
     * @date: 2021/11/26 19:55
     * codes: 扁鹊
     **/
    @Override
    public void validateMessageCode(String phone, String code, String describe) {
        String smsCaptcha = TemplateUtil.processTemplate(SMS_CAPTCHA, new HashMap<String, Object>(2) {{
            put("phone", phone);
            put("describe", describe);
        }});
        String cache = momentRedis.get(smsCaptcha);
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(cache) || !cache.equals(code)) {
            throw new BizException(VALIDATE_FAIL);
        }
    }

    /**
     * 通过ID查询单条数据
     *
     * @param aliId 主键
     * @return 实例对象
     */
    @Override
    public MomentSms queryById(Integer aliId) {
        return momentSmsMapper.queryById(aliId);
    }

    /**
     * 分页查询
     *
     * @param momentSms   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<MomentSms> queryByPage(MomentSms momentSms, PageRequest pageRequest) {
        long total = momentSmsMapper.count(momentSms);
        return new PageImpl<>(momentSmsMapper.queryAllByLimit(momentSms, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param momentSms 实例对象
     * @return 实例对象
     */
    @Override
    public MomentSms insert(MomentSms momentSms) {
        momentSmsMapper.insert(momentSms);
        return momentSms;
    }

    /**
     * 修改数据
     *
     * @param momentSms 实例对象
     * @return 实例对象
     */
    @Override
    public MomentSms update(MomentSms momentSms) {
        momentSmsMapper.update(momentSms);
        return this.queryById(momentSms.getAliId());
    }

    /**
     * 通过主键删除数据
     *
     * @param aliId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer aliId) {
        return momentSmsMapper.deleteById(aliId) > 0;
    }
}
