package top.bluer.moment.entity.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * @program: moment-back
 * @description: 发布留言接收参数实体类
 * @author: bluer
 * @date: 2021-12-05 18:18
 * codes: 扁鹊
 **/
@Data
public class MomentDiscussDto {
    /**
     * 留言内容
     */
    @NonNull
    private String discussData;
    /**
     * 级别：一级、二级
     */
    @NonNull
    private Integer levelType;
    /**
     * 上级留言id（二级留言适用）
     */
    private Integer levelId;
    /**
     * 所属动态id
     */
    @NonNull
    private String dynamicId;
}
