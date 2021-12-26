package top.bluer.moment.service;

import top.bluer.moment.entity.MomentSms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (MomentSms)表服务接口
 *
 * @author bluer
 * @since 2021-11-26 17:21:02
 */
public interface MomentSmsService {

    /**
    * @description: 通用短信发送
    * @date: 2021/11/26 19:46
    * codes: 扁鹊
    **/
    void sendMessages(String phone, String describe);

    /**
    * @description: 校验短信验证码
    * @date: 2021/11/26 19:55
    * codes: 扁鹊
    **/
    void validateMessageCode(String phone, String code,String describe);

    /**
     * 通过ID查询单条数据
     *
     * @param aliId 主键
     * @return 实例对象
     */
    MomentSms queryById(String aliId);

    /**
     * 分页查询
     *
     * @param momentSms   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<MomentSms> queryByPage(MomentSms momentSms, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param momentSms 实例对象
     * @return 实例对象
     */
    MomentSms insert(MomentSms momentSms);

    /**
     * 修改数据
     *
     * @param momentSms 实例对象
     * @return 实例对象
     */
    MomentSms update(MomentSms momentSms);

    /**
     * 通过主键删除数据
     *
     * @param aliId 主键
     * @return 是否成功
     */
    boolean deleteById(String aliId);

}
