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
import org.springframework.http.ResponseEntity;
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

    /**
     * @description: 发布动态
     * @date: 2021/12/2 17:55
     * codes: 扁鹊
     **/
    @SaCheckLogin
    @ApiOperation("发布动态")
    @PostMapping("/add")
    public ResponseEntity<MomentDynamic> add(@RequestBody MomentDynamicDto momentDynamicDto) {
        return ResponseEntity.ok(this.momentDynamicService.insert(momentDynamicDto));
    }

    /**
     * @description: 删除动态/恢复动态
     * @date: 2021/12/2 18:05
     * codes: 扁鹊
     **/
    @SaCheckLogin
    @ApiOperation("删除动态/恢复动态")
    @GetMapping("/delOrRec/{id}/{type}")
    public Boolean delOrRec(@PathVariable("id") String id, @PathVariable("type") String type) {
        return momentDynamicService.delOrRec(id, type);
    }

    /**
     * @description: 分页查询 公共
     * @date: 2021/12/2 18:30
     * codes: 扁鹊
     **/
    @ApiOperation("发现-分页查询动态列表")
    @GetMapping("/pages/{page}/{size}")
    public PageInfo<MomentDynamicAndUserVo> queryByPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return this.momentDynamicService.queryByPage(page, size);
    }

    /**
     * @description: 分页查询 我的
     * @date: 2021/12/2 18:30
     * codes: 扁鹊
     **/
    @SaCheckLogin
    @ApiOperation("查询我的动态列表")
    @GetMapping("/queryByMy/{index}")
    public List<MomentDynamicAndUserVo> queryByMy(@PathVariable("index") String index) {
        return this.momentDynamicService.queryByMy(index);
    }

    /**
     * @description: 根据id获取单条动态信息
     * @date: 2021/12/3 17:36
     * codes: 扁鹊
     **/
    @ApiOperation("根据id获取单条动态信息")
    @GetMapping("/getInfo/{id}")
    public MomentDynamicAndUserVo queryById(@PathVariable("id") String id) {
        return this.momentDynamicService.queryById(id);
    }

    /**
     * 编辑数据
     *
     * @param momentDynamic 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<MomentDynamic> edit(MomentDynamic momentDynamic) {
        return ResponseEntity.ok(this.momentDynamicService.update(momentDynamic));
    }

}

