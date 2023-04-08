package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMenu {
        public static void studentMenu(String userID,String password){
        Scanner sc = new Scanner(System.in);
        Student student = new Student();
        boolean pwChanged = false;
        ArrayList<Student> students = UserIO.readStudents();

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

            try {
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
                        } else if (student.getStatus().equals(StudentStatus.PENDING)) {
                            System.out.println("Please wait for your selection request to be processed first.");
                        } else if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                            System.out.println("You are already allocated a project.");
                        } else if (student.getStatus().equals(StudentStatus.DEREGISTERED)) {
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
                        } else if (student.getStatus().equals(StudentStatus.PENDING)) {
                            System.out.println("Please wait for your selection request to be processed first.");
                        } else if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                            System.out.println("You are already allocated a project.");
                        } else if (student.getStatus().equals(StudentStatus.DEREGISTERED)) {
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
                        }
                        else if (student.getStatus().equals(StudentStatus.PENDINGTITLE)){
                            System.out.println("Please wait for your title change request to be processed first.");
                        }
                        else {
                            System.out.println("You have not registered a project.");
                        }
                        break;
                    case 4: //r: change title
                        if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                            System.out.print("Enter New Title: ");
                            String newTitle = sc.nextLine();
                            if (!newTitle.isEmpty())
                                student.changeProjectTitle(newTitle);
                            else System.out.println("Invalid Input.");
                        } 
                        else if (student.getStatus().equals(StudentStatus.PENDINGTITLE)){
                            System.out.println("Please wait for your title change request to be processed first.");
                        }
                        else if(student.getStatus().equals(StudentStatus.PENDINGDEALLOCATION)){
                            System.out.println("Please wait for your deregistration request to be prossessed.");
                        } 
                        else {
                            System.out.println("You have not registered a project.");
                        }
                        break;
                    case 5: //r: deregister
                        if (student.getStatus().equals(StudentStatus.REGISTERED)) {
                            System.out.println("Warning: After de-registration, you would not be able to register for a project anymore. Continue?");
                            System.out.println("(Y) Continue | (Any Key) Exit");
                            String confirm = sc.nextLine();
                            if (confirm.equals("Y")) {
                                student.deregisterProject();
                            }
                        }
                        else if (student.getStatus().equals(StudentStatus.PENDINGTITLE)){
                            System.out.println("Please wait for your title change request to be processed first.");
                        }
                        else if(student.getStatus().equals(StudentStatus.PENDINGDEALLOCATION)){
                            System.out.println("Please wait for your deregistration request to be prossessed.");
                        } 
                        else {
                            System.out.println("You are not allocated a project.");
                        }
                        break;
                    case 6: //view request history
                        ArrayList<Request> allReq = student.viewRequests(student.getStudentID());
                        if (allReq.isEmpty()) {
                            System.out.println("Either you have not made any requests, or none of your requests has been processed.");
                            break;
                        }
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
            } catch (IOException e) {
                System.err.println("Invalid input!");
            }
        } while (student_choice != 8 && !pwChanged);
    }
}
