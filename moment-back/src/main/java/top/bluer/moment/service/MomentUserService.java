package top.bluer.moment.service;

import com.github.pagehelper.PageInfo;
import top.bluer.moment.entity.MomentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import top.bluer.moment.entity.vo.MomentUserInfoVo;

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
    MomentUser queryById(String userId);

    /**
     * @description: 用户详细信息
     * @date: 2021/12/26 14:50
     * @codes: 扁鹊
     **/
    MomentUserInfoVo getMomentUserInfo();

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
     * @description: 推荐用户列表
     * @date: 2021/12/25 12:19
     * @codes: 扁鹊
     **/
    PageInfo<MomentUserInfoVo> recommend(MomentUser momentUser, Integer page, Integer size);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(String userId);
}
