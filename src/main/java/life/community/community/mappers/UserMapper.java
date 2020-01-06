package life.community.community.mappers;


import life.community.community.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id, name, token, bio, avatar_url) values (#{ accountID }, #{ name }, #{ token }, #{ bio }, #{ avatarUrl })")
    void addUser(User user);

    @Select("select * from user where token = #{ token }")
    User getUserByToken(@Param("token") String token);

    @Delete("delete from user where account_id = #{ id }")
    void deleteFromAccountID(String id);

}
