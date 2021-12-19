package top.bluer.moment.mapper;

import top.bluer.moment.entity.MomentDiscuss;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.apache.ibatis.annotations.Mapper;
import top.bluer.moment.entity.vo.MomentDiscussAndUserVo;

import java.util.List;

/**
 * 片刻留言表(MomentDiscuss)表数据库访问层
 *
 * @author bluer
 * @since 2021-12-05 18:14:22
 */
@Mapper
public interface MomentDiscussMapper {

    /**
     * @description: 根据动态id获取所有留言
     * @date: 2021/12/5 19:41
     * codes: 扁鹊
     **/
    List<MomentDiscussAndUserVo> queryByDynamicId(@Param("dynamicId") Integer dynamicId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MomentDiscuss queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param momentDiscuss 查询条件
     * @param pageable      分页对象
     * @return 对象列表
     */
    List<MomentDiscuss> queryAllByLimit(MomentDiscuss momentDiscuss, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param momentDiscuss 查询条件
     * @return 总行数
     */
    long count(MomentDiscuss momentDiscuss);

    /**
     * @description: 发布留言
     * @date: 2021/12/5 18:30
     * codes: 扁鹊
     **/
    int insert(MomentDiscuss momentDiscuss);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentDiscuss> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentDiscuss> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentDiscuss> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentDiscuss> entities);

    /**
     * 修改数据
     *
     * @param momentDiscuss 实例对象
     * @return 影响行数
     */
    int update(MomentDiscuss momentDiscuss);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

