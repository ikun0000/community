package life.community.community.mappers;


import life.community.community.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /**
     * 添加一个用户
     * @param user      用户实体
     */
    @Insert("insert into user (account_id, name, token, bio, avatar_url) values (#{ accountID }, #{ name }, #{ token }, #{ bio }, #{ avatarUrl })")
    void addUser(User user);

    /**
     * 根据令牌获取用户
     * @param token     令牌
     * @return          用户实体
     */
    @Select("select * from user where token = #{ token }")
    User getUserByToken(@Param("token") String token);

    /**
     * 根据用户ID获取用户
     * @param id        用户的ID
     * @return          用户实体
     */
    @Select("select * from user where id = #{ id }")
    User getUserById(Integer id);

    /**
     * 根据Github的ID获取用户
     * @param accountID     Github上用户的ID
     * @return              用户实体
     */
    @Select("select * from user where account_id = #{ accountID }")
    User getUserByAccountId(String accountID);

    /**
     * 更新用户信息
     * @param user          用户实体
     */
    @Update("update user set token = #{ token }, name = #{ name }, token = #{ token }, gmt_modify = now(), bio = #{ bio }, avatar_url = #{ avatarUrl } where account_id = #{ accountID }")
    void updateAllByAccountId(User user);
}
