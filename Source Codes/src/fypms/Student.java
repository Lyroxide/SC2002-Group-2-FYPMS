package fypms;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Student extends User{
    
//     private enum Status {
//         new, registered, deregistered;
//     }

// 	we will be creating our own enum class
    
   
    private String studentID;
    private String curProject;
    private Request request;
	private String status;
    private ArrayList<Request> requestsHistory;


    //change constructor when extending from User class
    public Student() {};
	
	public Student(String userID, UserLogin userLogin) {
		super(userLogin);
		studentID = userID;
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
			if (project.getStatus() == "available" && !Supervisor.superviseMax(project.getSupervisorID())) {
				availableProjects.add(project);
			}
		}
		
		return availableProjects;
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
        Request requests = new Request();
        request.add(requests);
        System.out.println("Project Selection Requested. Please wait for approval");
    }
    
    public Request deregisterProject(){
        //change request to 0 for deregister request
        //change curproject to nothing
        if(projRegistered)
        {
            System.out.println("No Existing project selected.");
            
        }
        //change request to 0 for deregister request
        //change curproject to nothing
        // this.curProject = null;
        Request requests = new Request();
        request.add(requests);
        System.out.println("Project deregistration Requested. Please wait for approval");
    }

    public Request changeTitle(String ProjectTitle){
        //change title change request for that project
        //title request 
        titleChange = true;
        String newtitle = ProjectTitle;
        Request requests = new Request();
        request.add(requests);
        addRequestHistory("Requested to change project Title");
        System.out.println("Title Change requested");
        return newTitle;
    }
    


    public ArrayList<Request> viewRequestInfo(){
        return this.requestsHistory;
        
    }
    public void addRequestHistory(String request)
    {
        this.requestsHistory.add(request);
    }
}
