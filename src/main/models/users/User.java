package main.models.users;

import java.util.HashMap;
import java.util.Map;

import main.models.enums.GenderType;

public class User {
	private static int count = 0;
	private String id;
	private String lastName;
	private String firstName;
	private String username;
	private String password;
	private String salt;
	private GenderType gender;
	private String dateOfBirth;
	private Map<User, String> alias;

	public User(PreCreateUser tempUser, String password) {
		id = "user" + count++;
		this.lastName = tempUser.getLastName();
		this.firstName = tempUser.getFirstName();
		this.username = tempUser.getUsername();
		this.password = password;
		this.gender = tempUser.getGender();
		this.dateOfBirth = tempUser.getDateOfBirth();
		alias = new HashMap<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getSalt() {
		return salt;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Map<User, String> getAlias() {
		return alias;
	}

	public void setAlias(Map<User, String> alias) {
		this.alias = alias;
	}
}
