package dev.guilhermebpnr.springbootlearning.service;

import dev.guilhermebpnr.springbootlearning.dao.FakeDataDao;
import dev.guilhermebpnr.springbootlearning.dao.UserDao;
import dev.guilhermebpnr.springbootlearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;
    private FakeDataDao fakeDataDao;

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
            return userDao.updateUser(user);
        }
        throw new NotFoundException("user " + user.getUserUid() + " not found");
    }

    public int removeUser(UUID userUid) {
        Optional<User> optionalUser = getUser(userUid);
        if (optionalUser.isPresent()) {
            return userDao.deleteUserById(userUid);
        }
        throw new NotFoundException("user " + userUid + " not found");
    }

    public int insertUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if (!optionalUser.isPresent()) {
            User newUser = new User (
                user.getUserUid() == null ? UUID.randomUUID() : user.getUserUid(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getAge(),
                user.getEmail()
            );
            return userDao.insertUser(user);
        }
        throw new RuntimeException("user + " + user.getUserUid() + " already exists");
    }
}
