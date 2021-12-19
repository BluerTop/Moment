package top.bluer.moment.service;

import top.bluer.moment.entity.MomentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户表(MomentUser)表服务接口
 *
 * @author bluer
 * @since 2021-11-26 14:53:20
 */
public interface MomentUserService {

    /**
     * @description: 通过ID查询单条数据
     * @date: 2021/11/27 13:49
     * codes: 扁鹊
     **/
    MomentUser queryById(Integer userId);

    /**
     * @description: 修改数据
     * @date: 2021/11/28 12:09
     * codes: 扁鹊
     **/
    MomentUser update(MomentUser momentUser);

    /**
     * @description: 新增数据
     * @date: 2021/11/28 12:09
     * codes: 扁鹊
     **/
    MomentUser insert(MomentUser momentUser);

    /**
     * 分页查询
     *
     * @param momentUser  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<MomentUser> queryByPage(MomentUser momentUser, PageRequest pageRequest);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

}
