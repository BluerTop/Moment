package top.bluer.moment.service;

import top.bluer.moment.entity.MomentFollow;

import java.util.List;

/**
 * 片刻关注表(MomentFollow)表服务接口
 *
 * @author bluer
 * @since 2021-12-25 11:29:35
 */
public interface MomentFollowService {

    /**
     * @description: 通过ID查询单条数据
     * @date: 2021/12/25 11:59
     * @codes: 扁鹊
     **/
    MomentFollow queryById(String id);

    /**
     * @description: 列表查询
     * @date: 2021/12/25 11:52
     * @codes: 扁鹊
     **/
    List<MomentFollow> list(MomentFollow momentFollow);

    /**
     * @description: 新增数据
     * @date: 2021/12/25 11:45
     * @codes: 扁鹊
     **/
    MomentFollow insert(MomentFollow momentFollow);

    /**
     * @description: 通过主键删除数据
     * @date: 2021/12/25 11:57
     * @codes: 扁鹊
     **/
    boolean deleteById(String id);

    /**
     * @description: 删除数据
     * @date: 2021/12/25 14:44
     * @codes: 扁鹊
     **/
    boolean deleteByFollowedId(MomentFollow follow);
}
