package fypms;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Supervisor extends User {
	private String supervisorID;
	
	public Supervisor() {};
	
	public Supervisor(String userID, UserLogin userLogin) {
		super(userLogin);
		supervisorID = userID;
	}

	public String getSupervisorID() {
		return supervisorID;
	}
	
	public Project createProject(String projectTitle) {
		Project project = new Project(projectTitle, supervisorID);
		ProjectIO.writeProject(project);
		return project;
	}

	public ArrayList<Project> viewOwnProjects() {
		ArrayList<Project> ownProjects = new ArrayList<Project>();
		ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project project : allProjects) {
            if (project.getSupervisorID().equals(supervisorID)) {
                ownProjects.add(project);
            }
        }
		return ownProjects;
	}

	public void modifyProjectTitle(Request request) throws IOException {
		if (request instanceof RequestForTitle) {
			((RequestForTitle) request).approve(request.getProjectTitle());
			RequestIO.modifyRequest(request);
		}
	}

	public Request transferProject(int projectID, String newSupervisorID) {
        Request request = new RequestForTransfer(RequestType.TRANSFER, supervisorID, "FYPCoordinator", projectID, RequestStatus.PENDING, newSupervisorID);
		RequestIO.writeRequest(request);
        System.out.println("Transfer request submitted successfully");
        return request;
	}

	public ArrayList<Request> viewPendingRequests() { //read function
		ArrayList<Request> pendingRequests = new ArrayList<Request>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request request : allRequests) {
            if (request.getStatus().equals(ProjectStatus.PENDING) && request.getReceiver().equals(supervisorID)) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

	public ArrayList<Request> viewRequest() {
		ArrayList<Request> requests = new ArrayList<Request>();
		ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request request : allRequests) {
            if (request.getReceiver().equals(supervisorID)) {
                pendingRequests.add(request);
            }
        }
        return requests;
	}
}


