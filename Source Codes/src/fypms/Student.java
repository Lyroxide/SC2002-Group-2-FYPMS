package fypms;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Student extends User{
    
    enum Status {
        NEW, REGISTERED, DEREGISTERED,PENDING,APPROVED
    }

    private String studentID;
    private String curProject;
    private Request request;
    private Status status;
//  private ArrayList<Request> requestsHistory;
    private ArrayList<Project> AvailableProjects;


    //change constructor when extending from User class
    	public Student() {};
	
	public Student(String userID, UserLogin userLogin) {
		super(userLogin);
		studentID = userID;
		
		try {
		    ArrayList<Request> allRequests = RequestIO.readRequests();
		} catch (IOException e) {
		    System.out.println("Error reading requests: " + e.getMessage());
		}
	}

	public String getStudentID() {
	// Returns the student's studentID
		return studentID;
	}
    
	//call method from project class
	public ArrayList<Project> viewAvailableProjects(){
		if (this.getStatus() != "new") {
			System.out.println("Sorry, you cannot view available projects at this time.");
			return null;
		}
	ArrayList<Project> availableProjects = new ArrayList<Project>();
	ArrayList<Project> allProjects; //code next time
	for (Project project : allProjects) {
		// Check if project is available and supervisor is not supervising maximum projects
		if (project.getStatus() == "AVAILABLE" && !Supervisor.superviseMax(project.getSupervisorID())) {
			availableProjects.add(project);
		}
	}

	return availableProjects;
	}
	
	public void printAvailableProjects(){
		for(Project project : AvailableProjects){
			System.out.println("Supervisor: " + project.getSupervisorID);
			System.out.println("ProjectID: " + project.getProjectID + " )Project title: " + project.getProjectTitle);
		}
	}
    
    public ArrayList<Project> selectProject(int projectID){
        //read from project list
        //print projects that have are not taken
        //return project list
        //should i Assign a project ID to each when reading the projects list at initialization
        //students choose projects based off title or project ID
        if(projRegistered)
        {
            System.out.println("Existing project selected, Please deregister before selecting new project");
            break;
        }
        //set request for project selection
        requestProj = true;
        //add request method 
        Request requests = new RequestForRegister(RequestType.REGISTER, this.studentID, "FYPCoordinator", projectID, RequestStatus.PENDING, newSupervisorID);
        allRequests.add(requests);
        System.out.println("Project Selection Requested. Please wait for approval");
	return allRequests;
    }
    
    public ArrayList<Request> deregisterProject(){
        //change request to 0 for deregister request
        //change curproject to nothing
        if(projRegistered)
        {
            System.out.println("No Existing project selected.");
            
        }
        //change request to 0 for deregister request
        //change curproject to nothing
        // this.curProject = null;
        Request requests = new RequestForRegister(RequestType.DEREGISTER, this.studentID, "FYPCoordinator", projectID, RequestStatus.PENDING, newSupervisorID);
        allRequests.add(requests);
        System.out.println("Project deregistration Requested. Please wait for approval");
	return allRequests;
    }

    public Array<Request> changeProjectTitle(int projectID, String newTitle){
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
		Request requests = new RequestForTitleChange(RequestType.TITLECHANGE, this.studentID, "Supervisor", projectID, RequestStatus.PENDING, newSupervisorID);
		allRequests.add(requests);
		System.out.println("Project Title Change Requested. Please wait for approval");
		//student cannot edit need to wait for approval
		/*project.setProjectTitle(newTitle);
		modifyProject(project);
		System.out.println("Project title has been updated.");*/
	} else {
		System.out.println("You are not allowed to modify this project's title");
	}
	 return allRequests;
    }
    

    //change to void function
    public ArrayList<Request> viewRequestInfo(){
	    int i=1;
	for(Request r : allRequests){
		if(project.getStudetID.equals(studentID){
			System.out.println(i+")" + " ReqeustType: " +request.getType() + " ,ProjectTitle: "+ project.getProjectTitle+ " ,Status: " + request.getStatus());
		}
		i++;   
	}
    }

