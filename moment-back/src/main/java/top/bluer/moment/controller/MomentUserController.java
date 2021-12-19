package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.service.MomentUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户表(MomentUser)表控制层
 *
 * @author bluer
 * @since 2021-11-26 14:53:20
 */
@Api(tags = "片刻-用户信息管理接口")
@RestController
@SaCheckLogin
@RequestMapping("/momentUser")
public class MomentUserController {
    @Resource
    private MomentUserService momentUserService;

    @Resource
    private SaTokenConfig saTokenConfig;

    @ApiOperation("获取用户详细信息")
    @GetMapping("/info")
    public MomentUser getMomentUserInfo() {
        return saTokenConfig.getUser();
    }

    @ApiOperation("修改资料")
    @PostMapping("/edit")
    public ResponseEntity<MomentUser> edit(MomentUser momentUser) {
        return ResponseEntity.ok(this.momentUserService.update(momentUser));
    }

    /**
     * 分页查询
     *
     * @param momentUser  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<MomentUser>> queryByPage(MomentUser momentUser, PageRequest pageRequest) {
        return ResponseEntity.ok(this.momentUserService.queryByPage(momentUser, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<MomentUser> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.momentUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param momentUser 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<MomentUser> add(MomentUser momentUser) {
        return ResponseEntity.ok(this.momentUserService.insert(momentUser));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.momentUserService.deleteById(id));
    }

}

