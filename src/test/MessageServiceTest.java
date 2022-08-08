package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.data.DataStorage;
import main.services.GroupService;
import main.services.MessageService;
import main.services.UserService;
import main.services.interfaces.GroupServiceInterface;
import main.services.interfaces.MessageServiceInterface;
import main.services.interfaces.UserServiceInterface;
import main.models.enums.GroupType;
import main.models.files.File;
import main.models.groups.Group;
import main.models.messages.Message;

class MessageServiceTest {
	DataStorage dataStorage;
	UserServiceInterface userService = new UserService();
	GroupServiceInterface groupService = new GroupService();
	MessageServiceInterface messageService = new MessageService();
	String contentMessage1 = "Hello";
	String contentMessage2 = "Hi";
	String fileName = "Avatar";
	String filePath = "avatar.mp4";

	@BeforeEach
	void setUp() throws Exception {
		dataStorage = DataStorage.getData();
		userService.createNewUser("Thi", "Nguyen", "thinguyen", "123456789", "Female", "24032000");
		userService.createNewUser("Vu", "Pham", "vupham", "999999999", "Male", "13031999");
		userService.createNewUser("Bo", "Tran", "bobungbu", "000000000", "Female", "09081999");
		groupService.createGroup(GroupType.PRIVATE, "user0");
		groupService.createGroup(GroupType.PUBLIC, "user0");
		groupService.createGroup(GroupType.PRIVATE, "user1");
	}

	@Test
	public void sendMessageToGroupTest() {
		messageService.sendMessageToGroup("user1", "group0", contentMessage1);
		List<Message> messageList = dataStorage.getMessageList();
		assertEquals("Hello", messageList.get(messageList.size() - 1).getContent());
	}

	@Test
	public void sendMessageToUserTest() {
		messageService.sendMessageToUser("user1", "user0", contentMessage1);
		List<Message> messageList = dataStorage.getMessageList();
		assertEquals("Hello", messageList.get(messageList.size() - 1).getContent());
	}

	@Test
	public void sendFileToUserTest() {
		messageService.sendFileToUser(fileName, filePath, "user0", "user1");
		List<File> fileList = dataStorage.getFileList();
		assertEquals("Avatar", fileList.get(fileList.size() - 1).getFileName());
	}

	@Test
	public void sendFileToGroupTest() {
		messageService.sendFileToGroup(fileName, filePath, "user1", "group0");
		List<File> fileList = dataStorage.getFileList();
		assertEquals("Avatar", fileList.get(fileList.size() - 1).getFileName());
	}

	@Test
	public void showAllMessageGroupTest() {
		for (int i = 0; i < 4; i++) {
			messageService.sendMessageToGroup("user0", "group0", contentMessage1);
		}
		List<Message> messageList = messageService.showAllMessageGroup("group0", "user0");
		assertEquals(4, messageList.size());

	}

	@Test
	public void showAllMessageUserTest() {
		for (int i = 0; i < 12; i++) {
			messageService.sendMessageToUser("user1", "user0", contentMessage1);

		}
		List<Message> messageList = messageService.showAllMessageUser("user0", "user1");
		assertEquals(12, messageList.size());
	}

	@ParameterizedTest(name = "k = {0}, expected = {1}")
	@CsvSource({ "3, 3", "100, 24" })
	public void showKLatestMessageGroupTest(int k, int expected) {

		for (int i = 0; i < 10; i++) {
			messageService.sendMessageToGroup("user0", "group0", contentMessage1);
		}
		List<Message> messageList = messageService.showKLatestMessageGroup("group0", k);
		assertEquals(expected, messageList.size());

	}

	@Test
	public void showLatestMessageGroupExceptMTest() {

		for (int i = 0; i < 10; i++) {
			messageService.sendMessageToGroup("user0", "group0", contentMessage1);
		}
		List<Message> messageList = messageService.showLatestMessageGroupExceptM("group0", 3, 1);
		assertEquals(2, messageList.size());

	}

	@Test
	public void showAllFileInGroupTest() {
		Group group = groupService.getGroupByGroupId("group0");
		for (int i = 0; i < 2; i++) {
			messageService.sendFileToGroup(fileName, filePath, "user0", "group0");
		}
		List<File> fileList = messageService.showAllFileInGroup(group.getId());
		assertEquals(2, fileList.size());

	}

	@Test
	public void deleteMessageTest1() {
		for (int i = 0; i < 2; i++) {
			messageService.sendMessageToGroup("user1", "group2", contentMessage1);
		}
		assertTrue(messageService.deleteMessage("message0"));
	}

	@Test
	public void deleteMessageTest2() {
		for (int i = 0; i < 2; i++) {
			messageService.sendMessageToGroup("user1", "group2", contentMessage1);
		}
		assertFalse(messageService.deleteMessage("message100"));
	}

	@Test
	public void deleteFileTest1() {
		for (int i = 0; i < 2; i++) {
			messageService.sendFileToGroup(fileName, filePath, "file1", "group1");
		}
		assertTrue(messageService.deleteFile("file0"));
	}

	@Test
	public void deleteFileTest2() {
		for (int i = 0; i < 2; i++) {
			messageService.sendFileToGroup(fileName, filePath, "file1", "group1");
		}
		assertFalse(messageService.deleteFile("file4"));
	}

	@Test
	public void findMessageByKeywordInGroupChatTest() {
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToGroup("user1", "group2", contentMessage1);
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToGroup("user1", "group2", contentMessage2);
		}
		List<Message> messageList = messageService.findMessageByKeywordInGroupChat("group2", "user1", "Hi");
		assertEquals(3, messageList.size());
	}

	@Test
	public void findMessageByKeywordInPersonChatTest() {
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser("user0", "user1", contentMessage1);
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser("user0", "user2", contentMessage2);
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser("user1", "user0", contentMessage2);
		}

		List<Message> messageList = messageService.findMessageByKeywordInPersonChat("user0", "user1", "Hi");
		assertEquals(3, messageList.size());
	}

}
