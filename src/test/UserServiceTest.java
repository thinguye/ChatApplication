package test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.data.DataStorage;
import main.services.UserService;
import main.services.interfaces.UserServiceInterface;
import main.models.users.User;

class UserServiceTest {

	DataStorage dataStorage;
	UserServiceInterface userService = new UserService();
	@BeforeEach
	void setUp() throws Exception {
		dataStorage = DataStorage.getData();
		userService.createNewUser("Thi", "Nguyen", "thinguyen", "12345678", "Female", "24032000");
		userService.createNewUser("Vu", "Pham", "vupham", "999999999", "Male", "13031999");
		userService.createNewUser("Bo", "Tran", "bobungbu", "000000000", "Other", "09081999");
	}

	@Test
	public void addUserTest() throws NoSuchAlgorithmException, NoSuchProviderException {
		assertTrue(userService.createNewUser("Thanh", "Pham", "thanhpham", "999999999", "Male", "13031999"));
	}

	@Test
	public void addExistUserTest() throws NoSuchAlgorithmException, NoSuchProviderException {
		assertFalse(userService.createNewUser("Thi", "Nguyen", "thinguyen", "12345678", "Female", "24032000"));
	}
	
	@Test
	public void loginWithTruePasswordTest() {
		assertTrue(userService.login("thinguyen", "12345678"));
	}

	@Test
	public void loginWithWrongPasswordTest() {
		assertFalse(userService.login("vupham", "123456780"));
	}

	@Test
	public void loginNoExistTest() {
		assertFalse(userService.login("thinguenn", "12345678"));
	}

	@Test
	public void checkHashPasswordtest() {
		assertTrue(userService.checkHashPassword("thinguyen", "12345678"));
	}

	@Test
	public void getUserByUsernameTest() {
		User user = userService.getUserByUsername("vupham");
		assertEquals("user1", user.getId());
	}

	@Test
	public void getUserByIDTest() {
		User user = userService.getUserByUserId("user0");
		assertEquals("thinguyen", user.getUsername());
	}

	@Test
	public void setAliastest() {
		User user1 = userService.getUserByUsername("thinguyen");
		User user2 = userService.getUserByUsername("vupham");
		assertTrue(userService.setAlias(user1, user2, "Code Practice"));
	}

}
