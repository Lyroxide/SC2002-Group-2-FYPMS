package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SupervisorMenu {
    public static void supervisorMenu(String userID,String password){
        Supervisor supervisor = new Supervisor();
        Scanner sc = new Scanner(System.in);
        boolean pwChanged = false;
        ArrayList<Supervisor> supervisors = UserIO.readSupervisors();

        for (Supervisor s : supervisors) {
            if (s.getSupervisorID().equals(userID))
                supervisor = s;
        }

        int supervisor_choice = 0;
        do {
            System.out.println("----------------------------");
            System.out.println("Welcome to Supervisor Menu!");
            System.out.println("(1) Create New Project");
            System.out.println("(2) View Your Projects");
            System.out.println("(3) Update a Project Title");
            System.out.println("(4) Request to Transfer Student");
            System.out.println("(5) View Pending Requests to Approve / Reject");
            System.out.println("(6) View Request History");
            System.out.println("(7) Change Password");
            System.out.println("(8) Logout");
            System.out.print("Enter your choice: ");
            try {
                supervisor_choice = sc.nextInt();
                sc.nextLine();
                SupervisorView supervisorView = new SupervisorView();
                switch (supervisor_choice) {
                    case 1: //create proj
                        supervisorView.promptCreateProject(supervisor);
                        break;
                    case 2: //view own proj
                        supervisorView.promptViewOwnProjects(supervisor);
                        break;
                    case 3: //update title
                        supervisorView.promptUpdateTitle(supervisor);
                        break;
                    case 4: //new sup transfer
                        supervisorView.promptTransfer(supervisor);
                        break;
                    case 5: //view req then app/rej
                        supervisorView.promptViewRequests(supervisor);
                        break;
                    case 6: //view req hist
                        supervisorView.promptViewReqHistory(supervisor);
                        break;
                    case 7: //change pw
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
                            pwChanged = supervisor.changePW(newPW);
                            System.out.println("You have successfully changed password.");
                        }
                        break;
                    case 8: //logout
                        break;
                    default:
                        System.err.println("Invalid input!");
                }
            } catch (Exception e) {
                System.err.println("Invalid input!");
                sc.nextLine();
            }
        } while (supervisor_choice != 8 && !pwChanged);
        System.out.println("Returning to main screen...");
    }
}

