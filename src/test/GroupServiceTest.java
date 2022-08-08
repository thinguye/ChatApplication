package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.data.DataStorage;
import main.models.enums.GroupType;
import main.services.GroupService;
import main.services.UserService;
import main.services.interfaces.GroupServiceInterface;
import main.services.interfaces.UserServiceInterface;
import main.models.groups.Group;
import main.models.messages.Message;
import main.models.users.User;

class GroupServiceTest {
	GroupServiceInterface groupService = new GroupService();
	UserServiceInterface userService = new UserService();
	DataStorage dataStorage;

	@BeforeEach
	void setUp() throws Exception {
		dataStorage = DataStorage.getData();
		userService.createNewUser("Son", "Goku", "thinguyen", "123456789", "Male", "11031999");
		userService.createNewUser("Uzimaki", "Naruto", "vupham", "999999999", "Male", "13031999");
		userService.createNewUser("Dragon.D", "Luffy", "bobungbu", "000000000", "Female", "09081999");
		groupService.createGroup(GroupType.PRIVATE, "user0");
		groupService.createGroup(GroupType.PUBLIC, "user0");
		groupService.createGroup(GroupType.PRIVATE, "user1");
		
	}

	@Test
	public void addPrivateGroupTest() {
		assertTrue(groupService.createGroup(GroupType.PRIVATE, "1"));
	}

	@Test
	public void addPublicGroupTest() {
		assertTrue(groupService.createGroup(GroupType.PUBLIC, "1"));
	}
	
	@Test
	public void addPrivateMemberTest() {
		User user1 = userService.getUserByUsername("thinguyen");
		User user2 = userService.getUserByUsername("vupham");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), "group0"));
	}

	@Test
	public void addPublicMemberTest1() {
		User user1 = userService.getUserByUsername("thinguyen");
		User user2 = userService.getUserByUsername("vupham");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), "group1"));
	}

	@Test
	public void addPublicMemberTest2() {
		User user1 = userService.getUserByUsername("thinguyen");
		User user2 = userService.getUserByUsername("vupham");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), "group0"));
	}

	@Test
	public void addMemberByInviteCodeTest1() {
		User user1 = userService.getUserByUsername("thinguyen");
		assertTrue(
				groupService.addMemberByInviteCode(dataStorage.getGroupList().get(1).getInviteCode(), user1.getId()));
	}

	@ParameterizedTest(name = "groupId = {0}, userName = {1}, expected = {2}")
	@CsvSource({"group0, thinguyen, true", "group0, bobungbu, false " , "group09090, thinguyen, false"})
	public void checkAdminPrivateGroupTest(String groupId, String userName, boolean expected) {
		User user1 = userService.getUserByUsername(userName);
		assertEquals(expected, groupService.checkMemberGroup(user1.getId(), groupId));
	}

	@Test
	public void leaveGroupTest() {
		User user1 = userService.getUserByUsername("thinguyen");
		Group group = groupService.getGroupByGroupId("group0");
		assertTrue(groupService.leaveGroup(user1.getId(), group.getId()));
	}

}
