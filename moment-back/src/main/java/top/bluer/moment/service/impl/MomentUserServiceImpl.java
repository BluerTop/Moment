package top.bluer.moment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentDynamic;
import top.bluer.moment.entity.MomentFollow;
import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.entity.vo.MomentDynamicAndUserVo;
import top.bluer.moment.entity.vo.MomentUserInfoVo;
import top.bluer.moment.mapper.MomentDynamicMapper;
import top.bluer.moment.mapper.MomentFollowMapper;
import top.bluer.moment.mapper.MomentLikesMapper;
import top.bluer.moment.mapper.MomentUserMapper;
import top.bluer.moment.service.MomentUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(MomentUser)表服务实现类
 *
 * @author bluer
 * @since 2021-11-26 14:53:20
 */
@Service("momentUserService")
public class MomentUserServiceImpl implements MomentUserService {
    @Resource
    private MomentUserMapper momentUserMapper;
    @Resource
    private SaTokenConfig saTokenConfig;
    @Resource
    private MomentFollowMapper momentFollowMapper;
    @Resource
    private MomentDynamicMapper momentDynamicMapper;
    @Resource
    private MomentLikesMapper momentLikesMapper;

    /**
     * @description: 通过ID查询单条数据
     * @date: 2021/12/25 12:01
     * @codes: 扁鹊
     **/
    @Override
    public MomentUser queryById(String userId) {
        return momentUserMapper.queryById(userId);
    }

    /**
     * @description: 用户详细信息
     * @date: 2021/12/26 14:51
     * @codes: 扁鹊
     **/
    @Override
    public MomentUserInfoVo getMomentUserInfo() {
        String userId = saTokenConfig.getUser().getUserId();
        List<MomentDynamicAndUserVo> dynamicAndUserVos = momentDynamicMapper.queryAllByLimit(MomentDynamic.builder().build());
        List<String> collect = dynamicAndUserVos.stream().map(MomentDynamicAndUserVo::getId).collect(Collectors.toList());
        MomentUserInfoVo info = momentUserMapper.getMomentUserInfo(userId);
        info.setLikes(momentLikesMapper.userLikeCount(userId, collect));
        return info;
    }

    /**
     * @description: 推荐用户列表
     * @date: 2021/12/25 12:19
     * @codes: 扁鹊
     **/
    @Override
    public PageInfo<MomentUserInfoVo> recommend(MomentUser momentUser, Integer page, Integer size) {
        String userId = saTokenConfig.getUser().getUserId();
        momentUser.setUserId(userId);
        PageHelper.startPage(page, size);
        List<MomentUserInfoVo> items = momentUserMapper.recommend(momentUser);
        PageInfo<MomentUserInfoVo> pageInfo = new PageInfo<>(items);
        items.forEach(item -> item.setFollowStatus(momentFollowMapper.count(MomentFollow.builder().followersId(userId).followedId(item.getUserId()).status(0).build())));
        pageInfo.setList(items);
        return pageInfo;
    }

    /**
     * @description: 新增数据
     * @date: 2021/12/23 18:55
     * @codes: 扁鹊
     **/
    @Override
    public MomentUser insert(MomentUser momentUser) {
        momentUserMapper.insert(momentUser);
        return momentUser;
    }

    /**
     * 修改数据
     *
     * @param momentUser 实例对象
     * @return 实例对象
     */
    @Override
    public MomentUser update(MomentUser momentUser) {
        this.momentUserMapper.update(momentUser);
        return this.queryById(momentUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String userId) {
        return this.momentUserMapper.deleteById(userId) > 0;
    }
}
