package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class StudentView {

    public StudentView() {}


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

    public void promptSelectProject(Student student) {
        Scanner sc = new Scanner(System.in);
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
    }

    public void promptViewCurrProject(Student student) throws IOException {
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

    public void promptChangeTitle(Student student) throws IOException {
        Scanner sc = new Scanner(System.in);
        if (student.getStatus().equals(StudentStatus.REGISTERED)) {
            ArrayList<Request> requests = RequestIO.readRequests();
            int count = 0;
            for (Request r : requests) {
                if (r.getSender().equals(student.getStudentID())) {
                    if (r.getType() == RequestType.TITLECHANGE) count++;
                }
            }
            if (count == 0) {
                System.out.print("Enter New Title: ");
                String newTitle = sc.nextLine();
                if (!newTitle.isEmpty())
                    student.changeProjectTitle(newTitle);
                else System.out.println("Invalid input.");
            } else {
                System.out.println("Please wait for your request for title change to be processed first.");
            }
        }
        else {
            System.out.println("You have not registered a project.");
        }
    }

    public void promptDeregister(Student student) throws IOException {
        Scanner sc = new Scanner(System.in);
        if (student.getStatus().equals(StudentStatus.REGISTERED)) {
            ArrayList<Request> requests = RequestIO.readRequests();
            int count = 0;
            for (Request r : requests) {
                if (r.getSender().equals(student.getStudentID())) {
                    if (r.getType() == RequestType.DEREGISTRATION) count++;
                }
            }
            if (count == 0) {
                System.out.println("Warning: After de-registration, you would not be able to register for a project anymore. Continue?");
                System.out.println("(Y) Continue | (Any Key) Exit");
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
