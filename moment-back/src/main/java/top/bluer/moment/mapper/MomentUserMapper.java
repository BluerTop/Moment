package top.bluer.moment.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.bluer.moment.entity.MomentUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import top.bluer.moment.entity.vo.MomentUserInfoVo;

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
     * @description: 通过ID查询单条数据
     * @date: 2021/12/25 12:01
     * @codes: 扁鹊
     **/
    MomentUser queryById(String userId);

    /**
     * @description: 用户详细信息
     * @date: 2021/12/26 14:51
     * @codes: 扁鹊
     **/
    MomentUserInfoVo getMomentUserInfo(@Param("id") String userId);

    /**
     * @description: 推荐用户列表
     * @date: 2021/12/25 13:47
     * @codes: 扁鹊
     **/
    List<MomentUserInfoVo> recommend(@Param("item") MomentUser momentUser);

    /**
     * @description: 动态点赞用户列表
     * @date: 2021/12/25 13:47
     * @codes: 扁鹊
     **/
    List<MomentUserInfoVo> likeList(@Param("ids") List<String> ids);

    /**
     * @description: 查询指定行数据
     * @date: 2021/12/25 12:20
     * @codes: 扁鹊
     **/
    List<MomentUserInfoVo> queryAllByLimit(@Param("item") MomentUser momentUser);

    /**
     * @description: 统计总行数
     * @date: 2021/12/25 12:25
     * @codes: 扁鹊
     **/
    long count(MomentUser momentUser);

    /**
     * @description: 新增数据
     * @date: 2021/12/23 18:55
     * @codes: 扁鹊
     **/
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
    int deleteById(String userId);
}

