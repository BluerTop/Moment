package top.bluer.moment.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: moment-back
 * @description: 动态列表返回数据实体类
 * @author: bluer
 * @date: 2021-12-04 18:15
 * codes: 扁鹊
 **/
@Data
public class MomentDynamicAndUserVo {
    /**
     * 主键id
     */
    private String id;
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
     * 图片高度
     */
    private String ImageHeight;
    /**
     * 图片宽度
     */
    private String ImageWidth;
    /**
     * 图片主色调
     */
    private String ImageColor;
    /**
     * 图片数据集合
     */
    private List<String> pictureDataList;
    /**
     * 图片数据集合数
     */
    private Integer pictureDataListSize;
    /**
     * 视频数据
     */
    private String videoData;
    /**
     * 标签
     */
    private String label;
    /**
     * 标签
     */
    private List<String> labelList;
    /**
     * 可见范围：O-自己可见，F-粉丝可见，A-所有人可见，C-亲密关系可见
     */
    private String authority;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    /**
     * 状态：M-正常，F-删除，R-审核中,N-审核不通过
     */
    private String status;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 点赞数
     */
    private Integer likes;
    /**
     * 是否点赞
     */
    private boolean likeStatus;
    /**
     * 是否关注：0-否、1-是
     */
    private long followStatus;
}
