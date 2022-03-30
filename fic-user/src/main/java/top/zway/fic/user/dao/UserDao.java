package top.zway.fic.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.UserDO;

/**
 * @author ZZJ
 */
@Mapper
public interface UserDao {
    /**
     * 插入新user
     * @param userDO user信息
     * @return 数量 userDO包含id信息
     */
    int insertUser(@Param("userDO") UserDO userDO);

    /**
     * 查询用户名同名数量
     * @param username 用户名
     * @return 数量
     */
    int countUsername(@Param("username")String username);

    /**
     * 根据用户id出UserDO
     * @param id 用户id
     * @return UserDO
     */
    UserDO getUserDoById(@Param("id") Long id);

    /**
     * 修改密码
     * @param password 新密码
     * @param id 用户id
     * @return 数量
     */
    int updatePassword(@Param("password") String password,@Param("id") Long id);
}
