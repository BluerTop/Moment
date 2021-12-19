package top.bluer.moment.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * @program: moment-back
 * @description: 修改密码参数实体类
 * @author: bluer
 * @date: 2021-11-27 23:01
 * codes: 扁鹊
 **/
@Data
public class ChangePassDto {
    public ChangePassDto() {}

    @NonNull
    @ApiModelProperty(value = "旧密码")
    private String passwordA;

    @NonNull
    @ApiModelProperty(value = "新密码")
    private String passwordB;
}
