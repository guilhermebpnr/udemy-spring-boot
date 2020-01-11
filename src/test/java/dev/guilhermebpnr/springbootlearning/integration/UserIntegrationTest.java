package dev.guilhermebpnr.springbootlearning.integration;

import dev.guilhermebpnr.springbootlearning.clientproxy.UserControllerV1;
import dev.guilhermebpnr.springbootlearning.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserIntegrationTest {

	@Autowired
	private UserControllerV1 userControllerV1;

	@Test
	public void shouldInsertUser() {
		UUID userUid = UUID.randomUUID();
		User expectedUser = new User(
				userUid,
				"Sponge",
				"Bob",
				User.Gender.MALE,
				5,
				"sponge.bob@bikinibottom.com"
		);
		userControllerV1.insertUser(expectedUser);
		User actualUser = userControllerV1.fetchUser(userUid);
		assertThat(actualUser).isEqualToComparingFieldByField(expectedUser);
	}

    @Test
    void shouldDeleteUser() {
		UUID userUid = UUID.randomUUID();
		User expectedUser = new User(
				userUid,
				"Sponge",
				"Bob",
				User.Gender.MALE,
				5,
				"sponge.bob@bikinibottom.com"
		);

		userControllerV1.insertUser(expectedUser);
		User actualUser = userControllerV1.fetchUser(userUid);
		assertThat(actualUser).isEqualToComparingFieldByField(expectedUser);

		userControllerV1.removeUser(userUid);
		assertThatThrownBy(() -> userControllerV1.fetchUser(userUid))
				.isInstanceOf(NotFoundException.class);
	}

	@Test
	void shouldUpdateUser() {
		UUID userUid = UUID.randomUUID();
		User newUser = new User(
				userUid,
				"Sponge",
				"Bob",
				User.Gender.MALE,
				5,
				"sponge.bob@bikinibottom.com"
		);

		userControllerV1.insertUser(newUser);
		User actualUser = userControllerV1.fetchUser(userUid);
		assertThat(actualUser).isEqualToComparingFieldByField(newUser);

		User updatedUser = new User(
				userUid,
				"Mia",
				"Collins",
				User.Gender.MALE,
				5,
				"mia.collins@email.com"
		);

		userControllerV1.updateUser(updatedUser);
		actualUser = userControllerV1.fetchUser(userUid);
		assertThat(actualUser).isEqualToComparingFieldByField(updatedUser);
	}

	@Test
	void shouldFetchUsersByGender() {
		UUID userUid = UUID.randomUUID();
		User user = new User(
				userUid,
				"Sponge",
				"Bob",
				User.Gender.MALE,
				5,
				"sponge.bob@bikinibottom.com"
		);
		userControllerV1.insertUser(user);

		List<User> females = userControllerV1.fetchUsers(User.Gender.FEMALE.name());
		assertThat(females).extracting("userUid").doesNotContain(user.getUserUid());
		assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
		assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
		assertThat(females).extracting("gender").doesNotContain(user.getGender());
		assertThat(females).extracting("age").doesNotContain(user.getAge());
		assertThat(females).extracting("email").doesNotContain(user.getEmail());

		List<User> males = userControllerV1.fetchUsers(User.Gender.MALE.name());
		assertThat(males).extracting("userUid").contains(user.getUserUid());
		assertThat(males).extracting("firstName").contains(user.getFirstName());
		assertThat(males).extracting("lastName").contains(user.getLastName());
		assertThat(males).extracting("gender").contains(user.getGender());
		assertThat(males).extracting("age").contains(user.getAge());
		assertThat(males).extracting("email").contains(user.getEmail());
	}
}
