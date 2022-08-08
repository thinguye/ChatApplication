package main.services;

import java.util.ArrayList;
import java.util.List;

import main.models.enums.UserType;
import main.models.files.File;
import main.models.messages.Message;
import main.repositories.Repository;
import main.repositories.interfaces.RepositoryInterface;
import main.services.interfaces.GroupServiceInterface;
import main.services.interfaces.MessageServiceInterface;
import main.services.interfaces.UserServiceInterface;

public class MessageService implements MessageServiceInterface{
	UserServiceInterface userService;
	GroupServiceInterface groupService;
	RepositoryInterface repositoryInterface;

	public MessageService() {
		this.userService = new UserService();
		this.groupService = new GroupService();
		this.repositoryInterface = new Repository();
	}
	
	@Override
	public void sendMessageToGroup(String senderId, String receiverId, String content) {
		Message message = new Message(senderId, receiverId, content);
		message.setReceiverType(UserType.GROUP);
		repositoryInterface.addMessage(message);
	}

	@Override
	public void sendMessageToUser(String senderId, String receiverId, String content) {
		Message message = new Message(senderId, receiverId, content);
		message.setReceiverType(UserType.USER);
		repositoryInterface.addMessage(message);
	}

	@Override
	public void sendFileToGroup(String fileName, String filePath, String senderId, String receiverId) {
		File file = new File(fileName, filePath, senderId, receiverId);
		file.setReceiverType(UserType.GROUP);
		repositoryInterface.addFile(file);
	}
	
	@Override
	public void sendFileToUser(String fileName, String filePath, String senderId, String receiverId) {
		File file = new File(fileName, filePath, senderId, receiverId);
		file.setReceiverType(UserType.USER);
		repositoryInterface.addFile(file);
	}

	@Override
	public List<Message> showAllMessageGroup(String groupId, String userId) {
		List<Message> messageList = new ArrayList<>();
		List<Message> tempMessageList = repositoryInterface.getMessageList();
		for (Message message : tempMessageList) {
			if (message.getReceiverType() == UserType.GROUP) {
				if ((message.getReceiverId().equals(groupId)
						&& message.getSenderId().equals(userId))) {
					messageList.add(message);
				}
			}
		}
		return messageList;
	}

	@Override
	public List<Message> showAllMessageUser(String userId1, String userId2) {
		List<Message> userMessageList = new ArrayList<>();
		retrieveUserMessage(userId1, userId2, userMessageList);
		retrieveUserMessage(userId2, userId1, userMessageList);
		userMessageList.sort((m1, m2) -> m1.getId().compareTo(m2.getId()));
		return userMessageList;
	}

	@Override
	public List<Message> retrieveUserMessage(String userId1, String userId2, List<Message> userMessageList) {
		List<Message> messagesList = repositoryInterface.getMessageList();
		for (Message message : messagesList) {
			if (message.getReceiverType() == UserType.USER && message.getSenderId().equals(userId1)
					&& message.getReceiverId().equals(userId2)) {
				userMessageList.add(message);
			}
		}
		return userMessageList;

	}

	@Override
	public List<Message> showKLatestMessageGroup(String groupId, int k) {
		List<Message> groupMessage = new ArrayList<>();
		List<Message> messageList = repositoryInterface.getMessageList();
		for (int i = messageList.size() - 1; i >= 0; i--) {
			Message message = messageList.get(i);
			if (message.getReceiverType() == UserType.GROUP && message.getReceiverId().equals(groupId)) {
				groupMessage.add(message);
				k--;
			}
			if (k == 0) {
				return groupMessage;
			}
		}
		return groupMessage;
	}

	@Override
	public List<Message> showLatestMessageGroupExceptM(String groupId, int numberOfMessages, int m) {
		List<Message> messageList = showKLatestMessageGroup(groupId, numberOfMessages);
		for (int i = 0; i < m; i++) {
			messageList.remove(i);
		}
		return messageList;
	}

	@Override
	public List<File> showAllFileInGroup(String groupId) {
		List<File> fileList = repositoryInterface.getFileList();
		List<File> groupFileList = new ArrayList<>();
		for (File file : fileList) {
			if (file.getReceiverType() == UserType.GROUP && file.getReceiverId().equals(groupId)) {
				groupFileList.add(file);
			}
		}

		return groupFileList;
	}

	@Override
	public boolean deleteMessage(String messageId) {
		List<Message> messageList = repositoryInterface.getMessageList();
		for (int i = 0; i < messageList.size(); i++) {
			if (messageList.get(i).getId().equals(messageId)) {
				messageList.remove(messageList.get(i));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteFile(String fileId) {
		List<File> fileList = repositoryInterface.getFileList();
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getId().equals(fileId)) {
				fileList.remove(fileList.get(i));
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Message> findMessageByKeywordInGroupChat(String groupId, String userId, String keyword) {
		List<Message> groupMessageList = showAllMessageGroup(groupId, userId);
		List<Message> containKeyWordMessages = new ArrayList<>();

		for (Message message : groupMessageList) {
			if (message.getContent().toLowerCase().contains(keyword.toLowerCase())) {
				containKeyWordMessages.add(message);
			}
		}
		return containKeyWordMessages;
	}

	@Override
	public List<Message> findMessageByKeywordInPersonChat(String userId1, String userId2, String keyword) {
		List<Message> userMessageList = showAllMessageUser(userId1, userId2);
		List<Message> containKeywordMessages = new ArrayList<>();
		for (Message message : userMessageList) {
			if (message.getContent().toLowerCase().contains(keyword.toLowerCase())) {
				containKeywordMessages.add(message);
			}
		}
		return containKeywordMessages;
	}
}
