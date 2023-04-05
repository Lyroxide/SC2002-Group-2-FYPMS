package fypms;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class UserLogin {
    private String userID;
    private String password;
	private String userType;

    public UserLogin(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public boolean verifyUser(ArrayList<Student> students, ArrayList<Supervisor> supervisors, ArrayList<FYPCoordinator> coordinators) {
        for (Student student : students) {
            if (student.getUserID().equals(this.userID)) {
                return true;
            }
        }

        for (Supervisor supervisor : supervisors) {
            if (supervisor.getUserID().equals(this.userID)) {
                return true;
            }
        }
		
		for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.getUserID().equals(this.userID)) {
                return true;
            }
        }

        return false;
    }
    
	
	public boolean verifyUserAndPass(ArrayList<Student> students, ArrayList<Supervisor> supervisors, ArrayList<FYPCoordinator> coordinators) {
        for (Student student : students) {
            if (student.getUserID().equals(this.userID) && student.getPassword().equals(this.password)) {
                this.userType = "student";
                return true;
            }
        }

        for (Supervisor supervisor : supervisors) {
            if (supervisor.getUserID().equals(this.userID) && supervisor.getPassword().equals(this.password)) {
                this.userType = "supervisor";
                return true;
            }
        }
		
		for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.getUserID().equals(this.userID) && coordinator.getPassword().equals(this.password)) {
                this.userType = "coordinator";
                return true;
            }
        }

        return false;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }
	
	public String setUserID(String userID) {
		this.userID = userID;
	}

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getUserType() {
		return userType;
	}
}