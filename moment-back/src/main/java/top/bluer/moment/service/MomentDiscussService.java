package top.bluer.moment.service;

import top.bluer.moment.entity.MomentDiscuss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import top.bluer.moment.entity.dto.MomentDiscussDto;
import top.bluer.moment.entity.vo.MomentDiscussAndUserVo;

import java.util.List;

/**
 * 片刻留言表(MomentDiscuss)表服务接口
 *
 * @author bluer
 * @since 2021-12-05 18:14:21
 */
public interface MomentDiscussService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MomentDiscuss queryById(Integer id);

    /**
     * @description: 根据动态id获取所有留言
     * @date: 2021/12/5 19:25
     * codes: 扁鹊
     **/
    List<MomentDiscussAndUserVo> getInfos(String dynamicId);

    /**
    * @description: 发布留言
    * @date: 2021/12/5 18:21
    * codes: 扁鹊
    **/
    MomentDiscuss insert(MomentDiscussDto momentDiscussDto);

    /**
     * 修改数据
     *
     * @param momentDiscuss 实例对象
     * @return 实例对象
     */
    MomentDiscuss update(MomentDiscuss momentDiscuss);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
