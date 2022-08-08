package main.models.groups;

import main.models.enums.GroupType;

public class PublicGroup extends Group {

	public PublicGroup() {
		groupType = GroupType.PUBLIC;
	}
}
