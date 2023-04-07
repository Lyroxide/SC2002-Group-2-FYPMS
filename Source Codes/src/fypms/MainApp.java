package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * UI for the Main Application
 *
 * @version 1.0
 */
public class MainApp {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("-------------------");
            System.out.println("Welcome to FYPMS!");
            System.out.println("1: Log in");
            System.out.println("2: Quit");
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

                boolean validUser;
                boolean validUserAndPass = false;
                boolean pwChanged = false;


                UserLogin ul = new UserLogin();
                validUser = ul.verifyUser(userID);
                if (validUser) {
                    validUserAndPass = ul.verifyUserAndPass(userID, password);
                }


                // Valid login
                if (validUserAndPass) {
                    String userType = ul.getUserType();
                    ArrayList<Student> students = UserIO.readStudents();
                    ArrayList<Supervisor> supervisors = UserIO.readSupervisors();
                    ArrayList<FYPCoordinator> coordinators = UserIO.readFYPCoordinator();

                    switch (userType) {
                        // Student Interface
                        case "student" -> {
                            Student student = new Student();
                            for (Student s : students) {
                                if (s.getStudentID().equals(userID))
                                    student = s;
                            }
                            int student_choice = 0;
                            do {
                                System.out.println("----------------------------");
                                System.out.println("Welcome to the Student Menu!");
                                System.out.println("1: View Available Projects");
                                System.out.println("2: Select Project");
                                System.out.println("3: View Current Allocated Project");
                                System.out.println("4: Request To Change Project Title");
                                System.out.println("5: Request To Deregister FYP");
                                System.out.println("6: View Outgoing Request History");
                                System.out.println("7. Change Password");
                                System.out.println("8. Logout");
                                System.out.print("Enter your choice: ");


                                    student_choice = sc.nextInt();
                                    sc.nextLine();
                                    switch (student_choice) {
                                        case 1: //view available projects
                                            if (student.getStatus().equals(StudentStatus.NEW)) {
                                                ArrayList<Project> proj;
                                                proj = student.viewAvailableProjects();
                                                for (Project p : proj) {
                                                    p.printProjectInfo();
                                                }
                                            }
                                            else if (student.getStatus().equals(StudentStatus.PENDING)) {
                                                System.out.println("Please wait for your selection request to be processed first.");
                                            }
                                            else if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                                                System.out.println("You are already allocated a project.");
                                            }
                                            else if (student.getStatus().equals(StudentStatus.DEREGISTERED)) {
                                                System.out.println("You cannot view projects after de-registration.");
                                            }
                                            break;
                                        case 2: //select project
                                            if (student.getStatus().equals(StudentStatus.NEW)) {
                                                System.out.print("Enter Project ID: ");
                                                try {
                                                    int project_choice = sc.nextInt();
                                                    sc.nextLine();
                                                    student.selectProject(project_choice);
                                                    UserIO.writeStudentStatus(student);
                                                } catch (IOException e) {
                                                    System.err.println("Invalid input.");
                                                }
                                            }
                                            else if (student.getStatus().equals(StudentStatus.PENDING)) {
                                                System.out.println("Please wait for your selection request to be processed first.");
                                            }
                                            else if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                                                System.out.println("You are already allocated a project.");
                                            }
                                            else if (student.getStatus().equals(StudentStatus.DEREGISTERED)) {
                                                System.out.println("You cannot select a project after de-registration.");
                                            }
                                            break;
                                        case 3: //view curr project
                                            if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                                                ArrayList<Project> allProj = ProjectIO.readProjects();
                                                for (Project p : allProj) {
                                                    if (p.getStudentID().equals(student.getStudentID())) {
                                                        p.printAllocated();
                                                        break;
                                                    }
                                                }
                                            } else System.out.println("You have not registered a project.");
                                            break;
                                        case 4: //r: change title
                                            if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                                                System.out.print("Enter New Title: ");
                                                String newTitle = sc.nextLine();
                                                sc.nextLine();
                                                student.changeProjectTitle(newTitle);
                                            } else System.out.println("You have not registered a project.");
                                            break;
                                        case 5: //r: deregister
                                            if (student.getStatus().equals(StudentStatus.DEREGISTERED)) {
                                                System.out.println("Warning: After de-registration, you would not be able to register for a project anymore. Continue?");
                                                System.out.println("(Y) Continue | (Any Key) Exit");
                                                String confirm = sc.nextLine();
                                                if (confirm.equals("Y")) {
                                                    student.deregisterProject();
                                                }
                                            }
                                            else { System.out.println("You are not allocated a project."); }
                                            break;
                                        case 6: //view request history
                                            ArrayList<Request> allReq = student.viewRequests();
                                            for (Request r : allReq) {
                                                RequestIO.printRequestInfo(r);
                                            }
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
                                                pwChanged = student.changePW(newPW);
                                                System.out.println("You have successfully changed password.");
                                            }
                                            break;
                                        case 8: //logout
                                            break;
                                        default:
                                            System.err.println("Invalid input!");
                                    }
                            } while (student_choice != 8 && !pwChanged);
                            System.out.println("Returning to main screen...");
                        }
                        // Supervisor interface
                        case "supervisor" -> {
                            Supervisor supervisor = new Supervisor();
                            for (Supervisor s : supervisors) {
                                if (s.getSupervisorID().equals(userID))
                                    supervisor = s;
                            }
                            int supervisor_choice = 0;
                            do {
                                System.out.println("----------------------------");
                                System.out.println("Welcome to Supervisor Menu!");
                                System.out.println("1. Create New Project");
                                System.out.println("2. View Your Projects");
                                System.out.println("3. Update a Project Title");
                                System.out.println("4. Request to Transfer Student");
                                System.out.println("5. View Pending Requests to Approve / Reject");
                                System.out.println("6. View Request History");
                                System.out.println("7. Change Password");
                                System.out.println("8. Logout");
                                System.out.print("Enter your choice: ");
                                try {
                                    supervisor_choice = sc.nextInt();

                                    switch (supervisor_choice) {
                                        case 1: //create proj
                                            System.out.print("Enter New Project Title: ");
                                            String projTitle = sc.nextLine();
                                            supervisor.createProject(projTitle);
                                            break;
                                        case 2: //view own proj
                                            ArrayList<Project> ownProjs = supervisor.viewOwnProjects();
                                            for (Project p : ownProjs) {
                                                p.printProjectInfo();
                                            }
                                            break;
                                        case 3: //update title
                                            System.out.print("Enter Project ID: ");
                                            try {
                                                int id = sc.nextInt();
                                                System.out.print("Enter New Project Title: ");
                                                String title = sc.nextLine();
                                                supervisor.updateTitle(id, title);
                                            } catch (IOException e) {
                                                System.err.println("Invalid input");
                                                sc.nextLine();
                                            }

                                            break;
                                        case 4: //new sup transfer
                                            System.out.print("Enter Project ID: ");
                                            try {
                                                int id_2 = sc.nextInt();
                                                System.out.print("Enter New Project Supervisor: ");
                                                String new_supervisor = sc.nextLine();
                                                supervisor.transferProject(id_2, new_supervisor);
                                            } catch (IOException e) {
                                                System.err.println("Invalid input");
                                                sc.nextLine();
                                            }
                                            break;
                                        case 5: //view req then app/rej
                                            ArrayList<Request> pendingReqs = supervisor.viewPendingRequests();
                                            for (Request r : pendingReqs) {
                                                RequestIO.printRequestInfo(r);
                                            }
                                            System.out.print("Select Request ID to Process: ");
                                            try {
                                                int id_3 = sc.nextInt();
                                                ArrayList<Request> incReq = supervisor.viewRequests(1);
                                                for (Request r : incReq) {
                                                    if (r.getRequestID() == id_3) {
                                                        System.out.println("Approve (1) / Reject (2)");
                                                        System.out.print("Enter Choice: ");
                                                        try {
                                                            int request_choice = sc.nextInt();
                                                            if (request_choice == 1) {
                                                                supervisor.modifyProjectTitle(r);
                                                            } else if (request_choice == 2) {
                                                                if (r instanceof RequestForTitle rt) {
                                                                    rt.reject(rt);
                                                                }
                                                            }
                                                        } catch (IOException e) {
                                                            System.err.println("Invalid input");
                                                            sc.nextLine();
                                                        }
                                                    }
                                                }
                                            } catch (IOException e) {
                                                System.err.println("Invalid input");
                                                sc.nextLine();
                                            }
                                            System.out.println("Request Does Not Exist.");
                                            break;
                                        case 6: //view req hist
                                            System.out.println("(1) Incoming Requests / (2) Outgoing Requests");
                                            System.out.print("Enter your choice: ");
                                            try {
                                                int req_choice = sc.nextInt();
                                                sc.nextLine();

                                                if (req_choice == 1 || req_choice == 2) {
                                                    ArrayList<Request> allReq = supervisor.viewRequests(req_choice);
                                                    for (Request r : allReq) {
                                                        RequestIO.printRequestInfo(r);
                                                    }
                                                } else System.out.println("Invalid choice.");
                                            } catch (IOException e) {
                                                System.err.println("Invalid input");
                                                sc.nextLine();
                                            }
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
                        //FYPCoordinator interface
                        case "coordinator" -> {
                            FYPCoordinator coordinator = new FYPCoordinator();
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
                                    ArrayList<Request> allReqs = RequestIO.readRequests();
                                    switch (coordinator_choice) {
                                        case 1: //create proj
                                            System.out.print("Enter New Project Title: ");
                                            String projTitle = sc.nextLine();
                                            coordinator.createProject(projTitle);
                                            break;
                                        case 2: //view own proj
                                            ArrayList<Project> ownProjs = coordinator.viewOwnProjects();
                                            for (Project p : ownProjs) {
                                                p.printProjectInfo();
                                            }
                                            break;
                                        case 3: //update title
                                            System.out.print("Enter Project ID: ");
                                            try {
                                                int id = sc.nextInt();
                                                System.out.print("Enter New Project Title: ");
                                                String title = sc.nextLine();
                                                coordinator.updateTitle(id, title);
                                            } catch (Exception e) {
                                                System.err.println("Invalid input!");
                                                sc.nextLine();
                                            }
                                            break;
                                        case 4: //new sup transfer
                                            System.out.print("Enter Project ID: ");
                                            try {
                                                int id_2 = sc.nextInt();
                                                System.out.print("Enter New Project Supervisor: ");
                                                String new_supervisor = sc.nextLine();
                                                coordinator.transferProject(id_2, new_supervisor);
                                            } catch (Exception e) {
                                                System.err.println("Invalid input!");
                                                sc.nextLine();
                                            }
                                            break;
                                        case 5: //view req then app/rej
                                            ArrayList<Request> pendingReqs = coordinator.viewPendingRequests();
                                            for (Request r : pendingReqs) {
                                                RequestIO.printRequestInfo(r);
                                            }
                                            System.out.print("Select Request ID to Process: ");
                                            try {
                                                int id_3 = sc.nextInt();
                                                for (Request r : allReqs) {
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
                                                    }
                                                    System.out.println("Request Does Not Exist.");
                                                }
                                            } catch (Exception e) {
                                                System.err.println("Invalid input!");
                                                sc.nextLine();
                                            }

                                            break;
                                        case 6: //view req hist
                                            System.out.println("(1) Incoming Requests  | (2) Outgoing Requests ");
                                            System.out.print("Enter your choice: ");
                                            try {
                                                int req_choice = sc.nextInt();
                                                sc.nextLine();

                                                if (req_choice == 1 || req_choice == 2) {
                                                    ArrayList<Request> allReq = coordinator.viewRequests(req_choice); //make sure coordinator implements this
                                                    for (Request r : allReq) {
                                                        RequestIO.printRequestInfo(r);
                                                    }
                                                } else System.out.println("Invalid choice.");
                                            } catch (IOException e) {
                                                System.err.println("Invalid input");
                                                sc.nextLine();
                                            }
                                            break;
                                        case 7: //generate report
                                            System.out.println("| Filter by: (1) STATUS | (2) SUPERVISOR | (3) STUDENT | (4) PROJECT |");
                                            System.out.print("Enter Filter Type: ");
                                            int filterType = sc.nextInt();
                                            try {
                                                sc.nextLine();
                                                ArrayList<Project> reports = coordinator.generateReport(filterType);
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