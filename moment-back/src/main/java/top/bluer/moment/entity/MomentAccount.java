package top.bluer.moment.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

/**
 * 账户表(MomentAccount)实体类
 *
 * @author bluer
 * @since 2021-11-26 14:53:16
 */
@Data
@Builder
public class MomentAccount implements Serializable {
    private static final long serialVersionUID = -53768658285297673L;
    /**
     * 账户id
     */
    private Integer accountId;
    /**
     * 账户类型：P-手机号，M-邮箱
     */
    private String accountType;
    /**
     * 账户-手机号
     */
    private String accountPhone;
    /**
     * 账户-邮箱
     */
    private String accountMail;
    /**
     * 账户-密码
     */
    private String accountPassword;
    /**
     * 账户状态：E-正常，F-停用，A-审核中
     */
    private String accountStatus;
    /**
     * 账户-用户id
     */
    private Integer userId;
}

