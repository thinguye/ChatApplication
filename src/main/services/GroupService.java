package main.services;

import main.models.groups.*;
import main.models.enums.*;
import main.models.users.*;
import main.repositories.Repository;
import main.repositories.interfaces.RepositoryInterface;
import main.services.interfaces.GroupServiceInterface;
import main.services.interfaces.UserServiceInterface;

public class GroupService implements GroupServiceInterface{
	RepositoryInterface repositoryInterface;
	UserServiceInterface userServiceInterface;

	public GroupService() {
		this.repositoryInterface = new Repository();
		this.userServiceInterface = new UserService();
	}

	@Override
	public boolean createGroup(GroupType groupType, String userId) {
		User tempUser = userServiceInterface.getUserByUserId(userId);
		if (groupType.equals(GroupType.PRIVATE)) {
			PrivateGroup group = new PrivateGroup();
			group.addAdmin(tempUser);
			group.setCreator(tempUser);
			group.addMember(tempUser);
			repositoryInterface.addGroup(group);
			return true;
		} else {
			PublicGroup group = new PublicGroup();
			group.addAdmin(tempUser);
			group.setCreator(tempUser);
			group.addMember(tempUser);
			repositoryInterface.addGroup(group);
			return true;
		}
	}

	@Override
	public boolean addMember(String inviterId, String inviteeId, String groupId) {
		User inviteeUser = userServiceInterface.getUserByUserId(inviteeId);
		Group group = getGroupByGroupId(groupId);
		if (group.getGroupType().equals(GroupType.PRIVATE)) {
			if (checkAdminPrivateGroup(inviterId, groupId)) {
				group.addMember(inviteeUser);
				return true;
			}
		} else if (checkMemberGroup(inviterId, groupId)) {
			group.addMember(inviteeUser);
			return true;
		}
		return false;
	}

	@Override
	public boolean addMemberByInviteCode(String inviteCode, String userId) {
		User user = userServiceInterface.getUserByUserId(userId);
		Group group = getGroupByInviteCode(inviteCode);
		if (group != null) {
			group.addMember(user);
			return true;
		}
		return false;
	}

	private boolean checkAdminPrivateGroup(String userId, String groupId) {
		Group group = getGroupByGroupId(groupId);
		User admin = userServiceInterface.getUserByUserId(userId);
		if (group != null) {
			if (group.getAdminList().contains(admin)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean checkMemberGroup(String userId, String groupId) {
		Group group = getGroupByGroupId(groupId);
		User user = userServiceInterface.getUserByUserId(userId);
		if (group != null) {
			if (group.getMemberList().contains(user)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public boolean leaveGroup(String userId, String groupId) {
		User user = userServiceInterface.getUserByUserId(userId);
		boolean result = false;
		Group group = getGroupByGroupId(groupId);
		if (checkMemberGroup(userId, groupId)) {
			group.removeMember(user);
			result = true;
		}
		if (checkAdminPrivateGroup(userId, groupId)) {
			group.removeAdmin(user);
			result = true;
		}
		return result;
	}

	@Override
	public Group getGroupByGroupId(String id) {
		Group tempGroup = null;
		for (Group group : repositoryInterface.getGroupList()) {
			if (group.getId().equalsIgnoreCase(id)) {
				tempGroup = group;
			}
		}
		return tempGroup;
	}

	@Override
	public Group getGroupByInviteCode(String inviteCode) {
		Group tempGroup = null;
		for (Group group : repositoryInterface.getGroupList()) {
			if (group.getInviteCode().equals(inviteCode)) {
				tempGroup = group;
			}
		}
		return tempGroup;
	}

}
