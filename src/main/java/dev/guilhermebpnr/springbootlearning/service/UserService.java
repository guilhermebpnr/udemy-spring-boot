package dev.guilhermebpnr.springbootlearning.service;

import dev.guilhermebpnr.springbootlearning.dao.UserDao;
import dev.guilhermebpnr.springbootlearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserById(userUid);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if (optionalUser.isPresent()) {
            userDao.updateUser(user);
            return 1;
        }
        return -1;
    }

    public int removerUser(UUID userUid) {
        Optional<User> optionalUser = getUser(userUid);
        if (optionalUser.isPresent()) {
            userDao.deleteUserById(userUid);
            return 1;
        }
        return -1;
    }

    public int insertUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if (!optionalUser.isPresent()) {
            userDao.insertUser(user);
            return 1;
        }
        return -1;
    }
}
