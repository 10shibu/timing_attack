package edu.brandeis.cs12b.pa9;

/**
 * YOU MAY NOT MODIFY THIS CLASS.
 * 
 *
 */
public class UserAuthenticator {
	private static final String LINE_DELIMITER = "&&";
	private static final String FIELD_DELIMITER = "//";
	private String users = "Bob//test//regular";
	
	/**
	 * Adds a regular user with the given username and password to the database.
	 * @param username the username to add
	 * @param password the password of that user
	 */
	public void addRegularUser(String username, String password) {
		users = username + FIELD_DELIMITER + 
				password + FIELD_DELIMITER + "regular" + 
				LINE_DELIMITER + users;
	}
	
	/**
	 * Authenticates a user based on a username or password. Will return
	 * either the user's access type ("regular" or "admin"), or the string
	 * "Authentication failure!" if the username or password is incorrect
	 * 
	 * @param username the username to log in with
	 * @param password that user's password
	 * @return the user status or "Authentication failure!"
	 */
	public String authenticateUser(String username, String password) {
		for (String line : users.split(LINE_DELIMITER)) {
			String[] fields = line.split(FIELD_DELIMITER);
			if (username.equals(fields[0]) && password.equals(fields[1])) {
				return fields[2];
			}
		}
		
		return "Authentication failure!";
	}
}
