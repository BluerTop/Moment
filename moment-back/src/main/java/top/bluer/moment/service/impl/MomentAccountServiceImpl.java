package top.bluer.moment.service.impl;

import top.bluer.moment.entity.MomentAccount;
import top.bluer.moment.mapper.MomentAccountMapper;
import top.bluer.moment.service.MomentAccountService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 账户表(MomentAccount)表服务实现类
 *
 * @author bluer
 * @since 2021-11-26 14:53:19
 */
@Service("momentAccountService")
public class MomentAccountServiceImpl implements MomentAccountService {
    @Resource
    private MomentAccountMapper momentAccountMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param accountId 主键
     * @return 实例对象
     */
    @Override
    public MomentAccount queryById(Integer accountId) {
        return this.momentAccountMapper.queryById(accountId);
    }

    /**
     * @description: 通过用户条件查询单条数据
     * @date: 2021/11/27 13:52
     * codes: 扁鹊
     **/
    @Override
    public MomentAccount queryByCondition(MomentAccount momentAccount) {
        return momentAccountMapper.queryByCondition(momentAccount);
    }

    /**
     * @description: 新增数据
     * @date: 2021/11/27 23:13
     * codes: 扁鹊
     **/
    @Override
    public MomentAccount insert(MomentAccount momentAccount) {
        this.momentAccountMapper.insert(momentAccount);
        return momentAccount;
    }

    /**
    * @description: 修改数据
    * @date: 2021/11/27 23:14
    * codes: 扁鹊
    **/
    @Override
    public MomentAccount update(MomentAccount momentAccount) {
        this.momentAccountMapper.update(momentAccount);
        return this.queryById(momentAccount.getAccountId());
    }

    /**
     * 分页查询
     *
     * @param momentAccount 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @Override
    public Page<MomentAccount> queryByPage(MomentAccount momentAccount, PageRequest pageRequest) {
        long total = this.momentAccountMapper.count(momentAccount);
        return new PageImpl<>(this.momentAccountMapper.queryAllByLimit(momentAccount, pageRequest), pageRequest, total);
    }

    /**
     * 通过主键删除数据
     *
     * @param accountId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer accountId) {
        return this.momentAccountMapper.deleteById(accountId) > 0;
    }
}
