package top.bluer.moment.mapper;

import top.bluer.moment.entity.MomentFollow;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 片刻关注表(MomentFollow)表数据库访问层
 *
 * @author bluer
 * @since 2021-12-25 11:29:35
 */
@Mapper
public interface MomentFollowMapper {

    /**
     * @description: 查询单条
     * @date: 2021/12/25 11:47
     * @codes: 扁鹊
     **/
    MomentFollow queryOne(MomentFollow momentFollow);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MomentFollow queryById(String id);

    /**
     * @description: 列表查询
     * @date: 2021/12/25 11:53
     * @codes: 扁鹊
     **/
    List<MomentFollow> list(MomentFollow momentFollow);

    /**
     * 统计总行数
     *
     * @param momentFollow 查询条件
     * @return 总行数
     */
    long count(MomentFollow momentFollow);

    /**
     * @description: 新增数据
     * @date: 2021/12/25 11:50
     * @codes: 扁鹊
     **/
    int insert(MomentFollow momentFollow);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentFollow> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentFollow> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentFollow> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentFollow> entities);

    /**
     * @description: 通过主键删除数据
     * @date: 2021/12/25 11:57
     * @codes: 扁鹊
     **/
    int deleteById(String id);

}

