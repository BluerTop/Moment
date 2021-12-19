package top.bluer.moment.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 片刻动态表(MomentDynamic)实体类
 *
 * @author bluer
 * @since 2021-12-02 17:52:37
 */
@Data
@Builder
public class MomentDynamic implements Serializable {
    private static final long serialVersionUID = 706853127160415867L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 动态类型：T-文字，P-图片，G-图文，V-视频
     */
    private String type;
    /**
     * 文字数据
     */
    private String textData;
    /**
     * 图片数据
     */
    private String pictureData;
    /**
     * 视频数据
     */
    private String videoData;
    /**
     * 标签
     */
    private String label;
    /**
     * 可见范围：O-自己可见，F-粉丝可见，A-所有人可见，C-亲密关系可见
     */
    private String authority;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 状态：M-正常，F-删除，R-审核中,N-审核不通过
     */
    private String status;
}

