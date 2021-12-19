package top.bluer.moment.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.bluer.moment.entity.MomentUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户表(MomentUser)表数据库访问层
 *
 * @author bluer
 * @since 2021-11-26 14:53:20
 */
@Mapper
public interface MomentUserMapper {

    List<MomentUser> selec();

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    MomentUser queryById(Integer userId);

    /**
     * 查询指定行数据
     *
     * @param momentUser 查询条件
     * @param pageable   分页对象
     * @return 对象列表
     */
    List<MomentUser> queryAllByLimit(MomentUser momentUser, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param momentUser 查询条件
     * @return 总行数
     */
    long count(MomentUser momentUser);

    /**
     * 新增数据
     *
     * @param momentUser 实例对象
     * @return 影响行数
     */
    int insert(MomentUser momentUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentUser> entities);

    /**
     * 修改数据
     *
     * @param momentUser 实例对象
     * @return 影响行数
     */
    int update(MomentUser momentUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Integer userId);

}

