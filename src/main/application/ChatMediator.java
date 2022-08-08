package main.application;

import static org.hamcrest.CoreMatchers.is;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import main.data.DataStorage;
import main.models.enums.GroupType;
import main.models.groups.Group;
import main.models.messages.Message;
import main.models.users.User;
import main.services.GroupService;
import main.services.MessageService;
import main.services.UserService;
import main.services.interfaces.GroupServiceInterface;
import main.services.interfaces.MessageServiceInterface;
import main.services.interfaces.UserServiceInterface;

public class ChatMediator {
	private DataStorage dataStorage;
	private Scanner scanner = new Scanner(System.in);
	private GroupServiceInterface groupService;
	private UserServiceInterface userService;
	private MessageServiceInterface messageService;
	private User currentUser;

	public ChatMediator() throws NoSuchAlgorithmException, NoSuchProviderException {
		dataStorage = DataStorage.getData();
		groupService = new GroupService();
		userService = new UserService();
		userService.createNewUser("Thi", "Nguyen", "thinguyen", "12345678", "Female", "24032000");
		userService.createNewUser("Vu", "Pham", "vupham", "12345678", "Male", "19011999");
		groupService.createGroup(GroupType.PUBLIC, "user0");
		messageService = new MessageService();
		messageService.sendMessageToUser("user0", "user1", "Hello");
		messageService.sendMessageToUser("user0", "user1", "How are you?");
		messageService.sendMessageToUser("user1", "user0", "I'm fine");
		messageService.sendMessageToUser("user1", "user0", "Bye");
		doTask();
	}

	public void openConversation() {
		groupService = new GroupService();
		userService = new UserService();
		messageService = new MessageService();
	}

	private void getUserInterface() {
		String userInterface;
		userInterface = "######################## \n";
		userInterface += "1. Login \n";
		userInterface += "2. Register \n";
		userInterface += "####################### \n";
		userInterface += "Your choice: ";
		System.out.print(userInterface);
	}

