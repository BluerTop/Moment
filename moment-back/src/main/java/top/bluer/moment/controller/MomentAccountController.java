package top.bluer.moment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentAccount;
import top.bluer.moment.service.MomentAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 账户表(MomentAccount)表控制层
 *
 * @author bluer
 * @since 2021-11-26 14:53:14
 */
@Slf4j
@Api(tags = "片刻-账户信息管理接口")
@RestController
@RequestMapping("/momentAccount")
public class MomentAccountController {

    @Resource
    private MomentAccountService momentAccountService;

    @Resource
    private SaTokenConfig saTokenConfig;

    @SaCheckLogin
    @ApiOperation("获取账户详细信息")
    @GetMapping("/info")
    public MomentAccount getMomentUserInfo() {
        return saTokenConfig.getAccount();
    }
}

