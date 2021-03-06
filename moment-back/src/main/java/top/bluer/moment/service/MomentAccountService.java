package top.bluer.moment.service;

import top.bluer.moment.entity.MomentAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 账户表(MomentAccount)表服务接口
 *
 * @author bluer
 * @since 2021-11-26 14:53:18
 */
public interface MomentAccountService {

    /**
     * 通过ID查询单条数据
     *
     * @param accountId 主键
     * @return 实例对象
     */
    MomentAccount queryById(Integer accountId);

    /**
     * @description: 通过用户条件查询单条数据
     * @date: 2021/11/27 13:51
     * codes: 扁鹊
     **/
    MomentAccount queryByCondition(MomentAccount momentAccount);

    /**
     * 分页查询
     *
     * @param momentAccount 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    Page<MomentAccount> queryByPage(MomentAccount momentAccount, PageRequest pageRequest);

    /**
    * @description: 新增数据
    * @date: 2021/11/27 23:19
    * codes: 扁鹊
    **/
    MomentAccount insert(MomentAccount momentAccount);

    /**
    * @description: 修改数据
    * @date: 2021/11/27 23:19
    * codes: 扁鹊
    **/
    MomentAccount update(MomentAccount momentAccount);

    /**
     * 通过主键删除数据
     *
     * @param accountId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer accountId);

}
