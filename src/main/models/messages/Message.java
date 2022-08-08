package main.models.messages;

import main.models.enums.UserType;

public class Message {

	private static int count = 0;
	private String id;
	private String content;
	private String senderId;
	private String receiverId;
	private UserType receiverType;

	public Message(String senderId, String receiverId, String content) {
		id = "message" + count++;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public UserType getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(UserType receiverType) {
		this.receiverType = receiverType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}