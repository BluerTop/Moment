package top.bluer.moment.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 片刻留言表(MomentDiscuss)实体类
 *
 * @author bluer
 * @since 2021-12-05 18:14:22
 */
@Data
@Builder
public class MomentDiscuss implements Serializable {
    private static final long serialVersionUID = 112194592198563939L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 留言内容
     */
    private String discussData;
    /**
     * 级别：一级、二级
     */
    private Integer levelType;
    /**
     * 上级留言id（二级留言适用）
     */
    private Integer levelId;
    /**
     * 所属动态id
     */
    private String dynamicId;
    /**
     * 所属用户id
     */
    private Integer userId;
    /**
     * 是否是作者：M-是，F-不是
     */
    private String author;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 留言状态：M-正常、F-删除、A-审核中、P-审核不通过
     */
    private String status;
}

