package top.bluer.moment.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 片刻点赞表(MomentLikes)实体类
 *
 * @author bluer
 * @since 2021-12-23 17:56:52
 */
@Data
@Builder
public class MomentLikes implements Serializable {
    private static final long serialVersionUID = -20940399918687176L;
    /**
     * 主键id
     */
    private String id;
    /**
     * 动态id
     */
    private String dynamicId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 点赞时间
     */
    private Date time;
    /**
     * 数据状态：0-正常，1-删除
     */
    private Integer status;
}

