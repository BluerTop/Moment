package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentAccount;
import top.bluer.moment.service.MomentAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 账户表(MomentAccount)表控制层
 *
 * @author bluer
 * @since 2021-11-26 14:53:14
 */
@Slf4j
@Api(tags = "片刻-账户信息管理接口")
@RestController
@RequestMapping("/momentAccount")
public class MomentAccountController {

    @Resource
    private MomentAccountService momentAccountService;

    @Resource
    private SaTokenConfig saTokenConfig;

    @SaCheckLogin
    @ApiOperation("获取账户详细信息")
    @GetMapping("/info")
    public MomentAccount getMomentUserInfo() {
        return saTokenConfig.getAccount();
    }

    /**
     * 分页查询
     *
     * @param momentAccount 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<MomentAccount>> queryByPage(MomentAccount momentAccount, PageRequest pageRequest) {
        return ResponseEntity.ok(this.momentAccountService.queryByPage(momentAccount, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<MomentAccount> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.momentAccountService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param momentAccount 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<MomentAccount> add(MomentAccount momentAccount) {
        return ResponseEntity.ok(this.momentAccountService.insert(momentAccount));
    }

    /**
     * 编辑数据
     *
     * @param momentAccount 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<MomentAccount> edit(MomentAccount momentAccount) {
        return ResponseEntity.ok(this.momentAccountService.update(momentAccount));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.momentAccountService.deleteById(id));
    }

}

