package main.models.users;

import main.models.enums.GenderType;

public class PreCreateUser {
	private String lastName;
	private String firstName;
	private String username;
	private GenderType gender;
	private String dateOfBirth;

	public PreCreateUser(String lastName, String firstName, String username, String gender, String dateOfBirth) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.username = username;
		if (gender.equalsIgnoreCase("Male")) {
			this.gender = GenderType.MALE;
		} else if (gender.equalsIgnoreCase("Female")) {
			this.gender = GenderType.FEMALE;
		} else {
			this.gender = GenderType.OTHER;
		}
		this.dateOfBirth = dateOfBirth;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getUsername() {
		return username;
	}

	public GenderType getGender() {
		return gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}
}
