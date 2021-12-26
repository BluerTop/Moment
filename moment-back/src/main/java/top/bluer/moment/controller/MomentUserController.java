package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.entity.vo.MomentUserInfoVo;
import top.bluer.moment.service.MomentUserService;
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

    @ApiOperation("用户详细信息")
    @GetMapping("/info")
    public MomentUserInfoVo getMomentUserInfo() {
        return momentUserService.getMomentUserInfo();
    }

    @ApiOperation("修改资料")
    @PostMapping("/edit")
    public MomentUser edit(MomentUser momentUser) {
        return momentUserService.update(momentUser);
    }

    @ApiOperation("推荐用户列表")
    @GetMapping("/recommend")
    public PageInfo<MomentUserInfoVo> recommend(@RequestParam("page") Integer page, @RequestParam("size") Integer size,@RequestParam("name") String name) {
        return momentUserService.recommend(MomentUser.builder().userName(name).userStatus("E").build(),page,size);
    }
}

