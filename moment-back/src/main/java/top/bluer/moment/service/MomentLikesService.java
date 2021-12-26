package top.bluer.moment.service;

import top.bluer.moment.entity.MomentLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import top.bluer.moment.entity.vo.MomentUserInfoVo;

import java.util.List;

/**
 * 片刻点赞表(MomentLikes)表服务接口
 *
 * @author bluer
 * @since 2021-12-23 17:56:52
 */
public interface MomentLikesService {
    /**
     * @description: 点赞/消赞
     * @date: 2021/12/23 18:05
     * @codes: 扁鹊
     **/
    Integer[] like(String dynamicId);

    /**
     * @description: 动态点赞列表
     * @date: 2021/12/26 13:57
     * @codes: 扁鹊
     **/
    List<MomentUserInfoVo> likeList(String dynamicId);
}
