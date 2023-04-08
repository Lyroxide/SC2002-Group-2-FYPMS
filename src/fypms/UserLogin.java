package fypms;

import java.util.ArrayList;

/**
 * Class for Users to login and verify login details
 * @version 1.0
 */
public class UserLogin {

    /**
     * This is User's Type as a temporary variable used for login
     */
    private String userType;

    /**
     * Default UserLogin constructor
     */
    public UserLogin() {}

	/**
	 * Verify the userID only
	 * @param userID userID input
	 * @return true for correct ID, false for wrong ID
	 */
    public boolean verifyUser(String userID) {
        ArrayList<Student> students = UserIO.readStudents();
        ArrayList<Supervisor> supervisors = UserIO.readSupervisors();
        ArrayList<FYPCoordinator> coordinators = UserIO.readFYPCoordinator();

        for (Student student : students) {
            if (student.getStudentID().equals(userID)) {
                return true;
            }
        }

        for (Supervisor supervisor : supervisors) {
            if (supervisor.getSupervisorID().equals(userID)) {
                return true;
            }
        }

        for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.getCoordinatorID().equals(userID)) {
                return true;
            }
        }

        return false;
    }

	/**
	 * Verify the userID and password
	 * @param userID userID input
	 * @param password password input
	 * @return true for correct ID AND password
	 */
    public boolean verifyUserAndPass(String userID, String password) {
        ArrayList<Student> students = UserIO.readStudents();
        ArrayList<Supervisor> supervisors = UserIO.readSupervisors();
        ArrayList<FYPCoordinator> coordinators = UserIO.readFYPCoordinator();
        for (Student student : students) {
            if (student.getStudentID().equals(userID) && student.getPassword().equals(password)) {
                this.userType = "student";
                return true;
            }
        }

        for (Supervisor supervisor : supervisors) {
            if (supervisor.getSupervisorID().equals(userID) && supervisor.getPassword().equals(password)) {
                this.userType = "supervisor";
                return true;
            }
        }

        for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.getCoordinatorID().equals(userID) && coordinator.getPassword().equals(password)) {
                this.userType = "coordinator";
                return true;
            }
        }

        return false;
    }
	/**
	 * Gets the user's type for login
	 * @return this user's type as a String
	 */
    public String getUserType() {
        return userType;
    }
}