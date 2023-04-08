package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class FYPCoordinator extends Supervisor {

    private String coordinatorID;

    public FYPCoordinator() {}

    public FYPCoordinator(String userID) {
        super(userID);
        coordinatorID = userID;
    }

    public String getCoordinatorID() {
        return this.coordinatorID;
    }

    public void setCoordinatorID(String coordinatorID) {
        this.coordinatorID = coordinatorID;
    }

    public void changeSupervisor(Request request) throws IOException {
        String newSupervisorID;
        int supNum;
        if (request instanceof RequestForTransfer rx) {
            ArrayList<Project> proj = ProjectIO.readProjects();

            for (Project project : proj) {
                if (project.getProjectID() == rx.getProjectID()) {
                    newSupervisorID = rx.getSupervisorID();
                    supNum = project.superviseNum(newSupervisorID);
                    if (supNum < 2) {
                        rx.approve(newSupervisorID);
                        RequestIO.modifyRequest(rx);
                    } else {
                        System.out.println("Project cannot be transferred to Supervisor " + newSupervisorID
                                + " because he/she have 2 projects already");
                    }
                    break;
                }
            }
        }
        else {
            System.out.println("Request is of the wrong type");
        }
    }

    public void allocateProject (Request request) throws IOException {
        int supNum;
        if (request instanceof RequestForRegistration rr) {
            ArrayList<Project> proj = ProjectIO.readProjects();
            for (int i = 0; i < proj.size(); i++) {
                if (proj.get(i).getProjectID() == rr.getProjectID()) {
                    String supervisorID = proj.get(i).getSupervisorID();
                    supNum = proj.get(i).superviseNum(supervisorID);
                    if (supNum < 2) {
                        rr.approve(request.getSender());
                        RequestIO.modifyRequest(rr);

                        ArrayList<Student> stu = UserIO.readStudents();
                        for (i = 0; i < stu.size(); i++) {
                            if (stu.get(i).getStudentID().equals(rr.getSender())) {
                                stu.get(i).setStatus(StudentStatus.REGISTERED);
                                stu.get(i).setCurProject(rr.getProjectID());
                                UserIO.writeStudentStatus(stu.get(i));
                                break;
                            }
                        }
                    }
                    else {
                        System.out.println("Supervisor " + supervisorID + " is currently supervising 2 projects. ");
                        System.out.println("Current request status will remain as PENDING");
                    }
                    break;
                }
            }
        }
        else {
            System.out.println("Request is of the wrong type");
        }
    }

    public void deregisterStudent(Request request) throws IOException {
        if (request instanceof RequestForRegistration rr) {
            rr.approve(request.getSender());
            RequestIO.modifyRequest(rr);

            ArrayList<Student> stu = UserIO.readStudents();
            for (Student student : stu) {
                if (student.getStudentID().equals(rr.getSender())) {
                    student.setStatus(StudentStatus.DEREGISTERED);
                    student.setStudentID("");
                    student.setCurProject(-1);
                    UserIO.writeStudentStatus(student);
                    break;
                }
            }
        }
        else {
            System.out.println("Request is of the wrong type");
        }
    }

    public ArrayList<Project> generateReport(int filterType) throws IOException {

        int choice, i = 0;
        Scanner read = new Scanner(System.in);

        if (filterType == 1) {
            ArrayList<Project> proj = ProjectIO.readProjects();

            System.out.println("Generate report by: STATUS");
            System.out.println("Select Project Status to view:");
            System.out.println("(1) Available");
            System.out.println("(2) Unavailable");
            System.out.println("(3) Reserved");
            System.out.println("(4) Allocated");
            System.out.println("(5) All");

            do {
                System.out.println("Please select a choice:");
                choice = read.nextInt();
                if (choice < 0 || choice > 5) System.out.println("Invalid choice!");
            } while (choice < 0 || choice > 5);

            if (choice == 1) {
                while (i < proj.size()) {
                    if (proj.get(i).getStatus() != ProjectStatus.AVAILABLE)  proj.remove(i);
                    else i++;
                }
            }
            else if (choice == 2) {
                while (i < proj.size()) {
                    if (proj.get(i).getStatus() != ProjectStatus.UNAVAILABLE)  proj.remove(i);
                    else i++;
                }
            }
            else if (choice == 3) {
                while (i < proj.size()) {
                    if (proj.get(i).getStatus() != ProjectStatus.RESERVED)  proj.remove(i);
                    else i++;
                }
            }
            else if (choice == 4) {
                while (i < proj.size()) {
                    if (proj.get(i).getStatus() != ProjectStatus.ALLOCATED)  proj.remove(i);
                    else i++;
                }
            }

            return proj;
        }
        else if (filterType == 2) {
            ArrayList<Project> proj = ProjectIO.readProjects();
            ArrayList<Supervisor> sup = UserIO.readSupervisors();
            System.out.println("Generate report by: SUPERVISOR");
            System.out.println("Select Supervisor to view:");
            for (i = 0; i < sup.size(); i++) {
                int j = i + 1;
                System.out.println("(" + j + ") " + sup.get(i).getName());
            }
            int j = sup.size() + 1;
            System.out.println("(" + j + ") All");

            do {
                System.out.println("Please select a choice:");
                choice = read.nextInt();
                if (choice < 0 || choice > sup.size() + 1) System.out.println("Invalid choice!");
            } while (choice < 0 || choice > sup.size() + 1);
            if (choice != sup.size() + 1) {
                String supervisorID = sup.get(choice - 1).getSupervisorID();
                proj.removeIf(p -> !p.getSupervisorID().equals(supervisorID));
            }
            return proj;
        }
        else if (filterType == 3) {
            ArrayList<Project> proj = ProjectIO.readProjects();
            ArrayList<Student> stu = UserIO.readStudents();

            System.out.println("Generate report by: STUDENT");
            System.out.println("Select Student to view:");
            for (i = 0; i < stu.size(); i++) {
                int j = i + 1;
                System.out.println("(" + j + ") " + stu.get(i).getName());
            }
            int j = stu.size() + 1;
            System.out.println("(" + j + ") All");

            do {
                System.out.println("Please select a choice:");
                choice = read.nextInt();
                if (choice < 0 || choice > stu.size() + 1) System.out.println("Invalid choice!");
            } while (choice < 0 || choice > stu.size() + 1);

            if (choice != stu.size() + 1) {
                String studentID = stu.get(choice - 1).getStudentID();
                proj.removeIf(p -> !p.getStudentID().equals(studentID));
            }
            return proj;
        }
        else if (filterType == 4) {
            ArrayList<Project> proj = ProjectIO.readProjects();

            System.out.println("Generate report by: PROJECTS");
            System.out.println("Select Projects to view:");
            for (i = 0; i < proj.size(); i++) {
                int j = i + 1;
                System.out.println("(" + j + ") " + proj.get(i).getProjectTitle());
            }
            int j = proj.size() + 1;
            System.out.println("(" + j + ") All");

            do {
                System.out.println("Please select a choice:");
                choice = read.nextInt();
                if (choice < 0 || choice > proj.size() + 1) System.out.println("Invalid choice!");
            } while (choice < 0 || choice > proj.size() + 1);
            if (choice != proj.size() + 1) {
                String title = proj.get(choice - 1).getProjectTitle();
                proj.removeIf(p -> !p.getProjectTitle().equals(title));
            }
            return proj;
        }
        else return null;
    }

    public ArrayList<Request> viewPendingRequests() { //smth about the userid
        ArrayList<Request> req;
        int i = 0;
        req = RequestIO.readRequests();
        while (i < req.size()) {
            if (req.get(i).getStatus() != RequestStatus.PENDING) req.remove(i);
            else i++;
        }
        return req;
    }

    public ArrayList<Request> viewRequests() {
        ArrayList<Request> requests = new ArrayList<>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request request : allRequests) {
            if (request.getReceiver().equals("FYPCoordinator") && !request.getStatus().equals(RequestStatus.PENDING)) {
                requests.add(request);
            }
        }
        return requests;
    }
}