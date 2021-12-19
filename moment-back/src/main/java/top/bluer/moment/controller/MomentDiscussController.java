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

    /**
     * @description: 发布留言
     * @date: 2021/12/5 18:15
     * codes: 扁鹊
     **/
    @SaCheckLogin
    @ApiOperation("发布留言")
    @PostMapping("/add")
    public MomentDiscuss add(@RequestBody MomentDiscussDto momentDiscussDto) {
        return this.momentDiscussService.insert(momentDiscussDto);
    }

    /**
     * @description: 根据动态id获取所有留言
     * @date: 2021/12/5 19:25
     * codes: 扁鹊
     **/
    @ApiOperation("根据动态id获取所有留言")
    @GetMapping("/getInfos/{dynamicId}")
    public List<MomentDiscussAndUserVo> getInfos(@PathVariable("dynamicId") String dynamicId) {
        return this.momentDiscussService.getInfos(dynamicId);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<MomentDiscuss> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.momentDiscussService.queryById(id));
    }

    /**
     * 编辑数据
     *
     * @param momentDiscuss 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<MomentDiscuss> edit(MomentDiscuss momentDiscuss) {
        return ResponseEntity.ok(this.momentDiscussService.update(momentDiscuss));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.momentDiscussService.deleteById(id));
    }

}

