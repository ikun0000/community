package life.community.community.services;


import life.community.community.entity.User;
import life.community.community.mappers.UserMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户的实体创建或者修改用户
     * @param user      用户实体
     * @return          修改后的用户
     */
    public User createOrUpdate(@NotNull User user) {
        // 使用传入的user的Github ID搜索数据库中是否有匹配的，如果没有匹配则新建对象
        // 否则修改
        if (userMapper.getUserByAccountId(user.getAccountID()) != null) {
            userMapper.updateAllByAccountId(user);
        } else {
            userMapper.addUser(user);
        }

        return userMapper.getUserByAccountId(user.getAccountID());
    }
}
