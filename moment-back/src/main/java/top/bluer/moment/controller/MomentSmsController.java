package top.bluer.moment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.bluer.moment.service.MomentSmsService;


import javax.annotation.Resource;

/**
 * @program: moment-back
 * @description: 通用短信服务
 * @author: bluer
 * @date: 2021-11-26 15:44
 * codes: 扁鹊
 **/
@RestController
@Api(tags = "片刻-通用短信服务接口")
@RequestMapping("/momentSms")
public class MomentSmsController {

    @Resource
    private MomentSmsService momentSmsService;

    @GetMapping("/sendMessages")
    @ApiOperation("短信发送接口")
    public void sendMessageCode(@RequestParam("phone") String phone, @RequestParam("describe") String describe) {
        momentSmsService.sendMessages(phone, describe);
    }

    @GetMapping("/validate")
    @ApiOperation("校验短信验证码接口")
    public void validateMessageCode(@RequestParam(value = "phone") String phone, @RequestParam("code") String code, @RequestParam("describe") String describe) {
        momentSmsService.validateMessageCode(phone, code, describe);
    }
}
