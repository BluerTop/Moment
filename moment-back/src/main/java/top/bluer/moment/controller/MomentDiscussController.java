package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.bluer.moment.entity.MomentDiscuss;
import top.bluer.moment.entity.dto.MomentDiscussDto;
import top.bluer.moment.entity.vo.MomentDiscussAndUserVo;
import top.bluer.moment.service.MomentDiscussService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 片刻留言表(MomentDiscuss)表控制层
 *
 * @author bluer
 * @since 2021-12-05 18:14:20
 */
@Api(tags = "片刻-留言管理接口")
@RestController
@RequestMapping("/momentDiscuss")
public class MomentDiscussController {
    /**
     * 服务对象
     */
    @Resource
    private MomentDiscussService momentDiscussService;

    @SaCheckLogin
    @ApiOperation("发布留言")
    @PostMapping("/add")
    public MomentDiscuss add(@RequestBody MomentDiscussDto momentDiscussDto) {
        return momentDiscussService.insert(momentDiscussDto);
    }

    @ApiOperation("动态所有留言")
    @GetMapping("/getInfos")
    public List<MomentDiscussAndUserVo> getInfos(@RequestParam("dynamicId") String dynamicId) {
        return momentDiscussService.getInfos(dynamicId);
    }

    @SaCheckLogin
    @ApiOperation("删除留言")
    @GetMapping("/del")
    public boolean del(@RequestParam("id") String id){
        return momentDiscussService.deleteById(id);
    }

}

