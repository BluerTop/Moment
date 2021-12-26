package top.bluer.moment.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.bluer.moment.entity.MomentAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 账户表(MomentAccount)表数据库访问层
 *
 * @author bluer
 * @since 2021-11-26 14:53:15
 */
@Mapper
public interface MomentAccountMapper {

    /**
     * @description: 通过用户条件查询单条数据
     * @date: 2021/11/27 13:54
     * codes: 扁鹊
     **/
    MomentAccount queryByCondition(MomentAccount momentAccount);

    /**
     * @description: 修改数据
     * @date: 2021/11/27 23:12
     * codes: 扁鹊
     **/
    int update(MomentAccount momentAccount);

    /**
     * @description: 通过ID查询单条数据
     * @date: 2021/11/27 23:12
     * codes: 扁鹊
     **/
    MomentAccount queryById(String accountId);

    /**
     * 查询指定行数据
     *
     * @param momentAccount 查询条件
     * @param pageable      分页对象
     * @return 对象列表
     */
    List<MomentAccount> queryAllByLimit(MomentAccount momentAccount, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param momentAccount 查询条件
     * @return 总行数
     */
    long count(MomentAccount momentAccount);

    /**
     * @description: 新增数据
     * @date: 2021/11/27 23:13
     * codes: 扁鹊
     **/
    int insert(MomentAccount momentAccount);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentAccount> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MomentAccount> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MomentAccount> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MomentAccount> entities);

    /**
     * 通过主键删除数据
     *
     * @param accountId 主键
     * @return 影响行数
     */
    int deleteById(Integer accountId);
}

