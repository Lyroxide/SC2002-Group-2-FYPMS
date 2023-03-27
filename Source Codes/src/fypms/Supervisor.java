package fypms;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Supervisor extends User {
    private int numOfProjects;
	private int projMax = 2;
	private String supervisorID;
	private ArrayList<Project> ownProjects;
	private ArrayList<Project> projectsSupervising;
	private ArrayList<Request> requests;
	
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
		ownProjects.add(project);
		return project;
	}

	public ArrayList<Project> viewOwnProjects() {
		return ownProjects;
	}

	public void modifyProjectTitle(int projectID, String newTitle) throws IOException {
		Project project = null;
        for (Project p : projectsSupervising) {
            if (p.getProjectID().equals(projectID)) {
                project = p;
                break;
            }
        }
        if (project == null) {
            System.out.println("Project not found");
        }
		
		if (project.getSupervisorID().equals(supervisorID)) {
			project.setProjectTitle(newTitle);
			modifyProject(project);
			System.out.println("Project title has been updated.");
		} else {
			System.out.println("You are not allowed to modify this project's title");
		}
	}

	public boolean superviseMax() {
		return (projectsSupervising.size() < projMax);
	}

	public Request transferProject(int projectID, String newSupervisorID) {
		Project project = null;
        for (Project p : projectsSupervising) {
            if (p.getProjectID().equals(projectID)) {
                project = p;
                break;
            }
        }
        if (project == null) {
            System.out.println("Project not found");
            return null;
        }
        Request request = new Request();
        requests.add(request);
        System.out.println("Transfer request submitted successfully");
        return request;
	}

	public ArrayList<Request> viewPendingRequests() {
        ArrayList<Request> pendingRequests = new ArrayList<>();
        for (Request request : requests) {
            if (request.getStatus().equals("Pending")) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

	public ArrayList<Request> viewRequest() {
		return requests;
	}
}


