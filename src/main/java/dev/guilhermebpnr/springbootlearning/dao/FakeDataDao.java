package dev.guilhermebpnr.springbootlearning.dao;

import dev.guilhermebpnr.springbootlearning.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeDataDao implements UserDao {

    private static Map<UUID, User> database;

    static {
        database = new HashMap<>();
        UUID userUid = UUID.randomUUID();
        database.put(userUid, new User(
                userUid,
                "Joe",
                "Jones",
                User.Gender.MALE,
                22,
                "joe.jones@gmail.com"
        ));
    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserById(UUID userUid) {
        return Optional.ofNullable(database.get(userUid));
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }

    @Override
    public int deleteUserById(UUID userUid) {
        database.remove(userUid);
        return 1;
    }

    @Override
    public int insertUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }
}
