package fypms;
public class Project {
  
    public enum Status {
        AVAILABLE, RESERVED, UNAVAILABLE, ALLOCATED
    }
    public Status projectStatus;
    private String supervisorID;
    public String studentID;
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
            System.out.printf("Project ID: %d\n", projectID);
            for(int i=0;i<SupervisorArr.size();i++) {
            	if(SupervisorArr.get(i).getSupervisorID() == this.supervisorID)
            	{
            		System.out.printf("%s", SupervisorArr.get(i).getName());
            		System.out.printf("%s", SupervisorArr.get(i).getEmail());
            		
            	}
      		
            }
            System.out.printf("Supervisor ID: %s\n", supervisorID);
            System.out.printf("Project Title: %s\n", projectTitle);
            System.out.printf("Project Status: %s\n", projectStatus);
    
        
    }
    
    public void printAllocated(String studentID) {
      
      SupervisorArr = UserIO.getSupervisors();
    	StudentArr = UserIO.getStudents();
      if (projectStatus == Status.ALLOCATED) {
        
          System.out.println("Allocated Project Information: ");
            System.out.printf("Project ID: %d\n", projectID);
        
           //iterate through Supervisor array to find name and email
            for(int i=0;i<SupervisorArr.size();i++) {
            	if(SupervisorArr.get(i).getSupervisorID() == this.supervisorID)
            	{
            		System.out.printf("%s\n", SupervisorArr.get(i).getName());
            		System.out.printf("%s\n", SupervisorArr.get(i).getEmail());
            		
            	}
      		
            }
        
            //iterates through student Array to get name and email
            for(int i=0;i<StudentArr.size();i++) {
            	if(StudentArr.get(i).getStudentID() == this.studentID)
            	{
            		System.out.printf("%s\n", StudentArr.get(i).getName());
            		System.out.printf("%s\n", StudentArr.get(i).getEmail());
            		
            	}
      		
            }
            System.out.printf("Project Title: %s\n", projectTitle);
            System.out.printf("Project Status: %s\n", projectStatus);
    
        }
        
    }
}
