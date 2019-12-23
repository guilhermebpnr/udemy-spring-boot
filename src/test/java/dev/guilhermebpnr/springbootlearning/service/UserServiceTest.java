package dev.guilhermebpnr.springbootlearning.service;

import dev.guilhermebpnr.springbootlearning.dao.FakeDataDao;
import dev.guilhermebpnr.springbootlearning.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    FakeDataDao fakeDataDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> mockedUsersList = getMockedUsersList();
        given(fakeDataDao.selectAllUsers()).willReturn(mockedUsersList);
        List<User> allUsers = userService.getAllUsers();
        assertThat(allUsers.size()).isEqualTo(1);
        assertThat(allUsers.get(0)).isEqualToComparingFieldByField(mockedUsersList.get(0));
    }

    private List<User> getMockedUsersList() {
        User user = getRandomUser();
        List<User> mockedUsersList = new ArrayList<>();
        mockedUsersList.add(user);
        return mockedUsersList;
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