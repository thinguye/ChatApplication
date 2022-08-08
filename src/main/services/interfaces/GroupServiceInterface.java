package main.services.interfaces;

import main.models.enums.GroupType;
import main.models.groups.Group;

public interface GroupServiceInterface {
	
	boolean createGroup(GroupType groupType, String userId);

	boolean addMember(String inviterId, String inviteeId, String groupId);

	boolean addMemberByInviteCode(String userId, String inviteCode);

	boolean checkMemberGroup(String userId, String groupId);

	boolean leaveGroup(String userId, String groupId);

	Group getGroupByGroupId(String id);

	Group getGroupByInviteCode(String inviteCode);
}
