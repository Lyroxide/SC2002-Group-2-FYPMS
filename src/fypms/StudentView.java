package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * StudentView provides the prompt functions that {@link Student} can call
 * @version 1.0
 */
public class StudentView {

    /**
     * Default Constructor for StudentView
     */
    public StudentView() {}


    /**
     * Prompt function to allow {@link Student} to see available {@link Project}
     * See {@link Project} for printing function
     * @param student {@link Student} instance after login
     * @throws IOException IOException
     */
    public void promptViewAvailableProjects(Student student) throws IOException {
        if (student.getStatus().equals(StudentStatus.NEW)) {
            ArrayList<Project> proj = student.viewAvailableProjects();
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
    }

    /**
     * Prompt function to allow {@link Student} to select {@link Project} and send a {@link Request} to {@link FYPCoordinator}
     * See {@link Student} for select function and {@link UserIO} for modification of {@link Student} status
     * @param student {@link Student} instance after login
     */
    public void promptSelectProject(Student student) {
        Scanner sc = new Scanner(System.in);
        if (student.getStatus().equals(StudentStatus.NEW)) {
            System.out.print("Enter Project ID: ");
            try {
                int project_choice = sc.nextInt();
                sc.nextLine();
                student.selectProject(project_choice);
                UserIO.writeStudentStatus(student);
            } catch (Exception e) {
                System.out.println("Invalid input.");
                sc.nextLine();
            }
        } else if (student.getStatus().equals(StudentStatus.PENDING)) {
            System.out.println("Please wait for your selection request to be processed first.");
        } else if (student.getStatus().equals(StudentStatus.REGISTERED)) {
            System.out.println("You are already allocated a project.");
        } else if (student.getStatus().equals(StudentStatus.DEREGISTERED)) {
            System.out.println("You cannot select a project after de-registration.");
        }
    }

    /**
     * Prompt function to allow {@link Student} to view allocated {@link Project}
     * See {@link Project} for printing function
     * @param student {@link Student} instance after login
     */
    public void promptViewCurrProject(Student student) {
        if (student.getStatus().equals(StudentStatus.REGISTERED)) {
            ArrayList<Project> allProj = ProjectIO.readProjects();
            for (Project p : allProj) {
                if (p.getStudentID().equals(student.getStudentID())) {
                    p.printAllocated();
                    break;
                }
            }
        } else System.out.println("You have not registered a project.");
    }

    /**
     * Prompt function to allow {@link Student} to change allocated {@link Project} title and send a {@link Request} to {@link Supervisor}
     * See {@link Student} for change project title function
     * @param student {@link Student} instance after login
     * @throws IOException IOException
     */
    public void promptChangeTitle(Student student) throws IOException {
        Scanner sc = new Scanner(System.in);
        if (student.getStatus().equals(StudentStatus.REGISTERED)) {
            ArrayList<Request> requests = RequestIO.readRequests();
            int count = 0;
            for (Request r : requests) {
                if (r.getSender().equals(student.getStudentID())) {
                    if (r.getType() == RequestType.TITLECHANGE && r.getStatus() == RequestStatus.PENDING) count++;
                }
            }
            if (count == 0) {
                System.out.print("Enter New Title: ");
                String newTitle = sc.nextLine();
                if (!newTitle.isEmpty())
                    student.changeProjectTitle(newTitle);
                else System.out.println("Invalid input!");
            } else {
                System.out.println("Please wait for your request for title change to be processed first.");
            }
        }
        else {
            System.out.println("You have not registered a project.");
        }
    }

    /**
     * Prompt function to allow {@link Student} to deregister allocated {@link Project} and send a {@link Request} to {@link FYPCoordinator}
     * See {@link Student} for deregister function
     * @param student {@link Student} instance after login
     * @throws IOException IOException
     */
    public void promptDeregister(Student student) throws IOException {
        Scanner sc = new Scanner(System.in);
        if (student.getStatus().equals(StudentStatus.REGISTERED)) {
            ArrayList<Request> requests = RequestIO.readRequests();
            int count = 0;
            for (Request r : requests) {
                if (r.getSender().equals(student.getStudentID())) {
                    if (r.getType() == RequestType.DEREGISTRATION && r.getStatus() == RequestStatus.PENDING) count++;
                }
            }
            if (count == 0) {
                System.out.println("Warning: After de-registration, you would not be able to register for a project anymore. Continue?");
                System.out.println("(Y) Continue | (Any Key) Exit");
                System.out.print("Enter your choice: ");
                String confirm = sc.nextLine();
                if (confirm.equals("Y")) {
                    student.deregisterProject();
                }
            } else {
                System.out.println("Please wait for your request for de-registration to be processed first.");
            }
        }
        else {
            System.out.println("You are not allocated a project.");
        }
    }

    /**
     * Prompt function to allow {@link Student} to view processed {@link Request}
     * See {@link RequestIO} for printing function
     * @param student {@link Student} instance after login
     * @throws IOException IOException
     */
    public void promptViewRequestHistory(Student student) throws IOException {
        ArrayList<Request> allReq = student.viewRequests(student.getStudentID());
        if (allReq.isEmpty()) {
            System.out.println("Either you have not made any requests, or none of your requests has been processed.");
        }
        else {
            for (Request r : allReq) {
                RequestIO.printRequestInfo(r);
            }
        }
    }


}
