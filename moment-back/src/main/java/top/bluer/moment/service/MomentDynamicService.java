package top.bluer.moment.service;

import com.github.pagehelper.PageInfo;
import top.bluer.moment.entity.MomentDynamic;
import top.bluer.moment.entity.vo.MomentDynamicAndUserVo;
import top.bluer.moment.entity.dto.MomentDynamicDto;
import java.util.List;

/**
 * 片刻动态表(MomentDynamic)表服务接口
 *
 * @author bluer
 * @since 2021-12-02 17:52:33
 */
public interface MomentDynamicService {
    /**
     * @description: 发布动态
     * @date: 2021/12/2 18:02
     * codes: 扁鹊
     **/
    MomentDynamic insert(MomentDynamicDto momentDynamicDto);

    /**
     * @description: 删除动态
     * @date: 2021/12/2 18:08
     * codes: 扁鹊
     **/
    boolean delOrRec(String id, String type);

    /**
     * @description: 分页查询 公共
     * @date: 2021/12/3 17:23
     * codes: 扁鹊
     **/
    PageInfo<MomentDynamicAndUserVo> queryByPage(Integer page, Integer size);

    /**
     * @description: 查询我的动态列表
     * @date: 2021/12/3 17:23
     * codes: 扁鹊
     **/
    List<MomentDynamicAndUserVo> queryByMy(String index);

    /**
    * @description: 根据id获取单条动态信息
    * @date: 2021/12/3 17:38
    * codes: 扁鹊
    **/
    MomentDynamicAndUserVo queryById(String id);

    /**
     * 修改数据
     *
     * @param momentDynamic 实例对象
     * @return 实例对象
     */
    MomentDynamic update(MomentDynamic momentDynamic);
}
