package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.bluer.moment.entity.MomentFollow;
import top.bluer.moment.service.MomentFollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 片刻关注表(MomentFollow)表控制层
 *
 * @author bluer
 * @since 2021-12-25 11:29:35
 */
@Api(tags = "片刻-关注管理接口")
@RestController
@RequestMapping("/momentFollow")
public class MomentFollowController {
    /**
     * 服务对象
     */
    @Resource
    private MomentFollowService momentFollowService;

    @SaCheckLogin
    @ApiOperation("关注")
    @GetMapping("/add")
    public MomentFollow add(@RequestParam("followedId") String followedId) {
        return momentFollowService.insert(MomentFollow.builder().followedId(followedId).build());
    }

    @SaCheckLogin
    @ApiOperation("关注列表")
    @GetMapping("/list")
    public List<MomentFollow> list(@RequestParam("followersId") String followersId) {
        return momentFollowService.list(MomentFollow.builder().followersId(followersId).status(0).build());
    }

    @SaCheckLogin
    @ApiOperation("取消关注")
    @GetMapping("/del")
    public boolean del(@RequestParam("followedId") String followedId) {
        return momentFollowService.deleteByFollowedId(MomentFollow.builder().followedId(followedId).status(0).build());
    }
}

