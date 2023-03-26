package sc2002_assignment;

import java.util.ArrayList;

public class FYPCoordinator extends Supervisor {
	
	private String coordinatorID;
	
	public FYPCoordinator(String coordinatorID) {
		this.coordinatorID = coordinatorID;
	}
	
	public void changeSupervisor(Project project, String supervisorID) {
		project.setSupervisorID(supervisorID);
	}
	
	public void allocateProject(Student student, Project project) {
		student.setCurProject(project);
	}
	
	public void deregisterStudent(Student student, Project project) {
		project.setStudentID(null);
		project.setStatus("Available");
		student.setCurProject(null);
		student.setProjRegistered(false);
	}
	
	public ArrayList<Project> viewProjects(String status) {
		// copy the projects from the file and store it into the arraylist, then print it in main()?
	}
	
	public void generateReport(int filterType) {
		// project file should store all its attributes
		// generateReport() will read the file and display
		// filterType will be applied when file is read, append the file based on filter, and print
	}
	
	public ArrayList<Request> viewPendingRequest() {
		// under Request class, change requestType to requestStatus?
	}
	
	public ArrayList<Request> viewRequestHistory() {
		
	}
	
	public void filter() {
		
	}
	
	testing
}
