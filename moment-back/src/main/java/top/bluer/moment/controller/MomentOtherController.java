package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.bluer.moment.entity.vo.MomentUnsplashListVo;
import top.bluer.moment.service.MomentOtherService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @program: moment-back
 * @description: 片刻-第三方服务
 * @author: bluer
 * @date: 2021-11-27 23:27
 * codes: 扁鹊
 **/
@Api(tags = "片刻-其他服务接口")
@RestController
@RequestMapping("/momentOther")
public class MomentOtherController {

    @Resource
    private MomentOtherService momentOtherService;

    @SaCheckLogin
    @ApiOperation("通用上传文件接口")
    @PostMapping("/uploadFile/{describe}")
    public ArrayList<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("describe") String describe) {
        return momentOtherService.uploadFile(file, describe);
    }

    @SaCheckLogin
    @ApiOperation("一言")
    @GetMapping("/oneWord")
    public HashMap<String, Object> oneWord() {
        return momentOtherService.oneWord();
    }

    @ApiOperation("获取unsplash随机图片")
    @GetMapping("/photos/{index}")
    public ArrayList<MomentUnsplashListVo> photos(@PathVariable("index") String index) {
        return momentOtherService.photos(index);
    }
}
