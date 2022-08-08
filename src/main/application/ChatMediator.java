package main.application;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.models.enums.GroupType;
import main.models.groups.Group;
import main.models.messages.Message;
import main.models.users.User;
import main.repositories.Repository;
import main.repositories.interfaces.RepositoryInterface;
import main.services.GroupService;
import main.services.MessageService;
import main.services.UserService;
import main.services.interfaces.GroupServiceInterface;
import main.services.interfaces.MessageServiceInterface;
import main.services.interfaces.UserServiceInterface;

public class ChatMediator {
	private RepositoryInterface repositoryInterface;
	private Scanner scanner = new Scanner(System.in);
	private GroupServiceInterface groupService;
	private UserServiceInterface userService;
	private MessageServiceInterface messageService;
	private User currentUser;

	public ChatMediator() throws NoSuchAlgorithmException, NoSuchProviderException {
		repositoryInterface = new Repository();
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
		userInterface = "----------------------- \n";
		userInterface += "1. Login \n";
		userInterface += "2. Register \n";
		userInterface += "----------------------- \n";
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
		} while (choice >= 2);
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
		userInterface = "----------------------- \n";
		userInterface += "1. Register \n";
		userInterface += "2. Return to Login \n";
		userInterface += "3. Cancel \n";
		userInterface += "----------------------- \n";
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
		userInterface = "----------------------- \n";
		userInterface += "1. Login \n";
		userInterface += "2. Cancel \n";
		userInterface += "----------------------- \n";
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
		userInterface = "----------------------- \n";
		userInterface += "1. One To One \n";
		userInterface += "2. Group Chat\n";
		userInterface += "3. Logout \n";
		userInterface += "----------------------- \n";
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

	private void doChooseGroup() throws NoSuchAlgorithmException, NoSuchProviderException {
		List<Group> groups = repositoryInterface.getGroupList();
		Map<Integer, String> groupMap = new HashMap<>();
		int index = 1;
		String userInterface = "----------------------- \n";
		for (Group group : groups) {
			userInterface += index + ". " + group.getId() + " " + group.getGroupType() + "\n";
			groupMap.put(index, group.getId());
			index++;
		}
		userInterface += index + ". Create group\n";
		userInterface += index + 1 + ". Return\n";
		userInterface += "----------------------- \n";
		userInterface += "Your choice: ";
		System.out.print(userInterface);
		int choice;

		do {
			choice = scanner.nextInt();
			if (groupMap.containsKey(choice)) {
				doGroupChat(groupMap.get(choice), currentUser.getId());
			} else if (choice == groupMap.size() + 1) {
				doCreateGroup();
			} else if (choice == groupMap.size() + 2) {
				doChooseMode();
			}
		} while (choice != groupMap.size() + 2);
	}

