package top.bluer.moment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import top.bluer.moment.common.exception.BizException;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentDiscuss;
import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.entity.dto.MomentDiscussDto;
import top.bluer.moment.entity.vo.MomentDiscussAndUserVo;
import top.bluer.moment.entity.vo.MomentDynamicAndUserVo;
import top.bluer.moment.mapper.MomentDiscussMapper;
import top.bluer.moment.mapper.MomentDynamicMapper;
import top.bluer.moment.service.MomentDiscussService;
import org.springframework.stereotype.Service;
import top.bluer.moment.utils.RandomUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 片刻留言表(MomentDiscuss)表服务实现类
 *
 * @author bluer
 * @since 2021-12-05 18:14:21
 */
@Service("momentDiscussService")
public class MomentDiscussServiceImpl implements MomentDiscussService {
    @Resource
    private MomentDiscussMapper momentDiscussMapper;
    @Resource
    private SaTokenConfig saTokenConfig;
    @Resource
    private MomentDynamicMapper momentDynamicMapper;

    /**
     * @description: 发布留言
     * @date: 2021/12/5 18:21
     * codes: 扁鹊
     **/
    @Override
    public MomentDiscuss insert(MomentDiscussDto momentDiscussDto) {
        MomentDiscuss momentDiscuss = MomentDiscuss.builder().build();
        BeanUtil.copyProperties(momentDiscussDto, momentDiscuss);
        String userId = saTokenConfig.getUser().getUserId();
        momentDiscuss.setId(RandomUtil.getRanId());
        momentDiscuss.setUserId(userId);
        String userId1 = momentDynamicMapper.queryById(momentDiscuss.getDynamicId()).getUserId();
        // 是否是作者：M-是，F-不是
        momentDiscuss.setAuthor(userId.equals(userId1) ? "M" : "F");
        momentDiscuss.setCreatTime(new Date());
        // 留言状态：M-正常、F-删除、A-审核中、P-审核不通过
        momentDiscuss.setStatus("M");
        momentDiscuss.setDynamicId(momentDiscuss.getDynamicId());
        this.momentDiscussMapper.insert(momentDiscuss);
        return momentDiscuss;
    }

    /**
     * @description: 通过ID查询单条数据
     * @date: 2021/12/23 19:03
     * @codes: 扁鹊
     **/
    @Override
    public MomentDiscuss queryById(String id) {
        return this.momentDiscussMapper.queryById(id);
    }

    /**
     * @description: 根据动态id获取所有留言
     * @date: 2021/12/5 19:25
     * codes: 扁鹊
     **/
    @Override
    public List<MomentDiscussAndUserVo> getInfos(String dynamicId) {
        MomentUser user = null;
        if (Objects.nonNull(StpUtil.getLoginIdDefaultNull())) {
            user = saTokenConfig.getUser();
        }
        MomentDynamicAndUserVo dynamicAndUserVo = momentDynamicMapper.queryById(dynamicId);
        // 动态可见范围：O-自己可见，F-粉丝可见，A-所有人可见，C-亲密关系可见
        if ((!dynamicAndUserVo.getAuthority().equals("A") && Objects.isNull(user)) || (!dynamicAndUserVo.getAuthority().equals("A") && !user.getUserId().equals(dynamicAndUserVo.getUserId()))) {
            throw new BizException("无权操作");
        }
        List<MomentDiscussAndUserVo> items = momentDiscussMapper.queryByDynamicId(dynamicId);
        List<MomentDiscussAndUserVo> collect_A = items.stream().filter(item -> item.getLevelType().equals(1)).collect(Collectors.toList());
        List<MomentDiscussAndUserVo> collect_B = items.stream().filter(item -> item.getLevelType().equals(2)).collect(Collectors.toList());
        collect_A.forEach(item -> item.setMomentDiscussAndUserVos(collect_B.stream().filter(it -> it.getLevelId().equals(item.getId())).collect(Collectors.toList())));
        return collect_A;
    }

    /**
     * 修改数据
     *
     * @param momentDiscuss 实例对象
     * @return 实例对象
     */
    @Override
    public MomentDiscuss update(MomentDiscuss momentDiscuss) {
        this.momentDiscussMapper.update(momentDiscuss);
        return this.queryById(momentDiscuss.getId());
    }

    /**
     * @description: 通过主键删除数据
     * @date: 2021/12/25 10:50
     * @codes: 扁鹊
     **/
    @Override
    public boolean deleteById(String id) {
        MomentDiscuss discuss = momentDiscussMapper.queryById(id);
        String userId = saTokenConfig.getUser().getUserId();
        if (!Objects.equals(userId, discuss.getUserId())) {
            throw new BizException("无权操作");
        }
        return momentDiscussMapper.deleteById(id, discuss.getLevelType()) >= 1;
    }
}
