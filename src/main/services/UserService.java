package main.services;

import main.models.users.PreCreateUser;
import main.models.users.User;
import main.models.utilities.HashPassword;
import main.repositories.Repository;
import main.repositories.interfaces.RepositoryInterface;
import main.services.interfaces.UserServiceInterface;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class UserService implements UserServiceInterface {
	RepositoryInterface repositoryInterface;

	public UserService() {
		this.repositoryInterface = new Repository();
	}

	@Override
	public boolean createNewUser(String firstName, String lastName, String username, String password, String gender,
			String dateOfBirth) throws NoSuchAlgorithmException, NoSuchProviderException {
		PreCreateUser tempUser = new PreCreateUser(firstName, lastName, username, gender, dateOfBirth);
		String salt = HashPassword.getSalt();
		String hashedPassword = HashPassword.getHash(password, salt);
		if (getUserByUsername(tempUser.getUsername()) == null) {
			User user = new User(tempUser, hashedPassword);
			user.setSalt(salt);
			repositoryInterface.addUser(user);
			return true;
		} else
			return false;
	}

	@Override
	public boolean login(String username, String password) {
		boolean isSuccessful = false;
		if (getUserByUsername(username) != null && checkHashPassword(username, password)) {
			isSuccessful = true;
		}
		return isSuccessful;
	}

	@Override
	public boolean checkHashPassword(String username, String password) {
		User tempUser = getUserByUsername(username);
		if (tempUser == null)
			return false;
		String salt = tempUser.getSalt();
		String hashedPassword = HashPassword.getHash(password, salt);
		return getUserByUsername(username).getPassword().equals(hashedPassword);
	}

	@Override
	public User getUserByUsername(String username) {
		User tempUser = null;
		for (User user : repositoryInterface.getUserList()) {
			if (user.getUsername().equals(username)) {
				tempUser = user;
			}
		}
		return tempUser;
	}

	@Override
	public User getUserByUserId(String id) {
		User tempUser = null;
		for (User user : repositoryInterface.getUserList()) {
			if (user.getId().equalsIgnoreCase(id)) {
				tempUser = user;
			}
		}
		return tempUser;
	}

	@Override
	public boolean setAlias(User user1, User user2, String alias) {
		user1.getAlias().put(user2, alias);
		return true;
	}
}
