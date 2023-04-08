package fypms;

import java.io.*;
import java.util.ArrayList;

public class Supervisor extends User {
    private String supervisorID;

    public Supervisor() {}
    public Supervisor(String name, String email, String userID, String password, UserType userType) {
        setName(name);
        setEmail(email);
        this.supervisorID = userID;
        setPassword(password);
        setUserType(userType);
    }

    public String getSupervisorID() {
        return supervisorID;
    }


    public void createProject(String projectTitle) throws IOException {
        Project project = new Project(projectTitle, supervisorID);
        if (project.superviseNum(supervisorID) >= 2) {
            project.setStatus(ProjectStatus.UNAVAILABLE);
        }
        ProjectIO.writeProject(project);
        System.out.println("Project created successfully");
    }

    public void updateTitle(int projectID, String projectTitle) throws IOException {
        Project project = null;
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID() == projectID) {
                p.setProjectTitle(projectTitle);
                ProjectIO.modifyProject(p);
                project = p;
                System.out.println("Project Title Updated Successfully.");
                break;
            }
        }
        if (project == null) System.out.println("Project Not Found.");
    }

    public ArrayList<Project> viewOwnProjects() throws IOException {
        ArrayList<Project> ownProjects = new ArrayList<>();
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project project : allProjects) {
            if (project.getSupervisorID().equals(this.supervisorID)) {
                ownProjects.add(project);
            }
        }
        return ownProjects;
    }

    public ArrayList<Project> viewAllocatedProjects() throws IOException {
        ArrayList<Project> ownProjects = new ArrayList<>();
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project project : allProjects) {
            if (project.getSupervisorID().equals(this.supervisorID) && project.getStatus().equals(ProjectStatus.ALLOCATED)) {
                ownProjects.add(project);
            }
        }
        return ownProjects;
    }

    public void modifyProjectTitle(Request request) throws IOException {
        if (request instanceof RequestForTitle rt) {
            String newTitle =  rt.getProjectTitle();
            rt.approve(newTitle);
            RequestIO.modifyRequest(rt);
        }
    }

    public void transferProject(int projectID, String newSupervisorID) throws IOException {
        Request request = new RequestForTransfer(RequestType.TRANSFER, supervisorID, "FYPCoordinator", projectID, RequestStatus.PENDING, newSupervisorID);
        RequestIO.writeRequest(request);
        System.out.println("Transfer request submitted successfully");
    }

    public ArrayList<Request> viewPendingRequests() { //read function
        ArrayList<Request> pendingRequests = new ArrayList<>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request request : allRequests) {
            if (request.getStatus().equals(RequestStatus.PENDING) && request.getReceiver().equals(supervisorID)) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    public ArrayList<Request> viewRequests() throws IOException {
        ArrayList<Request> requests = new ArrayList<>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request request : allRequests) {
            if (request.getReceiver().equals(supervisorID) && !request.getStatus().equals(RequestStatus.PENDING)) {
                requests.add(request);
            }
        }
        return requests;
    }
}



