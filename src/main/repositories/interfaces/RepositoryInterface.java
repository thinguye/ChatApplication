package main.repositories.interfaces;

import java.util.List;

import main.models.files.File;
import main.models.groups.Group;
import main.models.groups.PrivateGroup;
import main.models.messages.Message;
import main.models.users.User;

public interface RepositoryInterface {

	void addGroup(Group group);

	List<Group> getGroupList();

	void addUser(User user);

	List<User> getUserList();

	void addMessage(Message message);

	void addFile(File file);

	List<Message> getMessageList();

	List<File> getFileList();

}
