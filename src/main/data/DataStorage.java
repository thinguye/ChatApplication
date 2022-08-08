package main.data;

import java.util.ArrayList;
import java.util.List;
import main.models.files.File;
import main.models.groups.Group;
import main.models.messages.Message;
import main.models.users.User;

public class DataStorage {
    private static DataStorage data;
    private List<User> userList;
    private List<Group> groupList;
    private List<File> fileList;
    private List<Message> messageList;

    private DataStorage() {
        userList = new ArrayList<>();
        groupList = new ArrayList<>();
        fileList = new ArrayList<>();
        messageList = new ArrayList<>();
    }

    public static DataStorage getData() {
        if (data == null) {
            data = new DataStorage();
        }
        return data;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public void addFile(File file) {
        fileList.add(file);
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public void addGroup(Group group) {
        groupList.add(group);
    }
    
}
