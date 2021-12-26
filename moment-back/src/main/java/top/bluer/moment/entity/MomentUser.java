package top.bluer.moment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(MomentUser)实体类
 *
 * @author bluer
 * @since 2021-11-26 14:53:20
 */
@Data
@Builder
public class MomentUser implements Serializable {
    private static final long serialVersionUID = 132045334115847396L;
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
}

