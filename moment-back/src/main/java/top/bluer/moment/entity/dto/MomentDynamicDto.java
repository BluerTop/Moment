package top.bluer.moment.entity.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * @program: moment-back
 * @description: 发布动态参数实体类
 * @author: bluer
 * @date: 2021-12-02 18:08
 * codes: 扁鹊
 **/
@Data
public class MomentDynamicDto {
    public MomentDynamicDto(){}
    /**
     * 动态类型：T-文字，P-图片，G-图文，V-视频
     */
    @NonNull
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
    @NonNull
    private String label;
    /**
     * 可见范围：O-自己可见，F-粉丝可见，A-所有人可见，C-亲密关系可见
     */
    @NonNull
    private String authority;
    /**
     * 用户id
     */
    @NonNull
    private Integer userId;
}
