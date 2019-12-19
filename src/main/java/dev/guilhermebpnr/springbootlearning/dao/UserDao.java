package dev.guilhermebpnr.springbootlearning.dao;

import dev.guilhermebpnr.springbootlearning.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    List<User> selectAllUsers();

    Optional<User> selectUserById(UUID userUid);

    int updateUser(User user);

    int deleteUserById(UUID userUid);

    int insertUser(User user);
}