	public void doTask() throws NoSuchAlgorithmException, NoSuchProviderException {
		int choice;
		do {
			getUserInterface();
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				doLogin();
				break;
			case 2:
				doRegister();
				break;
			default:
				System.out.println("Wrong input");
			}
		} while (choice != 2);
	}

	private void doRegister() throws NoSuchAlgorithmException, NoSuchProviderException {
		String firstName, lastName, username, password, gender, dateOfBirth;

		System.out.println("REGISTER");
		System.out.print("First name: ");
		firstName = scanner.next().trim();
		System.out.print("\nLast name: ");
		lastName = scanner.next().trim();
		System.out.print("\nUsername: ");
		username = scanner.next().trim();
		System.out.print("\nPassword: ");
		password = scanner.next().trim();
		System.out.print("\nGender (Male, Female or Other): ");
		gender = scanner.next().trim();
		System.out.print("\nDate Of Birth (DDMMYYYY): ");
		dateOfBirth = scanner.next().trim();

		String userInterface;
		userInterface = "######################## \n";
		userInterface += "1. Register \n";
		userInterface += "2. Return to Login \n";
		userInterface += "3. Cancel \n";
		userInterface += "####################### \n";
		userInterface += "Your choice: ";
		System.out.print(userInterface);
		int choice;
		do {
			choice = scanner.nextInt();
			System.out.print(userInterface);
			switch (choice) {
			case 1:
				if (userService.createNewUser(firstName, lastName, username, password, gender, dateOfBirth)) {
					doLogin();
				} else {
					System.out.println("Wrong input");
					doRegister();
				}
				break;
			case 2:
				getUserInterface();
				break;
			default:
				System.out.println("Wrong input");
			}
		} while (choice != 2);
	}

	private void doLogin() throws NoSuchAlgorithmException, NoSuchProviderException {

		System.out.println("LOGIN");
		System.out.print("Username: ");
		String username = scanner.next().trim();
		System.out.print("\nPassword: ");
		String password = scanner.next().trim();

		String userInterface;
		userInterface = "######################## \n";
		userInterface += "1. Login \n";
		userInterface += "2. Cancel \n";
		userInterface += "####################### \n";
		userInterface += "Your choice: ";
		System.out.print(userInterface);
		int choice;
		do {
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				if (userService.login(username, password)) {
					currentUser = userService.getUserByUsername(username);
					doChooseMode();
				} else {
					doLogin();
				}
				break;
			case 2:
				doTask();
				break;
			default:
				System.out.println("Wrong input");
			}
		} while (choice != 2);
	}

	private void doChooseMode() throws NoSuchAlgorithmException, NoSuchProviderException {
		String userInterface;
		userInterface = "######################## \n";
		userInterface += "1. One To One \n";
		userInterface += "2. Group \n";
		userInterface += "3. Logout \n";
		userInterface += "####################### \n";
		userInterface += "Your choice: ";
		System.out.print(userInterface);

		int choice;

		do {
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				doChooseUser();
				break;
			case 2:
				doChooseGroup();
				break;
			case 3:
				doLogout();
				break;
			default:
				break;
			}
		} while (choice != 3);
	}

	private void doChooseGroup() {
		List<Group> groups = dataStorage.getGroupList();
		Map<Integer, String> groupMap = new HashMap<>();
		int index = 1;
		String userInterface = "############### \n";
		for (Group group : groups) {
			userInterface += index + ". " + group.getId() + " " + group.getGroupType() + "\n";
			groupMap.put(index, group.getId());
			index++;
		}
		userInterface += index + ". Create group\n";
		userInterface += "Your choice: ";
		System.out.print(userInterface);
		int choice;

		do {
			choice = scanner.nextInt();
			if (groupMap.containsKey(choice)) {
				doGroupChat();
			} else if (choice == groupMap.size()) {
				doCreateGroup();
			}
		} while (choice != groupMap.size() || choice <= 0);
	}

	private void doGroupChat() {

	}

	private void doCreateGroup() {
		// TODO Auto-generated method stub

	}

	private void doChooseUser() {
		Map<Integer, String> userMap = getUserList();
		int choice;
		do {
			choice = scanner.nextInt();
			if (userMap.containsKey(choice)) {
				doChat(currentUser.getId(), userMap.get(choice));
			} else {
				System.out.println("Wrong input");
			}
		} while (choice >= userMap.size());

	}

	private void doChat(String senderId, String receiverId) {
		List<Message> messages = messageService.showAllMessageUser(senderId, receiverId);
		String messageInterface = "--------------------------\n";
		for (Message message : messages) {
			messageInterface += userService.getUserByUserId(message.getSenderId()).getUsername() + ": "
					+ message.getContent() + "\n";
		}
		messageInterface += "--------------------------\n1. Type message\t2. Send file";
		System.out.println(messageInterface);
		int choice;
		do {
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				doSendMessageInPersonChat(senderId, receiverId);
				break;
			case 2:
				doSendFileInPersonChat(senderId, receiverId);
				break;
			default:
				break;
			}
		} while (choice != 2);

	}

	private void doSendFileInPersonChat(String senderId, String receiverId) {
		boolean isSuccessful = false;
		String filename, filepath;
		while (!isSuccessful) {
			System.out.println("Input your file");
			System.out.print("Filename: ");
			filename = scanner.next();
			System.out.println();
			System.out.print("Filepath: ");
			filepath = scanner.next();
			messageService.sendFileToUser(filename, filepath, senderId, receiverId);
			isSuccessful = true;
			System.out.println("Successfully!");
		}
		doChat(senderId, receiverId);
	}

	private void doSendMessageInPersonChat(String senderId, String receiverId) {
		boolean isSuccessful = false;
		System.out.print("Enter your message: ");
		while (!isSuccessful) {
			String contentMessage = scanner.nextLine();
			if (!contentMessage.equals("")) {
				messageService.sendMessageToUser(senderId, receiverId, contentMessage);
				isSuccessful = true;
				System.out.println("Succesfully!");
			}
		}
		doChat(senderId, receiverId);
	}

	private Map<Integer, String> getUserList() {
		List<User> users = dataStorage.getUserList();
		Map<Integer, String> userMap = new HashMap<>();
		int index = 1;
		String userInterface = "################ \n";
		for (User user : users) {
			if (!user.getUsername().equals(currentUser.getUsername())) {
				userInterface += index + ". " + user.getUsername() + "\n";
				userMap.put(index, user.getId());
				index++;
			}
		}
		System.out.print(userInterface);
		return userMap;
	}

	private void doLogout() throws NoSuchAlgorithmException, NoSuchProviderException {
		String userInterface = "Do you want to log out? \n";
		userInterface += "---------------------- \n";
		userInterface += "1. Yes \n";
		userInterface += "2. No \n";
		userInterface += "---------------------- \n";
		userInterface += "Your choice: ";
		System.out.print(userInterface);

		int choice;

		do {
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				doTask();
				break;
			case 2:
				break;
			default:
				break;
			}
		} while (choice != 2);
	}

}
