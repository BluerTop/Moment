package top.bluer.moment.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import top.bluer.moment.entity.dto.ChangePassDto;
import top.bluer.moment.entity.dto.LoginParamsDto;

/**
 * @program: moment-back
 * @description: 用户授权服务接口
 * @author: bluer
 * @date: 2021-11-27 13:16
 * codes: 扁鹊
 **/
public interface MomentAuthService {
    /**
     * @description: 登录
     * @date: 2021/11/27 13:17
     * codes: 扁鹊
     **/
    SaTokenInfo login(LoginParamsDto loginParamsDto);

    /**
     * @description: 修改密码
     * @date: 2021/11/27 23:07
     * codes: 扁鹊
     **/
    void changePassword(ChangePassDto changePassDto);
}
