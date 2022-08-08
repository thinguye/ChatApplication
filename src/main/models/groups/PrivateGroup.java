package main.models.groups;

import main.models.enums.GroupType;

public class PrivateGroup extends Group {
	
	public PrivateGroup() {
		groupType = GroupType.PRIVATE;
	}
}
