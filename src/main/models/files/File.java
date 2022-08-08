package main.models.files;

import main.models.enums.FileType;
import main.models.enums.UserType;

public class File {
	
	private static int count = 0;
	private String id;
	private String fileName;
	private String filePath;
	private FileType fileType;
	private String senderId;
	private String receiverId;
	private UserType receiverType;

	public File(String fileName, String filePath, String senderId, String receiverId) {
		id = "file" + count++;
		this.fileName = fileName;
		this.filePath = filePath;
		this.senderId = senderId;
		this.receiverId = receiverId;
		if (filePath.endsWith("png") || filePath.endsWith("jpg")||filePath.endsWith("jpeg")) {
			fileType = FileType.IMAGE;
		}else if (filePath.endsWith("mp3")) {
			fileType = FileType.AUDIO;
		} else if (filePath.endsWith("mp4")) {
			fileType = FileType.VIDEO;
		} 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
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

}
