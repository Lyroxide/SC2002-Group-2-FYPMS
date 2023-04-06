package fypms;

import java.util.ArrayList;

public class Project {
  
    protected enum Status {
        AVAILABLE, RESERVED, UNAVAILABLE, ALLOCATED
    }
    private Status projectStatus;
    private String supervisorID;
    private String studentID;
    private int projectID;
    private String projectTitle;
    private ArrayList<Supervisor> SupervisorArr = new ArrayList<>();
    private ArrayList<Student> StudentArr = new ArrayList<>(); 
 
    public Project(String projectTitle, String supervisorID) {
        // Constructor that sets the project's projectID and supervisor ID
        this.projectTitle= projectTitle;
        this.supervisorID = supervisorID;
    }
  
    
    public Status getStatus() {
      return this.projectStatus;
    }
    
    public void setStatus(Status status) {
      this.projectStatus = status;
    }
    
    public String getSupervisorID() {
      return this.supervisorID;
    }
    
    public String getStudentID() {
      return this.studentID;
    }
    
    public void setStudentID(String studentID) {
      this.studentID = studentID;
    }
    
    public void setProjectID(int projectID) {
      this.projectID = projectID;
    }
    
    public int getProjectID() {
      return this.projectID;
    }
    
    public String getProjectTitle() {
      return this.projectTitle;
    }
    
  
    public void printProjectInfo() {
            SupervisorArr = UserIO.getSupervisors();
            System.out.printf("Project ID: %d ", projectID);
            System.out.printf("| Project Title: %s", projectTitle);
            System.out.printf("| Project Status: %s", projectStatus);
            System.out.printf("| Supervisor ID: %s", supervisorID);
            for(int i=0;i<SupervisorArr.size();i++) {
            	if(SupervisorArr.get(i).getSupervisorID() == this.supervisorID)
            	{
            		System.out.printf("| Supervisor Name: %s ", SupervisorArr.get(i).getName());
            		System.out.printf("| Supervisor Email: %s", SupervisorArr.get(i).getEmail());
            		
            	}
      		
            }
            
            
    
        
    }
    
    public void printAllocated() {
      
      SupervisorArr = UserIO.getSupervisors();
    	StudentArr = UserIO.getStudents();
      if (projectStatus == Status.ALLOCATED) {
        
          System.out.println("Allocated Project Information: ");
          System.out.printf("Project ID: %d", projectID);
          System.out.printf("| Project Title: %s", projectTitle);
          System.out.printf("| Project Status: %s", projectStatus);
        
           //iterate through Supervisor array to find name and email
            for(int i=0;i<SupervisorArr.size();i++) {
            	if(SupervisorArr.get(i).getSupervisorID() == this.supervisorID)
            	{
            		System.out.printf("| Supervisor Name: %s", SupervisorArr.get(i).getName());
            		System.out.printf("| Supervisor Email: %s", SupervisorArr.get(i).getEmail());
            		
            	}
      		
            }
        
            //iterates through student Array to get name and email
            for(int i=0;i<StudentArr.size();i++) {
            	if(StudentArr.get(i).getStudentID() == this.studentID)
            	{
            		System.out.printf("| Student Name: %s", StudentArr.get(i).getName());
            		System.out.printf("| Student Email: %s", StudentArr.get(i).getEmail());
            		
            	}
      		
            }
            
    
        }
        
    }
    public int superviseNum(String supervisorID) {
      int count = 0;
      ArrayList<Project> allProjects = ProjectIO.readProjects();
      for (Project p : allProjects) {
        if (p.getSupervisorID().equals(supervisorID) && p.getStatus().equals(Status.ALLOCATED)) {
        count++;
        }
     }
     return count;
   }
}
