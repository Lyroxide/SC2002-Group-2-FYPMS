package fypms;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Supervisor extends User {
	private int projMax = 2;
	private String supervisorID;
	private ArrayList<Project> ownProjects = new ArrayList<>();
	private ArrayList<Project> projectsSupervising = new ArrayList<>();
	private ArrayList<Request> requests = new ArrayList<>();
	
	public Supervisor() {};
	
	public Supervisor(String userID, UserLogin userLogin) {
		super(userLogin);
		supervisorID = userID;
		try {
            ArrayList<Project> allProjects = ProjectIO.readProjects();
            for (Project project : allProjects) {
                if (project.getSupervisorID().equals(supervisorID)) {
                    ownProjects.add(project);
					if (!project.getStudentID().equals("") {
						projectsSupervising.add(project);
					}
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading projects: " + e.getMessage());
        }
		try {
            ArrayList<Request> allRequests = RequestIO.readRequests();
            for (Request request : allRequests) {
                if (request.getReceiver().equals(supervisorID)) {
                    requests.add(request);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading requests: " + e.getMessage());
        }
	}

	public String getSupervisorID() {
		return supervisorID;
	}

	public Project createProject(String projectTitle) {
		Project project = new Project(projectTitle, supervisorID);
		ownProjects.add(project);
		ProjectIO.writeProject(project);
		return project;
	}

	public ArrayList<Project> viewOwnProjects() {
		return ownProjects;
	}

	public void modifyProjectTitle(Request request) throws IOException {
		if (request instanceof RequestForTitle) {
			((RequestForTitle) request).approve(request.getProjectTitle());
			RequestIO.modifyRequest(request);
		}
	}

	public boolean superviseMax() {
		return (projectsSupervising.size() > projMax); //this function needs to be called in FYPCoordinator's allocateProject()
		//in order to change other projects to be UNAVAILABLE if True
	}

	public Request transferProject(int projectID, String newSupervisorID) {
		
        Request request = new RequestForTitle(RequestType.TRANSFER, supervisorID, "FYPCoordinator", projectID, RequestStatus.PENDING, newSupervisorID);
        requests.add(request);
        System.out.println("Transfer request submitted successfully");
        return request;
	}

	public ArrayList<Request> viewPendingRequests() { //read function
        ArrayList<Request> pendingRequests = new ArrayList<>();
        for (Request request : requests) {
            if (request.getStatus().equals(ProjectStatus.PENDING)) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

	public ArrayList<Request> viewRequest() {
		return requests;
	}
}


