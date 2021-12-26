package top.bluer.moment.mapper;

import top.bluer.moment.entity.MomentLikes;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 片刻点赞表(MomentLikes)表数据库访问层
 *
 * @author bluer
 * @since 2021-12-23 17:56:52
 */
@Mapper
public interface MomentLikesMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MomentLikes queryById(String id);

    /**
    * @description: 列表查询
    * @date: 2021/12/26 13:59
    * @codes: 扁鹊
    **/
    List<MomentLikes> list(MomentLikes momentLikes);

    /**
     * @description: 统计总行数
     * @date: 2021/12/23 18:18
     * @codes: 扁鹊
     **/
    int count(MomentLikes momentLikes);

    /**
     * @description: 新增数据
     * @date: 2021/12/23 18:18
     * @codes: 扁鹊
     **/
    int insert(MomentLikes momentLikes);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentLikes> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentLikes> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentLikes> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentLikes> entities);

    /**
    * @description: 修改数据
    * @date: 2021/12/23 19:34
    * @codes: 扁鹊
    **/
    int update(MomentLikes momentLikes);

    /**
     * @description: 删除数据
     * @date: 2021/12/23 18:15
     * @codes: 扁鹊
     **/
    int deleteById(String id);

    /**
     * @description: 详细信息
     * @date: 2021/12/23 18:16
     * @codes: 扁鹊
     **/
    MomentLikes getInfo(MomentLikes momentLikes);

}

