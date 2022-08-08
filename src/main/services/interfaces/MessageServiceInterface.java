package main.services.interfaces;

import java.util.List;

import main.models.files.File;
import main.models.messages.Message;

public interface MessageServiceInterface {

	void sendMessageToGroup(String senderId, String receiverId, String content);

	void sendMessageToUser(String senderId, String receiverId, String content);

	void sendFileToGroup(String fileName, String filePath, String senderId, String receiverId);

	void sendFileToUser(String fileName, String filePath, String senderId, String receiverId);

	List<Message> showAllMessageGroup(String groupId, String userId);

	List<Message> showAllMessageUser(String userId1, String userId2);

	List<Message> retrieveUserMessage(String userId1, String userId2, List<Message> userMessageList);

	List<Message> showKLatestMessageGroup(String groupId, int k);

	List<Message> showLatestMessageGroupExceptM(String groupId, int numberOfMessages, int m);

	List<File> showAllFileInGroup(String groupId);

	boolean deleteMessage(String messageId);

	boolean deleteFile(String fileId);

	List<Message> findMessageByKeywordInGroupChat(String groupId, String userId, String keyword);

	List<Message> findMessageByKeywordInPersonChat(String userId1, String userId2, String keyword);

}
