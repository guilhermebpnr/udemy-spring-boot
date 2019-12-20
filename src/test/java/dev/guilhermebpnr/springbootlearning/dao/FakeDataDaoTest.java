package dev.guilhermebpnr.springbootlearning.dao;

import com.sun.tools.javah.Gen;
import dev.guilhermebpnr.springbootlearning.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;

    @BeforeEach
    void setUp() {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    void shouldSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);

        User joe = users.get(0);

        assertThat(joe.getFirstName()).isEqualTo("Joe");
        assertThat(joe.getLastName()).isEqualTo("Jones");
        assertThat(joe.getAge()).isEqualTo(22);
        assertThat(joe.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(joe.getEmail()).isEqualTo("joe.jones@gmail.com");
        assertThat(joe.getUserUid()).isNotNull();
    }

    @Test
    void shouldSelectUserById() {
        User anna = new User(
                UUID.randomUUID(),
                "Anna",
                "Anastacia",
                User.Gender.FEMALE,
                30,
                "anna.anastacia@gmail.com"
        );
        fakeDataDao.insertUser(anna);
        User selectedAnna = fakeDataDao.selectUserById(anna.getUserUid()).get();
        assertThat(selectedAnna).isEqualToComparingFieldByField(anna);
    }

    @Test
    void shouldNotSelectUserByRandomId() {
        Optional<User> optionalUser = fakeDataDao.selectUserById(UUID.randomUUID());
        assertThat(optionalUser.isPresent()).isFalse();
    }

    @Test
    void shouldUpdateUser() {
        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        User updatedJoe = new User(
                joeUserUid,
                "Joseph",
                "Jones",
                User.Gender.MALE,
                23,
                "joseph.jones@gmail.com"
        );
        fakeDataDao.updateUser(updatedJoe);
        User selectedJoe = fakeDataDao.selectUserById(joeUserUid).get();
        assertThat(selectedJoe).isEqualToComparingFieldByField(updatedJoe);
    }

    @Test
    void deleteUserById() {
        UUID userUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        fakeDataDao.deleteUserById(userUid);
        Optional<User> deletedUser = fakeDataDao.selectUserById(userUid);
        assertThat(deletedUser.isPresent()).isFalse();
    }

    @Test
    void insertUser() {
        int preInsertSize = fakeDataDao.selectAllUsers().size();
        UUID insertedUserUid = UUID.randomUUID();
        User insertedUser = new User(
                insertedUserUid,
                "Jack",
                "Jackson",
                User.Gender.MALE,
                30,
                "jack.jackson@hotmail.com"
        );
        fakeDataDao.insertUser(insertedUser);
        User selectedUser = fakeDataDao.selectUserById(insertedUserUid).get();
        assertThat(selectedUser).isEqualToComparingFieldByField(insertedUser);
        assertThat(fakeDataDao.selectAllUsers().size()).isEqualTo(preInsertSize + 1);
    }
}