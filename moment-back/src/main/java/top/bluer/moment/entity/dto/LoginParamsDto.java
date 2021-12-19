package top.bluer.moment.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * @program: moment-back
 * @description: 登录参数实体类
 * @author: bluer
 * @date: 2021-11-26 15:42
 * codes: 扁鹊
 **/
@Data
public class LoginParamsDto {
    public LoginParamsDto() {}

    @NonNull
    @ApiModelProperty(value = "登录类型：PC-手机号+验证码，MP-邮箱+密码，PP-手机号+密码")
    private String loginType;

    @ApiModelProperty(value = "账户-手机号")
    private String accountPhone;

    @ApiModelProperty(value = "账户-邮箱")
    private String accountMail;

    @ApiModelProperty(value = "账户-密码")
    private String accountPassword;

    @ApiModelProperty(value = "账户-code")
    private String accountCode;

    @ApiModelProperty(value = "阿里云短信类型")
    private String describe;
}
