package main.repositories;

import java.util.List;

import main.data.DataStorage;
import main.models.files.File;
import main.models.groups.Group;
import main.models.messages.Message;
import main.models.users.User;
import main.repositories.interfaces.RepositoryInterface;

public class Repository implements RepositoryInterface{
	DataStorage dataStorage;
	
	public Repository() {
		this.dataStorage = DataStorage.getData();
	}
	
	@Override
	public void addGroup(Group group) {
		dataStorage.addGroup(group);
		
	}

	@Override
	public List<Group> getGroupList() {
		return dataStorage.getGroupList();
	}

	@Override
	public void addUser(User user) {
		dataStorage.addUser(user);
	}

	@Override
	public List<User> getUserList() {
		return dataStorage.getUserList();
	}

	@Override
	public void addMessage(Message message) {
		dataStorage.addMessage(message);
		
	}

	@Override
	public void addFile(File file) {
		dataStorage.addFile(file);
	}

	@Override
	public List<Message> getMessageList() {
		return dataStorage.getMessageList();
	}

	@Override
	public List<File> getFileList() {
		return dataStorage.getFileList();
	}

}
