package fypms;

import java.util.ArrayList;
import java.util.Scanner;

public class FYPCoordinatorMenu {
    public static void fypCoordinatorMenu(String userID,String password){
        FYPCoordinator coordinator = new FYPCoordinator();
        Scanner sc = new Scanner(System.in);
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
                    case 9: //logout
                        break;
                    default:
                        System.err.println("Invalid input!");
                }
            } catch (Exception e) {
                System.err.println("Invalid input!");
                sc.nextLine();
            }
        } while (coordinator_choice != 9 && !pwChanged);
        System.out.println("Returning to main screen...");
    }
}