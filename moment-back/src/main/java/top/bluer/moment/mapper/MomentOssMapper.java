package top.bluer.moment.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.bluer.moment.entity.MomentOss;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import top.bluer.moment.entity.MomentSms;

import java.util.List;

/**
 * 阿里云oss存储(MomentOss)表数据库访问层
 *
 * @author bluer
 * @since 2021-11-28 11:37:51
 */
@Mapper
public interface MomentOssMapper {

    /**
     * @description: 通过describe查询单条数据
     * @date: 2021/11/27 14:37
     * codes: 扁鹊
     **/
    MomentOss queryByDescribe(String describe);

    /**
     * 通过ID查询单条数据
     *
     * @param ossId 主键
     * @return 实例对象
     */
    MomentOss queryById(Integer ossId);

    /**
     * 查询指定行数据
     *
     * @param momentOss 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<MomentOss> queryAllByLimit(MomentOss momentOss, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param momentOss 查询条件
     * @return 总行数
     */
    long count(MomentOss momentOss);

    /**
     * 新增数据
     *
     * @param momentOss 实例对象
     * @return 影响行数
     */
    int insert(MomentOss momentOss);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentOss> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentOss> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentOss> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentOss> entities);

    /**
     * 修改数据
     *
     * @param momentOss 实例对象
     * @return 影响行数
     */
    int update(MomentOss momentOss);

    /**
     * 通过主键删除数据
     *
     * @param ossId 主键
     * @return 影响行数
     */
    int deleteById(Integer ossId);

}

