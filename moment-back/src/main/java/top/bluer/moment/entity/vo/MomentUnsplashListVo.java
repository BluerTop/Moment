package top.bluer.moment.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: moment-back
 * @description: unsplash每日推荐列表返回数据类
 * @author: bluer
 * @date: 2021-12-11 16:28
 * codes: 扁鹊
 **/
@Data
public class MomentUnsplashListVo {
    /**
     * 文字数据
     */
    private String textData;
    /**
     * 图片数据
     */
    private String pictureData;
    /**
     * 颜色
     */
    private String color;
    /**
     * 宽
     */
    private String width;
    /**
     * 高
     */
    private String height;
    /**
     * 图片数据
     */
    private boolean pictureDataType;
    /**
     * 图片数据集合
     */
    private List<String> pictureDataList;
    /**
     * 图片数据集合数
     */
    private Integer pictureDataListSize;
    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 位置
     */
    private String location;
    /**
     * 点赞数
     */
    private Integer likes;
}
