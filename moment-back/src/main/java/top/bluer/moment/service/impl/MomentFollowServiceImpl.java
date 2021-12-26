package top.bluer.moment.service.impl;

import org.apache.commons.lang.StringUtils;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentFollow;
import top.bluer.moment.mapper.MomentFollowMapper;
import top.bluer.moment.service.MomentFollowService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import top.bluer.moment.utils.RandomUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 片刻关注表(MomentFollow)表服务实现类
 *
 * @author bluer
 * @since 2021-12-25 11:29:35
 */
@Service("momentFollowService")
public class MomentFollowServiceImpl implements MomentFollowService {
    @Resource
    private MomentFollowMapper momentFollowMapper;
    @Resource
    private SaTokenConfig saTokenConfig;

    /**
     * @description: 通过ID查询单条数据
     * @date: 2021/12/25 11:59
     * @codes: 扁鹊
     **/
    @Override
    public MomentFollow queryById(String id) {
        return momentFollowMapper.queryById(id);
    }

    /**
     * @description: 列表查询
     * @date: 2021/12/25 11:53
     * @codes: 扁鹊
     **/
    @Override
    public List<MomentFollow> list(MomentFollow momentFollow) {
        if (StringUtils.isBlank(momentFollow.getFollowersId())) {
            String userId = saTokenConfig.getUser().getUserId();
            momentFollow.setFollowersId(userId);
        }
        return momentFollowMapper.list(momentFollow);
    }

    /**
     * @description: 新增数据
     * @date: 2021/12/25 11:45
     * @codes: 扁鹊
     **/
    @Override
    public MomentFollow insert(MomentFollow momentFollow) {
        String userId = saTokenConfig.getUser().getUserId();
        momentFollow.setFollowersId(userId);
        momentFollow.setStatus(0);
        MomentFollow follow = momentFollowMapper.queryOne(momentFollow);
        if (Objects.nonNull(follow)) {
            return follow;
        }
        momentFollow.setId(RandomUtil.getRanId());
        momentFollow.setTime(new Date());
        momentFollowMapper.insert(momentFollow);
        return momentFollow;
    }

    /**
     * @description: 通过主键删除数据
     * @date: 2021/12/25 11:57
     * @codes: 扁鹊
     **/
    @Override
    public boolean deleteById(String id) {
        return momentFollowMapper.deleteById(id) > 0;
    }

    /**
     * @description: 删除数据
     * @date: 2021/12/25 14:45
     * @codes: 扁鹊
     **/
    @Override
    public boolean deleteByFollowedId(MomentFollow follow) {
        follow = momentFollowMapper.queryOne(follow);
        return deleteById(follow.getId());
    }
}
