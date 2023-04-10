package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * FYPCoordinatorMenu provides the menu functions that takes in user input and calls {@link FYPCoordinatorView} prompt functions
 * @version 1.0
 */
public class FYPCoordinatorMenu {

    /**
     * Menu function to allow {@link FYPCoordinator} to call functions through input
     * See {@link FYPCoordinatorView} for prompt functions
     * @param userID correct userID after login
     * @param password correct password after login
     */
    public static void fypCoordinatorMenu(String userID,String password) {

        FYPCoordinator coordinator = new FYPCoordinator();
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\\n");
        boolean pwChanged = false;
        ArrayList<FYPCoordinator> coordinators = UserIO.readFYPCoordinator();

        for (FYPCoordinator f : coordinators) {
            if (f.getCoordinatorID().equals(userID))
                coordinator = f;
        }
        int coordinator_choice = 0;
        do {
            System.out.println("----------------------------");
            System.out.println("Welcome to FYP Coordinator Menu!");
            System.out.println("(1) Create New Project");
            System.out.println("(2) View Your Projects");
            System.out.println("(3) Update a Project Title");
            System.out.println("(4) Request to Transfer Student");
            System.out.println("(5) View Pending Requests to Approve / Reject");
            System.out.println("(6) View Request History");
            System.out.println("(7) Generate Project Report");
            System.out.println("(8) Change Password");
            System.out.println("(9) Logout");
            System.out.print("Enter your choice: ");
            try {
                if (sc.hasNextInt()) {
                    coordinator_choice = sc.nextInt();
                    sc.nextLine();
                    FYPCoordinatorView coordinatorView = new FYPCoordinatorView();
                    switch (coordinator_choice) {
                        case 1: //create proj
                            coordinatorView.promptCreateProject(coordinator);
                            break;
                        case 2: //view own proj
                            coordinatorView.promptViewOwnProjects(coordinator);
                            break;
                        case 3: //update title
                            coordinatorView.promptUpdateTitle(coordinator);
                            break;
                        case 4: //new sup transfer
                            coordinatorView.promptTransfer(coordinator);
                            break;
                        case 5: //view req then app/rej
                            coordinatorView.promptViewRequests(coordinator);
                            break;
                        case 6: //view req hist
                            coordinatorView.promptViewReqHistory(coordinator);
                            break;
                        case 7: //generate report
                            coordinatorView.promptGenerateReport(coordinator);
                            break;
                        case 8:
                            System.out.print("Enter old password: ");
                            String oldPW = sc.nextLine();
                            System.out.print("Enter new password: ");
                            String newPW = sc.nextLine();
                            System.out.print("Re-enter new password: ");
                            String newPW_2 = sc.nextLine();

                            if (!oldPW.equals(password))
                                System.out.println("You have entered wrong password.");
                            else if (!newPW.equals(newPW_2))
                                System.out.println("New password fields do not match.");
                            else if (newPW.equals(password))
                                System.out.println("You cannot re-use the same password!");
                            else if (oldPW.isEmpty() || newPW.isEmpty())
                                System.out.println("You cannot input empty fields.");
                            else {
                                pwChanged = coordinator.changePW(newPW);
                                System.out.println("You have successfully changed password.");
                            }
                            break;
                        case 9: //logout
                            break;
                        default:
                            System.out.println("Invalid input!");
                            break;
                    }
                } else {
                    sc.nextLine();
                    System.out.println("Invalid input!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (coordinator_choice != 9 && !pwChanged);
        System.out.println("Returning to main screen...");
    }
}