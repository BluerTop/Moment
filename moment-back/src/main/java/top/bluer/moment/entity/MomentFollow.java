package top.bluer.moment.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 片刻关注表(MomentFollow)实体类
 *
 * @author bluer
 * @since 2021-12-25 11:29:35
 */
@Data
@Builder
public class MomentFollow implements Serializable {
    private static final long serialVersionUID = 608529973781404414L;
    /**
     * id
     */
    private String id;
    /**
     * 关注者id
     */
    private String followersId;
    /**
     * 被关注者id
     */
    private String followedId;
    /**
     * 关注时间
     */
    private Date time;
    /**
     * 数据状态：0-正常、1-删除
     */
    private Integer status;
}

