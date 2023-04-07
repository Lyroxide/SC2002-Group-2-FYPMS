package fypms;

import java.io.*;
import java.util.ArrayList;

public class Student extends User {

    private String studentID;
    private StudentStatus status;

    private int curProject = -1;

    public Student() {};


    public String getStudentID() {
        return studentID;
    }

    public void setStatus(StudentStatus studentStatus) {
        this.status = studentStatus;
    }

    public StudentStatus getStatus() {
        return this.status;
    }

    public int getCurProject() {
        return curProject;
    }

    public void setCurProject(int id) {
        curProject = id;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public ArrayList<Project> viewAvailableProjects() throws IOException {
        ArrayList<Project> availableProjects = new ArrayList<>();
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project project : allProjects) {
            if (project.getStatus().equals(ProjectStatus.AVAILABLE)) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }

    public void selectProject(int projectID) throws IOException {
        Request request = new RequestForRegistration(RequestType.ALLOCATION, this.studentID, "FYPCoordinator", projectID, RequestStatus.PENDING);
        RequestIO.writeRequest(request);
        setStatus(StudentStatus.PENDING);
        System.out.println("Project Selection Requested. Please wait for approval.");
    }

    public void deregisterProject() throws IOException {
        Request request = new RequestForRegistration(RequestType.DEREGISTRATION, this.studentID, "FYPCoordinator", this.curProject, RequestStatus.PENDING);
        RequestIO.writeRequest(request);
        System.out.println("Project De-registration Requested. Please wait for approval.");
    }

    public void changeProjectTitle(String newTitle) throws IOException {
        if (!this.getStatus().equals(StudentStatus.REGISTERED)) {
            System.out.println("You are not allocated a project.");
        }
        else {
            ArrayList<Project> allProjects = ProjectIO.readProjects();
            for (Project p : allProjects) {
                if (p.getProjectID() == this.curProject) {
                    Request request = new RequestForTitle(RequestType.TITLECHANGE, this.studentID, p.getSupervisorID(), this.curProject, RequestStatus.PENDING, newTitle);
                    RequestIO.writeRequest(request);
                    System.out.println("Project De-registration Requested. Please wait for approval.");
                }
            }

        }
    }

    public ArrayList<Request> viewRequests() {
        ArrayList<Request> ownRequests = new ArrayList<>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request r : allRequests) {
            if (r.getSender().equals(this.studentID)) {
                ownRequests.add(r);
            }
        }
        return ownRequests;
    }
}


