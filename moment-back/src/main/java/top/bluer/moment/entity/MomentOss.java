package top.bluer.moment.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 阿里云oss存储(MomentOss)实体类
 *
 * @author bluer
 * @since 2021-11-28 11:37:51
 */
@Data
@Builder
public class MomentOss implements Serializable {
    private static final long serialVersionUID = -74537740361620049L;
    /**
     * 主键id
     */
    private Integer ossId;
    /**
     * 访问密钥 ID
     */
    private String accessKeyId;
    /**
     * 访问密钥 密码
     */
    private String accessKeySecret;
    /**
     * 访问域名
     */
    private String endpoint;
    /**
     * 资源名
     */
    private String bucketName;
    /**
     * 路径key
     */
    private String urlKey;
    /**
     * 返回前缀
     */
    private String returnPrefix;
    /**
     * 描述：P-图片，V-视频
     */
    private String describes;
}

