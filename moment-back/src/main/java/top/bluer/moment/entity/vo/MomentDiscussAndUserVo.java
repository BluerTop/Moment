package top.bluer.moment.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: moment-back
 * @description: 获取所有留言返回数据实体类
 * @author: bluer
 * @date: 2021-12-05 19:27
 * codes: 扁鹊
 **/
@Data
public class MomentDiscussAndUserVo {
    /**
     * 主键
     */
    private String id;
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
    private String levelId;
    /**
     * 所属动态id
     */
    private String dynamicId;
    /**
     * 所属用户id
     */
    private String userId;
    /**
     * 是否是作者：M-是，F-不是
     */
    private String author;
    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    /**
     * 留言状态：M-正常、F-删除、A-审核中、P-审核不通过
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
     * 下级留言集
     */
    private List<MomentDiscussAndUserVo> MomentDiscussAndUserVos;
}
