package fypms;

import java.io.IOException;
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
            System.out.println("1. Create New Project");
            System.out.println("2. View Your Projects");
            System.out.println("3. Update a Project Title");
            System.out.println("4. Request to Transfer Student");
            System.out.println("5. View Pending Requests to Approve / Reject");
            System.out.println("6. View Request History");
            System.out.println("7. Generate Project Report");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            try {
                coordinator_choice = sc.nextInt();
                sc.nextLine();
                ArrayList<Project> ownProj = coordinator.viewOwnProjects();
                switch (coordinator_choice) {
                    case 1: //create proj
                        System.out.print("Enter New Project Title: ");
                        String projTitle = sc.nextLine();
                        if (!projTitle.isEmpty())
                            coordinator.createProject(projTitle);
                        else System.out.println("Invalid Input.");
                        break;
                    case 2: //view own proj
                        if (ownProj.isEmpty()) {
                            System.out.println("You have no projects.");
                            break;
                        }
                        for (Project p : ownProj) {
                            p.printProjectInfo();
                        }
                        break;
                    case 3: //update title
                        if (ownProj.isEmpty()) {
                            System.out.println("You have no projects.");
                            break;
                        }
                        System.out.print("Enter Project ID: ");
                        try {
                            int id = sc.nextInt();
                            int count = 0;
                            sc.nextLine();
                            for (Project p : ownProj) {
                                if (p.getProjectID() == id) {
                                    System.out.print("Enter New Project Title: ");
                                    String title = sc.nextLine();
                                    if (!title.isEmpty()) {
                                        coordinator.updateTitle(id, title);
                                        count++;
                                        break;
                                    }
                                    else System.out.println("Invalid Input.");
                                }
                            }
                            if (count == 0) System.out.println("You are not supervising that project.");
                        } catch (IOException e) {
                            System.err.println("Invalid input");
                            sc.nextLine();
                        }
                        break;
                    case 4: //new sup transfer
                        if (ownProj.isEmpty()) {
                            System.out.println("You have no projects.");
                            break;
                        }
                        ArrayList<Project> allocated_proj = coordinator.viewAllocatedProjects();
                        if (!allocated_proj.isEmpty()) {
                            System.out.print("Enter Project ID: ");
                            try {
                                int id_2 = sc.nextInt();
                                sc.nextLine();
                                for (Project p : ownProj) {
                                    if (p.getProjectID() == id_2) {
                                        System.out.print("Enter New Project Supervisor: ");
                                        String new_supervisor = sc.nextLine();
                                        if (!new_supervisor.isEmpty()) {
                                            coordinator.transferProject(id_2, new_supervisor);
                                            break;
                                        } else System.out.println("Invalid Input.");
                                    }
                                }
                                System.out.println("You are not supervising that project.");
                            } catch (Exception e) {
                                System.err.println("Invalid input!");
                                sc.nextLine();
                            }
                        } else System.out.println("None of your projects are allocated.");
                        break;
                    case 5: //view req then app/rej
                        ArrayList<Request> pendingReqs = coordinator.viewPendingRequests();
                        if (pendingReqs.isEmpty()) {
                            System.out.println("You currently have no pending requests,");
                            break;
                        }
                        for (Request r : pendingReqs) {
                            RequestIO.printRequestInfo(r);
                        }
                        System.out.print("Select Request ID to Process: ");
                        try {
                            int id_3 = sc.nextInt();
                            sc.nextLine();
                            int count = 0;
                            for (Request r : pendingReqs) {
                                if (r.getRequestID() == id_3) {
                                    System.out.println("(1) Approve | (2) Reject");
                                    System.out.print("Enter Choice: ");
                                    try {
                                        int request_choice = sc.nextInt();
                                        if (request_choice == 1) {
                                            if (r.getType().equals(RequestType.ALLOCATION))
                                                coordinator.allocateProject(r);
                                            else if (r.getType().equals(RequestType.DEREGISTRATION))
                                                coordinator.deregisterStudent(r);
                                            else if (r.getType().equals(RequestType.TITLECHANGE))
                                                coordinator.modifyProjectTitle(r);
                                            else if (r.getType().equals(RequestType.TRANSFER))
                                                coordinator.changeSupervisor(r);
                                        } else if (request_choice == 2) {
                                            if (r instanceof RequestForTitle rt) {
                                                rt.reject(rt);
                                            } else if (r instanceof RequestForRegistration rr) {
                                                rr.reject(rr);
                                            } else if (r instanceof RequestForTransfer rx) {
                                                rx.reject(rx);
                                            }

                                        }
                                    } catch (Exception e) {
                                        System.err.println("Invalid input!");
                                        sc.nextLine();
                                    }
                                } else count++;
                            }
                            if (count == pendingReqs.size()) System.out.println("Request does not exist.");
                        } catch (Exception e) {
                            System.err.println("Invalid input!");
                            sc.nextLine();
                        }

                        break;
                    case 6: //view req hist
                        ArrayList<Request> allReq = coordinator.viewRequests(); //make sure coordinator implements this
                        if (allReq.isEmpty()) {
                            System.out.println("Either you have not made any requests, or none of your requests has been processed.");
                            break;
                        }
                        for (Request r : allReq) {
                            RequestIO.printRequestInfo(r);
                        }
                        break;
                    case 7: //generate report
                        System.out.println("| Filter by: (1) STATUS | (2) SUPERVISOR | (3) STUDENT | (4) PROJECT |");
                        System.out.print("Enter Filter Type: ");
                        try {
                            int filterType = sc.nextInt();
                            sc.nextLine();
                            ArrayList<Project> reports = coordinator.generateReport(filterType);
                            if (reports.isEmpty()) {
                                System.out.println("No project is found.");
                                break;
                            }
                            for (Project p : reports) {
                                p.printProjectInfo();
                            }
                        } catch (Exception e) {
                            System.err.println("Invalid input!");
                            sc.nextLine();
                        }
                        break;
                    case 8: //change pw
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
