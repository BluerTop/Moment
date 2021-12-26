package top.bluer.moment.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import top.bluer.moment.common.exception.BizException;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentAccount;
import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.entity.dto.ChangePassDto;
import top.bluer.moment.entity.dto.LoginParamsDto;
import top.bluer.moment.service.MomentAccountService;
import top.bluer.moment.service.MomentAuthService;
import top.bluer.moment.service.MomentSmsService;
import top.bluer.moment.service.MomentUserService;
import top.bluer.moment.utils.JasyptUtil;
import top.bluer.moment.utils.RandomUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @program: moment-back
 * @description: 用户授权服务实现类
 * @author: bluer
 * @date: 2021-11-27 13:17
 * codes: 扁鹊
 **/
@Service("momentAuthService")
public class MomentAuthServiceImpl implements MomentAuthService {
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_FAILED = "登录失败";
    public static final String LOGIN_FAILED_PC = "登录失败，手机号或验证码错误";
    public static final String LOGIN_FAILED_PP = "登录失败，手机号或密码错误";
    public static final String LOGIN_FAILED_MP = "登录失败，邮箱或密码错误";

    public static final String PASSWORD_A = "原密码错误";

    @Resource
    private MomentUserService momentUserService;

    @Resource
    private MomentAccountService momentAccountService;

    @Resource
    private MomentSmsService momentSmsService;

    @Resource
    private JasyptUtil jasyptUtil;

    @Resource
    private SaTokenConfig saTokenConfig;

    /**
     * @description: 登录
     * @date: 2021/11/27 13:19
     * codes: 扁鹊
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaTokenInfo login(LoginParamsDto loginParamsDto) {
        // 登录类型：PC-手机号+验证码，MP-邮箱+密码，PP-手机号+密码
        verifyLoginParams(loginParamsDto);
        String loginType = loginParamsDto.getLoginType();
        switch (loginType) {
            case "PC": {
                MomentAccount momentAccount = this.loginPC(loginParamsDto);
                StpUtil.login(momentAccount.getAccountPhone(), SaLoginModel.create().setIsLastingCookie(true).setDevice("P"));
                break;
            }
            case "PP": {
                MomentAccount momentAccount = this.loginPP(loginParamsDto);
                StpUtil.login(momentAccount.getAccountPhone(), SaLoginModel.create().setIsLastingCookie(true).setDevice("P"));
                break;
            }
            case "MP": {
                MomentAccount momentAccount = this.loginMP(loginParamsDto);
                StpUtil.login(momentAccount.getAccountMail(), SaLoginModel.create().setIsLastingCookie(true).setDevice("M"));
                break;
            }
            default:
                throw new BizException(LOGIN_FAILED);
        }
        return StpUtil.getTokenInfo();
    }

    /**
     * @description: 修改密码
     * @date: 2021/11/27 23:07
     * codes: 扁鹊
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(ChangePassDto changePassDto) {
        MomentAccount momentAccount = saTokenConfig.getAccount();
        String password = jasyptUtil.decrypt(momentAccount.getAccountPassword());
        if (!password.equals(changePassDto.getPasswordA())) {
            throw new BizException(PASSWORD_A);
        }
        momentAccount.setAccountPassword(jasyptUtil.encrypt(changePassDto.getPasswordB()));
        momentAccountService.update(momentAccount);
    }

    /**
     * @description: 登录-PC:手机号+验证码
     * @date: 2021/11/27 13:22
     * codes: 扁鹊
     * 供 login(LoginDto loginDto) 调用
     **/
    private MomentAccount loginPC(LoginParamsDto loginParamsDto) {
        // 校验验证码
        momentSmsService.validateMessageCode(loginParamsDto.getAccountPhone(), loginParamsDto.getAccountCode(), loginParamsDto.getDescribe());
        MomentAccount momentAccount = momentAccountService.queryByCondition(MomentAccount.builder().accountPhone(loginParamsDto.getAccountPhone()).build());
        if (Objects.isNull(momentAccount)) {
            // 未注册
            MomentUser momentUser = momentUserService.insert(MomentUser
                    .builder()
                    .userId(RandomUtil.getRanId())
                    .userName("片刻" + RandomUtil.getRandomString(6))
                    .userGender("K")
                    .userBirthday(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                    .userSign("快来\"武装\"一下自己吧...")
                    .userDate(new Date())
                    .userStatus("E")
                    .build());
            momentAccount = momentAccountService.insert(MomentAccount
                    .builder()
                    .accountId(RandomUtil.getRanId())
                    .accountType("P")
                    .accountPhone(loginParamsDto.getAccountPhone())
                    .accountPassword(jasyptUtil.encrypt(RandomUtil.getRandomString(10)))
                    .userId(momentUser.getUserId())
                    .accountStatus("E")
                    .build());
            momentSmsService.sendMessages(loginParamsDto.getAccountPhone(), "P");
        }
        return momentAccount;
    }

    /**
     * @description: 登录-MP:邮箱+密码
     * @date: 2021/11/27 16:14
     * codes: 扁鹊
     * 供 login(LoginDto loginDto) 调用
     **/
    private MomentAccount loginMP(LoginParamsDto loginParamsDto) {
        // 获取账户信息
        MomentAccount momentAccount = momentAccountService.queryByCondition(MomentAccount.builder().accountMail(loginParamsDto.getAccountMail()).build());
        if (Objects.isNull(momentAccount) || !jasyptUtil.decrypt(momentAccount.getAccountPassword()).equals(loginParamsDto.getAccountPassword())) {
            throw new BizException(LOGIN_FAILED_MP);
        }
        return momentAccount;
    }

    /**
     * @description: 登录-PP:手机号+密码
     * @date: 2021/11/27 17:13
     * codes: 扁鹊
     * 供 login(LoginDto loginDto) 调用
     **/
    private MomentAccount loginPP(LoginParamsDto loginParamsDto) {
        // 获取账户信息
        MomentAccount momentAccount = momentAccountService.queryByCondition(MomentAccount.builder().accountPhone(loginParamsDto.getAccountPhone()).build());
        if (Objects.isNull(momentAccount) || !jasyptUtil.decrypt(momentAccount.getAccountPassword()).equals(loginParamsDto.getAccountPassword())) {
            throw new BizException(LOGIN_FAILED_PP);
        }
        return momentAccount;
    }

    /**
     * @description: 校验登录参数
     * @date: 2021/11/27 16:22
     * codes: 扁鹊
     * 供 login(LoginDto loginDto) 调用
     **/
    private void verifyLoginParams(LoginParamsDto loginParamsDto) {
        String loginType = loginParamsDto.getLoginType();
        if (loginType.equals("PC") && (StringUtils.isBlank(loginParamsDto.getAccountPhone()) || StringUtils.isBlank(loginParamsDto.getAccountCode()) || StringUtils.isBlank(loginParamsDto.getDescribe()))) {
            throw new BizException(LOGIN_FAILED_PC);
        }
        if (loginType.equals("PP") && (StringUtils.isBlank(loginParamsDto.getAccountPhone()) || StringUtils.isBlank(loginParamsDto.getAccountPassword()))) {
            throw new BizException(LOGIN_FAILED_PP);
        }
        if (loginType.equals("MP") && (StringUtils.isBlank(loginParamsDto.getAccountMail()) || StringUtils.isBlank(loginParamsDto.getAccountPassword()))) {
            throw new BizException(LOGIN_FAILED_MP);
        }
    }
}
