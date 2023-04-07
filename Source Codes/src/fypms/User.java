package fypms;

import java.io.IOException;

public class User {
    private String name;
    private String email;
    private String userID;
    private String password;
    private UserType userType;

    public User() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType type) {
        userType = type;
    }

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

        return false; //when true, go back to Menu
    }
}