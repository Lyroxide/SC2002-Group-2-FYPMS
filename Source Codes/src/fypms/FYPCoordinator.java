package sc2002_assignment;

import java.util.ArrayList;
import java.util.Collections;

public class FYPCoordinator extends Supervisor {
	
	private String coordinatorID;
	
	// viewProjects(): changed status (argument) to typeOfProjects, added ArrayList<Project> (argument)
	
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
	
	public void viewProjects(ArrayList<Project> project, int typeOfProject) {
		// in the argument:
		// ArrayList<Project> project: array list of project class
		// int typeOfProject: the type of project to be displayed
		int index = 1;
		String type;
		
		if (typeOfProject == 1) type = "Available";
		else if (typeOfProject == 2) type = "Unavailable";
		else if (typeOfProject == 3) type = "Reserved";
		else if (typeOfProject == 4) type = "Allocated";
		else type = "All";
		
		System.out.println(type + " projects:");
		System.out.println("\tProject ID\tProject Title");
		
		if (type == "All") {
			for (int i = 0; i < project.size(); i++) {
				System.out.println(index + "\t" + project.get(i).projectID + "\t" + project.get(i).projectTitle);
				index++;
			}
		}
		else {
			for (int i = 0; i < project.size(); i++) {
				if (project.get(i).status == type) {
					System.out.println(index + "\t" + project.get(i).projectID + "\t" + project.get(i).projectTitle);
					index++;
				}
			}
		}
		// case 1: available
		// case 2: unavailable
		// case 3: reserved
		// case 4: allocated
		// case 5: all
		
		// project class:
		// status
		// supervisorID
		// studentID
		// projectID
		// projectTitle
	}
	
	public void generateReport(ArrayList<Project> project, int filterType, int noOfSupervisors) {
		// in the argument:
		// ArrayList<Project> project: array list of project class
		// int filterType: filter based on ?
		
		/*
		ArrayList<String> tempArray = new ArrayList<>();
		
		switch (filterType) {
			case 1: {
				System.out.println("Project Detail Report (filtered by Project Status)");
				System.out.println("Status\tSupervisor ID\tStudent ID\tProject ID\tProject Title");
				tempArray.add("Available");
				tempArray.add("Unavailable");
				tempArray.add("Reserved");
				tempArray.add("Allocated");
				
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < project.size(); j++) {
						if (project.get(i).status == tempArray.get(i)) {
							System.out.println(project.get(j).status + "\t" + project.get(j).supervisorID + "\t" + project.get(j).studentID + 
									"\t" + project.get(j).projectID + "\t" + project.get(j).projectTitle);
						}
					}
				}
				break;
			}
			case 2: {
				System.out.println("Project Detail Report (filtered by Supervisor)");
				System.out.println("Supervisor ID\tStudent ID\tProject ID\tProject Title\tStatus");
				
				
				for (int i = 0; i < noOfSupervisors; i++) { // loop count: no. of supervisors
					for (int k = 0; k < project.size(); k++) { // if ID is not in tempArray, add in tempArray
						if (tempArray.size() == 0 || project.get(k).supervisorID != tempArray.get(tempArray.size() - 1)) {
							tempArray.add(project.get(k).supervisorID);
							break;
						}
					}
					for (int j = 0; j < project.size(); j++) { // if ID equals last of tempArray, print
						if (project.get(j).supervisorID == tempArray.get(tempArray.size() - 1)) {
							System.out.println(project.get(j).supervisorID + "\t" + project.get(j).studentID + 
									"\t" + project.get(j).projectID + "\t" + project.get(j).projectTitle + "\t" + project.get(j).status);
						}
						
					}
				}
				break;
			}
			case 3: {
				
				break;
			}
		}*/
		
		
		// to be added in Project class for sorting with Comparable
		/*
		public int compareTo(Project project, int filterType) {
			if (filterType == 1) return this.status.compareTo(project.status);
			else if (filterType == 2) return this.supervisorName.compareTo(project.supervisorName);
			else if (filterType == 3) return this.studentName.compareTo(project.studentName);
			else return this.projectID - project.projectID;
		}*/
		ProjectFilter p = new ProjectFilter();
		
		Collections.sort(project, p.filterType(1));
		if (filterType == 1) {
			Collections.sort(project, p.filterType(1));
			System.out.println("Project Detail Report (filtered by Project Status)");
			System.out.println("Status\tSupervisor name\tStudent name\tProject ID\tProject Title");
			for (int i = 0; i < project.size(); i++) {
				System.out.println(project.get(i).status + "\t" + project.get(i).supervisorName + "\t" + project.get(i).studentName + 
						"\t" + project.get(i).projectID + "\t" + project.get(i).projectTitle);
			}
		}
		else if (filterType == 2) {
			Collections.sort(project, p.filterType(2));
			System.out.println("Project Detail Report (filtered by Supervisor)");
			System.out.println("Supervisor Name\tStudent Name\tProject ID\tProject Title\tStatus");
			for (int i = 0; i < project.size(); i++) {
				System.out.println(project.get(i).supervisorName + "\t" + project.get(i).studentName + 
						"\t" + project.get(i).projectID + "\t" + project.get(i).projectTitle + "\t" + project.get(i).status);
			}
			
		}
		else if (filterType == 3) {
			Collections.sort(project, p.filterType(3));
			System.out.println("Project Detail Report (filtered by Student)");
			System.out.println("Student Name\tSupervisor Name\tProject ID\tProject Title\tStatus");
			for (int i = 0; i < project.size(); i++) {
				System.out.println(project.get(i).studentName + "\t" + project.get(i).supervisorName + 
						"\t" + project.get(i).projectID + "\t" + project.get(i).projectTitle + "\t" + project.get(i).status);
			}
		}
		else {
			Collections.sort(project, p.filterType(4));
			System.out.println("Project Detail Report (filtered by Project)");
			System.out.println("Project ID\tProject Title\tSupervisor Name\tStudent Name\tStatus");
			for (int i = 0; i < project.size(); i++) {
				System.out.println(project.get(i).projectID + "\t" + project.get(i).projectTitle + 
						"\t" + project.get(i).supervisorName + "\t" + project.get(i).studentName + "\t" + project.get(i).status);
			}
		}
		
		// case 1: filter by status
		// case 2: filter by supervisor
		// case 3: filter by student id
		// case 4: filter by project id
	}
	
	public ArrayList<Request> viewPendingRequest() {
		// under Request class, change requestType to requestStatus?
	}
	
	public ArrayList<Request> viewRequestHistory() {
		
	}
	
	public void filter() {
		
	}
}
