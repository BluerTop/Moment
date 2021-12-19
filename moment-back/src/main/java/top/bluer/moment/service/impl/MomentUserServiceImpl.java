package top.bluer.moment.service.impl;

import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.mapper.MomentUserMapper;
import top.bluer.moment.service.MomentUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

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

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public MomentUser queryById(Integer userId) {
        return this.momentUserMapper.queryById(userId);
    }

    /**
     * 分页查询
     *
     * @param momentUser  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<MomentUser> queryByPage(MomentUser momentUser, PageRequest pageRequest) {
        long total = this.momentUserMapper.count(momentUser);
        return new PageImpl<>(this.momentUserMapper.queryAllByLimit(momentUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param momentUser 实例对象
     * @return 实例对象
     */
    @Override
    public MomentUser insert(MomentUser momentUser) {
        this.momentUserMapper.insert(momentUser);
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
    public boolean deleteById(Integer userId) {
        return this.momentUserMapper.deleteById(userId) > 0;
    }
}
