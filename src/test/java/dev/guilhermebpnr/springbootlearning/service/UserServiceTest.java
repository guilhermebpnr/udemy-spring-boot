package dev.guilhermebpnr.springbootlearning.service;

import dev.guilhermebpnr.springbootlearning.dao.FakeDataDao;
import dev.guilhermebpnr.springbootlearning.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    FakeDataDao fakeDataDao;

    private UserService userService;

    private String[] maleNames = {"John", "Paul", "George", "Patrick", "Mark",
            "Robert", "Walt", "Richard", "Charles", "William"};

    private String[] femaleNames = {"Julia", "Agata", "Ema", "Elle", "Mary",
            "Adel", "Lucy", "Amanda", "Barbara", "Joan"};

    private String[] lastNames = {"Thompson", "Greenford", "Watson", "Mueller", "Ford",
            "Carvalho", "Wood", "Hoffmann", "Peterson", "Franken"};

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);

    }

    @Test
    void shouldGetAllUsers() {
        List<User> mockedUsersList = new ArrayList<>();
        mockedUsersList.add(getRandomUser());
        given(fakeDataDao.selectAllUsers()).willReturn(mockedUsersList);
        List<User> allUsers = userService.getAllUsers(Optional.empty());
        assertThat(allUsers.size()).isEqualTo(1);
        assertThat(allUsers.get(0)).isEqualToComparingFieldByField(mockedUsersList.get(0));
    }

    @Test
    void shouldGetAllUsersByGender() {
        List<User> mockedUsersList = new ArrayList<>();
        mockedUsersList.add(getGenderedUser(User.Gender.MALE));
        mockedUsersList.add(getGenderedUser(User.Gender.MALE));
        mockedUsersList.add(getGenderedUser(User.Gender.MALE));
        mockedUsersList.add(getGenderedUser(User.Gender.FEMALE));
        mockedUsersList.add(getGenderedUser(User.Gender.FEMALE));
        given(fakeDataDao.selectAllUsers()).willReturn(mockedUsersList);

        List<User> maleUsers = userService.getAllUsers(Optional.of("MALE"));
        List<User> femaleUsers = userService.getAllUsers(Optional.of("female"));

        assertThat(maleUsers.size()).isEqualTo(3);
        assertThat(femaleUsers.size()).isEqualTo(2);
    }

    @Test
    void shouldThrowExceptionWhenGenderInvalid() {
        assertThatThrownBy(() -> userService.getAllUsers(Optional.of("XXXX")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid gender.");
    }

    @Test
    void shouldGetUser() {
        User mockedUser = getRandomUser();
        given(fakeDataDao.selectUserById(mockedUser.getUserUid())).willReturn(Optional.of(mockedUser));

        Optional<User> userOptional = userService.getUser(mockedUser.getUserUid());
        assertThat(userOptional.isPresent()).isTrue();
        assertThat(userOptional.get()).isEqualToComparingFieldByField(mockedUser);
    }

    @Test
    void shouldUpdateUser() {
        User mockedUser = getRandomUser();
        given(fakeDataDao.selectUserById(mockedUser.getUserUid())).willReturn(Optional.of(mockedUser));
        given(fakeDataDao.updateUser(mockedUser)).willReturn(1);
        assertThat(userService.updateUser(mockedUser)).isEqualTo(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(fakeDataDao).selectUserById(mockedUser.getUserUid());
        verify(fakeDataDao).updateUser(captor.capture());
        assertThat(captor.getValue()).isEqualToComparingFieldByField(mockedUser);
    }

    @Test
    void shouldRemoveUser() {
        User mockedUser = getRandomUser();
        given(fakeDataDao.selectUserById(mockedUser.getUserUid())).willReturn(Optional.of(mockedUser));
        given(fakeDataDao.deleteUserById(mockedUser.getUserUid())).willReturn(1);
        assertThat(userService.removeUser(mockedUser.getUserUid())).isEqualTo(1);
    }

    @Test
    void shouldInsertUser() {
        User mockedUser = getRandomUser();
        given(fakeDataDao.insertUser(mockedUser)).willReturn(1);
        assertThat(userService.insertUser(mockedUser)).isEqualTo(1);
    }

    @Test
    void shouldNotInsertUserWhenUserAlreadyExists() {
        User mockedUser = getRandomUser();
        given(fakeDataDao.selectUserById(mockedUser.getUserUid())).willReturn(Optional.of(mockedUser));
        assertThat(userService.insertUser(mockedUser)).isEqualTo(-1);
    }

    private User getGenderedUser(User.Gender gender) {
        String name;
        if (gender == User.Gender.MALE) {
            name = maleNames[new Random().nextInt(10)];
        } else {
            name = femaleNames[new Random().nextInt(10)];
        }
        String lastName = lastNames[new Random().nextInt(10)];
        String email = (name + "." + lastName + "@email.com").toLowerCase();
        return new User(
                UUID.randomUUID(),
                name,
                lastName,
                gender,
                new Random().nextInt(100) + 18,
                email
        );
    }

    private User getRandomUser() {
        return new User(
                UUID.randomUUID(),
                "Charles",
                "Charleston",
                User.Gender.MALE,
                28,
                "chl.chlston@email.com"
        );
    }
}