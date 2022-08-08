package main.services.interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import main.models.users.User;

public interface UserServiceInterface {

	boolean createNewUser(String firstName, String lastName, String username, String password, String gender,
			String dateOfBirth) throws NoSuchAlgorithmException, NoSuchProviderException;

	boolean login(String username, String password);

	boolean checkHashPassword(String username, String password);

	User getUserByUsername(String username);

	User getUserByUserId(String id);

	boolean setAlias(User user1, User user2, String alias);

}
