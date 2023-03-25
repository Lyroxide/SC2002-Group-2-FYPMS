package fypms;

public class Project {
    private enum Status {
        available, reserved, unavailable, allocated
    }
    private Status projectStatus;
    private String supervisorID;
    private String studentID;
    private int projectID;
    private String projectTitle;
    
    public Project(int projectID, String supervisorID) {
        // Constructor that sets the project's projectID and supervisor ID
        this.projectID = projectID
        this.supervisorID = supervisorID
    }
    
    public void printProjectInfo() {
        if (projectStatus == Status.available) {                        //doesnt it need to print no matter the status? - hamka
          System.out.println("Available Project Information: ");
            System.out.printf("Project ID: %d\n", projectID);
            //supervisor name
            //supervisor email address
            System.out.printf("Supervisor ID: %s\n", supervisorID);
            System.out.printf("Project Title: %s\n", projectTitle);
            System.out.printf("Project Status: %s\n", projectStatus);
            //check about supervisor name and email address
    
        }
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSupervisorID() {
        return supervisorID;
    }
    
    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }
    
    public String getStudentID() {
        return studentID;
    }
    
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
    public int getProjectID() {
        return projectID;
    }
    
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
    
    public String getProjectTitle() {
        return projectTitle;
    }
    
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
