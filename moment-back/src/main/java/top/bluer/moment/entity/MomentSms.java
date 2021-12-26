package top.bluer.moment.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (MomentSms)实体类
 *
 * @author bluer
 * @since 2021-11-26 17:21:01
 */
@Data
public class MomentSms implements Serializable {
    private static final long serialVersionUID = -36868191293547668L;
    /**
     * 阿里云id
     */
    private String aliId;
    /**
     * 访问密钥 ID
     */
    private String accessKeyId;
    /**
     * 访问密钥 密码
     */
    private String accessKeySecret;
    /**
     * 短信签名
     */
    private String signName;
    /**
     * 短信模板id
     */
    private String templateCode;
    /**
     * 短信模板参数
     */
    private String templateParam;
    /**
     * 短信模板变量名
     */
    private String variableName;
    /**
     * 访问域名
     */
    private String endpoint;
    /**
     * 描述：C-验证码，P-密码，S-通知
     */
    private String describes;
}

