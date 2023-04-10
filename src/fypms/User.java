package fypms;

import java.io.IOException;


/**
 * Class for User common attributes and functions
 * @version 1.0
 */
public class User {
	
	/**
	 * user's name
	 */
    private String name;
	
	/**
	 * user's email
	 */
    private String email;
	
	/**
	 * user's password
	 */
    private String password;
	
	/**
	 * user's type
	 */
    private UserType userType;

	/**
	 * Default constructor
	 */
    public User() {}

	/**
	 * Gets name
	 * @return name
	 */
    public String getName() {
        return name;
    }

	/**
	 * Sets name
	 * @param name user's name
	 */
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * Gets email
	 * @return email
	 */
    public String getEmail() {
        return email;
    }

	/**
	 * Sets email
	 * @param email user's email
	 */
    public void setEmail(String email) {
        this.email = email;
    }

	/**
	 * Gets password
	 * @return password
	 */
    public String getPassword() {
        return password;
    }

	/**
	 * Sets password
	 * @param password user's password
	 */
    public void setPassword(String password) {
        this.password = password;
    }

	/**
	 * Sets user type
	 * @param type user's type
	 */
    public void setUserType(UserType type) {
        userType = type;
    }

	/**
	 * Function to change user password and write to file
	 * See {@link UserIO} for write password function
	 * @param newPassword new password from input
	 * @return when true, returns to main menu
	 * @throws IOException IOException
	 */
    public boolean changePW(String newPassword) throws IOException {
        setPassword(newPassword);

        switch (userType) {
            case STUDENT -> {
                UserIO.writeStudentPassword(this.getName(), newPassword);
                return true;
            }
            case SUPERVISOR -> {
                UserIO.writeSupervisorPassword(this.getName(), newPassword);
                return true;
            }
            case FYPCOORDINATOR -> {
                UserIO.writeCoordinatorPassword(this.getName(), newPassword);
                return true;
            }
        }

        return false;
    }
}