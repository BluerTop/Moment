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
    * @description: 通过ID查询单条数据
    * @date: 2021/12/23 19:01
    * @codes: 扁鹊
    **/
    @Override
    public MomentAccount queryById(String accountId) {
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
        momentAccountMapper.insert(momentAccount);
        return momentAccount;
    }

    /**
    * @description: 修改数据
    * @date: 2021/11/27 23:14
    * codes: 扁鹊
    **/
    @Override
    public MomentAccount update(MomentAccount momentAccount) {
        momentAccountMapper.update(momentAccount);
        return queryById(momentAccount.getAccountId());
    }
}
