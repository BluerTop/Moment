package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import top.bluer.moment.entity.vo.MomentUserInfoVo;
import top.bluer.moment.service.MomentLikesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 片刻点赞表(MomentLikes)表控制层
 *
 * @author bluer
 * @since 2021-12-23 17:56:52
 */
@Api(tags = "片刻-点赞管理接口")
@RestController
@RequestMapping("/momentLikes")
public class MomentLikesController {
    /**
     * 服务对象
     */
    @Resource
    private MomentLikesService momentLikesService;

    @SaCheckLogin
    @ApiOperation("点赞/消赞")
    @GetMapping("/like")
    public Integer[] like(@RequestParam("id") @ApiParam("动态id") String dynamicId) {
        return momentLikesService.like(dynamicId);
    }

    @ApiOperation("动态点赞列表")
    @GetMapping("/likeList")
    public List<MomentUserInfoVo> likeList(@RequestParam("id") @ApiParam("动态id") String dynamicId) {
        return momentLikesService.likeList(dynamicId);
    }
}

