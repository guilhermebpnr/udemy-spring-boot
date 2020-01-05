package dev.guilhermebpnr.springbootlearning.service;

import dev.guilhermebpnr.springbootlearning.dao.UserDao;
import dev.guilhermebpnr.springbootlearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> allUsers = userDao.selectAllUsers();
        if (!gender.isPresent()) return allUsers;
        try {
            User.Gender theGender = User.Gender.valueOf(gender.get().toUpperCase());
            return allUsers.stream()
                    .filter(user -> user.getGender().equals(theGender))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender.", e);
        }
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

    public int removeUser(UUID userUid) {
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
            userDao.insertUser(User.newUser(user));
            return 1;
        }
        return -1;
    }
}
