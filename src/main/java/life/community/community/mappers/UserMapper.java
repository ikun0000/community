package life.community.community.mappers;


import life.community.community.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id, name, token, bio, avatar_url) values (#{ accountID }, #{ name }, #{ token }, #{ bio }, #{ avatarUrl })")
    void addUser(User user);

    @Select("select * from user where token = #{ token }")
    User getUserByToken(@Param("token") String token);

    @Select("select * from user where id = #{ id }")
    User getUserById(Integer id);

    @Select("select * from user where account_id = #{ accountID }")
    User getUserByAccountId(String accountID);

    @Delete("delete from user where account_id = #{ id }")
    void deleteFromAccountID(String id);

    @Update("update user set token = #{ token } where account_id = #{ accountID }")
    void updateTokenByAccountId(String accountID, String token);

    @Update("update user set token = #{ token }, name = #{ name }, token = #{ token }, gmt_modify = now(), bio = #{ bio }, avatar_url = #{ avatarUrl } where account_id = #{ accountID }")
    void updateAllByAccountId(User user);
}