	private void doGroupChat(String groupId, String userId) throws NoSuchAlgorithmException, NoSuchProviderException {
		List<Message> messages = messageService.showAllMessageGroup(groupId, userId);
		String messageInterface = "----------------------- \n";
		for (Message message : messages) {
			messageInterface += userService.getUserByUserId(message.getSenderId()).getUsername() + ": "
					+ message.getContent() + "\n";
		}
		messageInterface += "----------------------- \n1. Type message\t2. Send file\t3. Add member\t4. Return";
		System.out.println(messageInterface);
		int choice;
		do {
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				doSendMessageInGroupChat(groupId, userId);
				break;
			case 2:
				doSendFileInGroupChat(groupId, userId);
				break;
			case 3:
				doAddMember(userId, groupId);
			case 4:
				doChooseGroup();
				break;
			default:
				System.out.println("Wrong input!");
				doGroupChat(groupId, userId);
				break;
			}
		} while (choice != 2);

	}

	private void doAddMember(String inviterId, String groupId) throws NoSuchAlgorithmException, NoSuchProviderException {
		Map<Integer, String> userMap = getUserList();
		System.out.println(userMap.size() + 1 + ". Return");
		System.out.println("-----------------------");
		System.out.print("Your choice: ");
		int choice;
		do {
			choice = scanner.nextInt();
			if (userMap.containsKey(choice)) {
				if(groupService.addMember(inviterId, userMap.get(choice), groupId)) {
					System.out.println("Successfully!");
				}else {
					System.out.println("Unsucessfully!");
				}
				doGroupChat(groupId, inviterId);
			} else if (choice == userMap.size() + 1) {
				doGroupChat(groupId, inviterId);
			}
		} while (choice >= userMap.size() + 1);

		
	}

	private void doSendFileInGroupChat(String groupId, String userId)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		boolean isSuccessful = false;
		String filename, filepath;
		while (!isSuccessful) {
			System.out.println("Input your file");
			System.out.print("Filename: ");
			filename = scanner.next();
			System.out.println();
			System.out.print("Filepath: ");
			filepath = scanner.next();
			messageService.sendFileToGroup(filename, filepath, userId, userId);
			isSuccessful = true;
			System.out.println("Successfully!");
		}
		doGroupChat(groupId, userId);
	}

	private void doSendMessageInGroupChat(String groupId, String userId)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		boolean isSuccessful = false;
		System.out.print("Enter your message: ");
		while (!isSuccessful) {
			String contentMessage = scanner.nextLine();
			if (!contentMessage.equals("")) {
				messageService.sendMessageToGroup(userId, groupId, contentMessage);
				isSuccessful = true;
				System.out.println("Succesfully!");
			}
		}
		doGroupChat(groupId, userId);
	}

	private void doCreateGroup() throws NoSuchAlgorithmException, NoSuchProviderException {
		String groupInterface = "----------------------- \n";
		groupInterface += "1. Public Group\n2. Private Group\n";
		groupInterface += "----------------------- \n";
		groupInterface += "Your choice: ";
		System.out.println(groupInterface);
		int choice;
		do {
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				groupService.createGroup(GroupType.PUBLIC, currentUser.getId());
				doChooseGroup();
				break;
			case 2:
				groupService.createGroup(GroupType.PRIVATE, currentUser.getId());
				doChooseGroup();
				break;
			default:
				System.out.println("Wrong input!");
				doCreateGroup();
				break;
			}
		} while (choice >= 2);
	}

	private void doChooseUser() throws NoSuchAlgorithmException, NoSuchProviderException {
		Map<Integer, String> userMap = getUserList();
		System.out.println(userMap.size() + 1 + ". Return");
		int choice;
		do {
			choice = scanner.nextInt();
			if (userMap.containsKey(choice)) {
				doUserChat(currentUser.getId(), userMap.get(choice));
			} else if (choice == userMap.size() + 1) {
				doChooseMode();
			}
		} while (choice >= userMap.size() + 1);

	}

	private void doUserChat(String senderId, String receiverId)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		List<Message> messages = messageService.showAllMessageUser(senderId, receiverId);
		String messageInterface = "----------------------- \n";
		for (Message message : messages) {
			messageInterface += userService.getUserByUserId(message.getSenderId()).getUsername() + ": "
					+ message.getContent() + "\n";
		}
		messageInterface += "----------------------- \n1. Type message\t2. Send file\t3. Return";
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
			case 3:
				doChooseUser();
				break;
			default:
				System.out.println("Wrong input!");
				break;
			}
		} while (choice != 2);

	}

	private void doSendFileInPersonChat(String senderId, String receiverId)
			throws NoSuchAlgorithmException, NoSuchProviderException {
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
		doUserChat(senderId, receiverId);
	}

	private void doSendMessageInPersonChat(String senderId, String receiverId)
			throws NoSuchAlgorithmException, NoSuchProviderException {
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
		doUserChat(senderId, receiverId);
	}

	private Map<Integer, String> getUserList() {
		List<User> users = repositoryInterface.getUserList();
		Map<Integer, String> userMap = new HashMap<>();
		int index = 1;
		String userInterface = "------------------------ \n";
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
		doTask();
	}
}
