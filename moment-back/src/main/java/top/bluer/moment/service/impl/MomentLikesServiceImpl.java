package top.bluer.moment.service.impl;

import org.springframework.transaction.annotation.Transactional;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentLikes;
import top.bluer.moment.entity.vo.MomentUserInfoVo;
import top.bluer.moment.mapper.MomentLikesMapper;
import top.bluer.moment.mapper.MomentUserMapper;
import top.bluer.moment.service.MomentLikesService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import top.bluer.moment.utils.RandomUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 片刻点赞表(MomentLikes)表服务实现类
 *
 * @author bluer
 * @since 2021-12-23 17:56:52
 */
@Service("momentLikesService")
public class MomentLikesServiceImpl implements MomentLikesService {
    @Resource
    private MomentLikesMapper momentLikesMapper;
    @Resource
    private SaTokenConfig saTokenConfig;
    @Resource
    private MomentUserMapper momentUserMapper;

    /**
     * @description: 点赞/消赞
     * @date: 2021/12/23 18:06
     * @codes: 扁鹊
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer[] like(String dynamicId) {
        String userId = saTokenConfig.getUser().getUserId();
        MomentLikes like = momentLikesMapper.getInfo(MomentLikes.builder().userId(userId).dynamicId(dynamicId).build());
        Integer[] integers = new Integer[]{0, 0};
        if (Objects.isNull(like)) {
            momentLikesMapper.insert(MomentLikes
                    .builder()
                    .id(RandomUtil.getRanId())
                    .dynamicId(dynamicId)
                    .userId(userId)
                    .time(new Date())
                    .status(0)
                    .build());
            integers[0] = 0;
        } else {
            if (like.getStatus() == 1) {
                like.setStatus(0);
                momentLikesMapper.update(like);
                integers[0] = 0;
            } else {
                momentLikesMapper.deleteById(like.getId());
                integers[0] = 1;
            }
        }
        int count = momentLikesMapper.count(MomentLikes.builder().dynamicId(dynamicId).status(0).build());
        integers[1] = count;
        return integers;
    }

    /**
     * @description: 动态点赞列表
     * @date: 2021/12/26 13:58
     * @codes: 扁鹊
     **/
    @Override
    public List<MomentUserInfoVo> likeList(String dynamicId) {
        List<MomentLikes> list = momentLikesMapper.list(MomentLikes.builder().dynamicId(dynamicId).status(0).build());
        List<String> collect = list.stream().map(MomentLikes::getUserId).collect(Collectors.toList());
        if (collect.size() <= 0) {
            return new ArrayList<>();
        }
        return momentUserMapper.likeList(collect);
    }
}
