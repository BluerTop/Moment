package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.bluer.moment.entity.MomentDynamic;
import top.bluer.moment.entity.vo.MomentDynamicAndUserVo;
import top.bluer.moment.entity.dto.MomentDynamicDto;
import top.bluer.moment.service.MomentDynamicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 片刻动态表(MomentDynamic)表控制层
 *
 * @author bluer
 * @since 2021-12-02 17:52:33
 */
@RestController
@Slf4j
@Api(tags = "片刻-动态信息管理接口")
@RequestMapping("/momentDynamic")
public class MomentDynamicController {
    /**
     * 服务对象
     */
    @Resource
    private MomentDynamicService momentDynamicService;

    @SaCheckLogin
    @ApiOperation("发布动态")
    @PostMapping("/add")
    public MomentDynamic add(@RequestBody MomentDynamicDto momentDynamicDto) {
        return momentDynamicService.insert(momentDynamicDto);
    }

    @SaCheckLogin
    @ApiOperation("删除动态/恢复动态")
    @GetMapping("/delOrRec")
    public Boolean delOrRec(@RequestParam("id") String id, @RequestParam("type") String type) {
        return momentDynamicService.delOrRec(id, type);
    }

    @ApiOperation("发现-分页查询动态列表")
    @GetMapping("/pages")
    public PageInfo<MomentDynamicAndUserVo> queryByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return momentDynamicService.queryByPage(page, size);
    }

    @SaCheckLogin
    @ApiOperation("我的动态列表")
    @GetMapping("/queryByMy")
    public List<MomentDynamicAndUserVo> queryByMy(@RequestParam("index") Integer index) {
        return momentDynamicService.queryByMy(index);
    }

    @SaCheckLogin
    @ApiOperation("我的关注动态列表")
    @GetMapping("/queryByMyFocus")
    public PageInfo<MomentDynamicAndUserVo> queryByMyFocus(@RequestParam("index") Integer index) {
        return momentDynamicService.queryByMyFocus(index);
    }

    @ApiOperation("单条动态信息")
    @GetMapping("/getInfo")
    public MomentDynamicAndUserVo queryById(@RequestParam("id") String id) {
        return momentDynamicService.queryById(id);
    }
}

