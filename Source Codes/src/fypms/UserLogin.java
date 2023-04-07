package fypms;

import java.util.ArrayList;

public class UserLogin {

    private String userType;

    public UserLogin() {}

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

    public String getUserType() {
        return userType;
    }
}