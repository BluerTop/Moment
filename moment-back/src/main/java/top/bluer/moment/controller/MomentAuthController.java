package top.bluer.moment.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.bluer.moment.entity.dto.ChangePassDto;
import top.bluer.moment.entity.dto.LoginParamsDto;
import top.bluer.moment.service.MomentAuthService;

import javax.annotation.Resource;

/**
 * @program: moment-back
 * @description: 用户授权管理
 * @author: bluer
 * @date: 2021-11-26 14:56
 * codes: 扁鹊
 **/
@Slf4j
@Api(tags = "片刻-用户授权管理接口")
@RestController
@RequestMapping("/momentAuth")
public class MomentAuthController {

    @Resource
    private MomentAuthService momentAuthService;

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public SaTokenInfo login(@RequestBody LoginParamsDto loginParamsDto) {
        return momentAuthService.login(loginParamsDto);
    }

    @PostMapping("/changePassword")
    @ApiOperation("修改密码")
    public void changePassword(@RequestBody ChangePassDto changePassDto){
        momentAuthService.changePassword(changePassDto);
    }

    @ApiOperation("校验当前用户是否登录")
    @GetMapping("/verifyLogin")
    public boolean verifyLogin() {
        return StpUtil.isLogin();
    }

}
