package top.bluer.moment.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.bluer.moment.entity.MomentSms;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (MomentSms)表数据库访问层
 *
 * @author bluer
 * @since 2021-11-26 17:21:00
 */
@Mapper
public interface MomentSmsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param aliId 主键
     * @return 实例对象
     */
    MomentSms queryById(String aliId);

    /**
     * @description: 通过describes查询单条数据
     * @date: 2021/11/27 14:37
     * codes: 扁鹊
     **/
    MomentSms queryByDescribe(String describes);

    /**
     * 查询指定行数据
     *
     * @param momentSms 查询条件
     * @param pageable  分页对象
     * @return 对象列表
     */
    List<MomentSms> queryAllByLimit(MomentSms momentSms, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param momentSms 查询条件
     * @return 总行数
     */
    long count(MomentSms momentSms);

    /**
     * 新增数据
     *
     * @param momentSms 实例对象
     * @return 影响行数
     */
    int insert(MomentSms momentSms);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentSms> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentSms> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentSms> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentSms> entities);

    /**
     * 修改数据
     *
     * @param momentSms 实例对象
     * @return 影响行数
     */
    int update(MomentSms momentSms);

    /**
     * 通过主键删除数据
     *
     * @param aliId 主键
     * @return 影响行数
     */
    int deleteById(String aliId);

}

