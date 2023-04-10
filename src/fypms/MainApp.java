package fypms;

import java.util.Scanner;


/**
 * UI for the Main Application
 *
 * @version 1.0
 */
public class MainApp {


    /**
     * Main function that is the starting point of the UI
     * @param args Arguments passed to the app
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        boolean validUser;
        boolean validUserAndPass;
        boolean pwChanged;
        do {
            validUser = false;
            validUserAndPass = false;
            pwChanged = false;
            System.out.println("-------------------");
            System.out.println("Welcome to FYPMS!");
            System.out.println("(1) Log in");
            System.out.println("(2) Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.err.println("Invalid input!");
                sc.nextLine();
            }

            if (choice == 1) {
                sc.nextLine();
                System.out.println("Please enter your User ID and password.");
                System.out.print("User ID: ");
                String userID = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();

                UserLogin ul = new UserLogin();
                validUser = ul.verifyUser(userID);
                if (validUser) {
                    validUserAndPass = ul.verifyUserAndPass(userID, password);
                }


                // Valid login
                if (validUserAndPass) {
                    String userType = ul.getUserType();

                    switch (userType) {
                        case ("student") -> StudentMenu.studentMenu(userID, password);
                        case ("supervisor") -> SupervisorMenu.supervisorMenu(userID, password);
                        case ("coordinator") -> FYPCoordinatorMenu.fypCoordinatorMenu(userID, password);
                    }
                }
                // Invalid login
                else if (!validUser) {
                    System.out.println("Wrong username entered! Returning to main screen...");
                }
                else if (!validUserAndPass) {
                    System.out.println("Wrong password entered! Returning to main screen...");
                }
                else if (pwChanged) {
                    System.out.println("Password changed. Returning to main screen...");
                }
            }
        } while (choice != 2);
        System.out.println("Thank you for using FYPMS!");
        System.exit(0);
    }

}
