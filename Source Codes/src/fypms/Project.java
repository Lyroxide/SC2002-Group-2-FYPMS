package projectandreq;
public class Project {
  
    public enum Status {
        available, reserved, unavailable, allocated
    }
    public Status projectStatus;
    private String supervisorID;
    public String studentID;
    private int projectID;
    private String projectTitle;
 
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
     
    
            System.out.printf("Project ID: %d\n", projectID);
            //supervisor name
            //supervisor email address
            System.out.printf("Supervisor ID: %s\n", supervisorID);
            System.out.printf("Project Title: %s\n", projectTitle);
            System.out.printf("Project Status: %s\n", projectStatus);
            //check about supervisor name and email address
    
        
    }
    
    public void printAllocated(String studentID) {
      if (projectStatus == Status.allocated) {
        
          System.out.println("Allocated Project Information: ");
            System.out.printf("Project ID: %d\n", projectID);
            //supervisor name
            //supervisor email
            //student name
            //studnet email address 
            System.out.printf("Project Title: %s\n", projectTitle);
            System.out.printf("Project Status: %s\n", projectStatus);
    
        }
        
    }
}
