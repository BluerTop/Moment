package top.bluer.moment.mapper;

import top.bluer.moment.entity.MomentDynamic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import top.bluer.moment.entity.vo.MomentDynamicAndUserVo;

import java.util.List;

/**
 * 片刻动态表(MomentDynamic)表数据库访问层
 *
 * @author bluer
 * @since 2021-12-02 17:52:33
 */
@Mapper
public interface MomentDynamicMapper {

    /**
    * @description: 根据id获取单条动态信息
    * @date: 2021/12/3 17:39
    * codes: 扁鹊
    **/
    MomentDynamicAndUserVo queryById(Integer id);
    List<MomentDynamic> queryB(Integer id);

    /**
    * @description: 查询指定行数据
    * @date: 2021/12/3 17:25
    * codes: 扁鹊
    **/
    List<MomentDynamicAndUserVo> queryAllByLimit(@Param("item") MomentDynamic momentDynamic);

    /**
     * @description: 删除动态/恢复动态
     * @date: 2021/12/2 18:19
     * codes: 扁鹊
     **/
    int delOrRec(@Param("id") Integer id, @Param("status") String status);

    /**
     * @description: 发布动态
     * @date: 2021/12/2 18:17
     * codes: 扁鹊
     **/
    int insert(MomentDynamic momentDynamic);

    /**
     * 统计总行数
     *
     * @param momentDynamic 查询条件
     * @return 总行数
     */
    long count(MomentDynamic momentDynamic);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentDynamic> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentDynamic> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentDynamic> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentDynamic> entities);

    /**
     * 修改数据
     *
     * @param momentDynamic 实例对象
     * @return 影响行数
     */
    int update(MomentDynamic momentDynamic);
}

