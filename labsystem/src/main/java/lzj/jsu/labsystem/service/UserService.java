package lzj.jsu.labsystem.service;


import lzj.jsu.labsystem.pojo.User;

import java.util.List;

public interface UserService {
    public List<User> getUserList(User user);

    void addUserList(User user);

    void updateUserList(User user);

    void deleteUserList(Integer id);
}
