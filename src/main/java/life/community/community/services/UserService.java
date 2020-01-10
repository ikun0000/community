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

    public User createOrUpdate(@NotNull User user) {
        if (userMapper.getUserByAccountId(user.getAccountID()) != null) {
            userMapper.updateAllByAccountId(user);
        } else {
            userMapper.addUser(user);
        }

        return userMapper.getUserByAccountId(user.getAccountID());
    }
}
