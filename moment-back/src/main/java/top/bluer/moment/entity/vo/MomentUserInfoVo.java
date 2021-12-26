package top.bluer.moment.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: moment-back
 * @description: 用户详细信息
 * @author: bluer
 * @date: 2021-12-25 12:02
 * codes: 扁鹊
 **/
@Data
public class MomentUserInfoVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户性别：M-男生，F-女，K-保密
     */
    private String userGender;
    /**
     * 用户生日
     */
    private String userBirthday;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户签名
     */
    private String userSign;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userDate;
    /**
     * 用户状态：E-正常，F-停用，A-审核中
     */
    private String userStatus;
    /**
     * 是否关注：0-否、1-是
     */
    private long followStatus;
    /**
     * 关注数
     */
    private Integer followNum;
    /**
     * 粉丝数
     */
    private Integer fanNum;
}
