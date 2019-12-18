package dev.guilhermebpnr.springbootlearning.dao;

import dev.guilhermebpnr.springbootlearning.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    List<User> getAllUsers();

    User getUser(UUID userUid);

    int updateUser(User user);

    int removerUser(UUID userUid);

    int insertUser(User user);
}
