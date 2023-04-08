package fypms;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for Supervisor attributes and functions
 * Inherits User Class
 * @version 1.0
 */
public class Supervisor extends User {
	
	/**
	 * supervisor's userID
	 */
    private String supervisorID;
	
	/**
	 * Default constructor
	 */
    public Supervisor() {}
	
	/**
	 * Supervisor constructor when reading from file
     * @param name name
     * @param email email
     * @param userID supervisor ID
     * @param password password
     * @param userType user type which is supervisor
	 */
    public Supervisor(String name, String email, String userID, String password, UserType userType) {
        setName(name);
        setEmail(email);
        this.supervisorID = userID;
        setPassword(password);
        setUserType(userType);
    }

	/**
	 * Gets supervisor id
	 * @return supervisor id
	 */
    public String getSupervisorID() {
        return supervisorID;
    }

	/**
	 * Function that creates and writes a project
	 * @param projectTitle new project
	 * @throws IOException IOException
	 */
    public void createProject(String projectTitle) throws IOException {
        Project project = new Project(projectTitle, supervisorID);
        if (project.superviseNum(supervisorID) >= 2) {
            project.setStatus(ProjectStatus.UNAVAILABLE);
        }
        ProjectIO.writeProject(project);
        System.out.println("Project created successfully");
    }

	/**
	 * Function that updates project title and writes project
	 * @param projectID project ID input
     * @param projectTitle new project title
	 * @throws IOException IOException
	 */
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

	/**
	 * Function that returns own projects
	 * @return Array List of Project
	 * @throws IOException IOException
	 */
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

	/**
	 * Function that returns allocated projects
	 * @return Array List of Project
	 * @throws IOException IOException
	 */
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

	/**
	 * Function that checks for correct request instance before approval of title change
	 * @param request Request instance
	 * @throws IOException IOException
	 */
    public void modifyProjectTitle(Request request) throws IOException {
        if (request instanceof RequestForTitle rt) {
            String newTitle =  rt.getProjectTitle();
            rt.approve(newTitle);
            RequestIO.modifyRequest(rt);
        }
    }

	/**
	 * Function that makes a transfer request to coordinator
	 * @param projectID project ID input
     * @param newSupervisorID new supervisor ID to be transferred to
	 * @throws IOException IOException
	 */
    public void transferProject(int projectID, String newSupervisorID) throws IOException {
        Request request = new RequestForTransfer(RequestType.TRANSFER, supervisorID, "FYPCoordinator", projectID, RequestStatus.PENDING, newSupervisorID);
        RequestIO.writeRequest(request);
        System.out.println("Transfer request submitted successfully");
    }

	/**
	 * Function that returns pending requests
	 * @return Array List of Request
	 */
    public ArrayList<Request> viewPendingRequests() {
        ArrayList<Request> pendingRequests = new ArrayList<>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request request : allRequests) {
            if (request.getStatus().equals(RequestStatus.PENDING) && request.getReceiver().equals(supervisorID)) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

	/**
	 * Function that returns processed requests
	 * @return Array List of Request
	 */
    public ArrayList<Request> viewRequests() {
        ArrayList<Request> requests = new ArrayList<>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request request : allRequests) {
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                if (request.getReceiver().equals(supervisorID) || request.getSender().equals(supervisorID))
                    requests.add(request);
            }
        }
        return requests;
    }
}



