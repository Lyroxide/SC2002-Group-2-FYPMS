package sc2002_assignment;

import java.util.ArrayList;
import java.util.*;
import java.util.Collections;

public class FYPCoordinator extends Supervisor {
	
	private String coordinatorID;
	
	// viewProjects(): changed status (argument) to typeOfProjects, added ArrayList<Project> (argument)
	// viewPendingReques(): under Request class, add requestStatus
	
	public FYPCoordinator(String coordinatorID) {
		this.coordinatorID = coordinatorID;
	}
	
	public void changeSupervisor(ArrayList<Project> project) {
		// will do after arguments to take in is confirmed
	}
	
	public void allocateProject(ArrayList<Project> project, ArrayList<Student> student) {
		// will do after arguments to take in is confirmed
	}
	
	public void deregisterStudent(ArrayList<Project> project, ArrayList<Student> student) {
		// will do after arguments to take in is confirmed
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
				System.out.println(index + "\t" + project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
				index++;
			}
		}
		else {
			for (int i = 0; i < project.size(); i++) {
				if (project.get(i).status == type) {
					System.out.println(index + "\t" + project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
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
	
	public void generateReport(ArrayList<Project> project, ArrayList<Supervisor> supervisor, ArrayList<Student> student, int filterType) {
		// in the argument:
		// ArrayList<Project> project: array list of project class
		int choice, index;
		String type;
		Scanner read = new Scanner(System.in);
		ProjectFilter p = new ProjectFilter();
		
		if (filterType == 1) {
			System.out.println("Generate report by: STATUS");
			System.out.println("Select Project Status to view:");
			System.out.println("(1) Available");
			System.out.println("(2) Unavailable");
			System.out.println("(3) Reserved");
			System.out.println("(4) Allocated");
			System.out.println("(5) All");
			
			do {
				System.out.println("Please select a choice:");
				choice = read.nextInt();
				if (choice < 0 || choice > 5) System.out.println("Invalid choice!");
			} while (choice > 0 && choice < 6);
			
			if (choice == 1) type = "Available";
			else if (choice == 2) type = "Unavailable";
			else if (choice == 3) type = "Reserved";
			else if (choice == 4) type = "Allocated";
			else type = "All";
			
			System.out.println(type + " projects:");
			System.out.println("Student\tSupervisor\tStatus\tProject ID\tProject Title");
			
			index = 1;
			if (type == "All") {
				Collections.sort(project, p.filterType(1));
				for (int i = 0; i < project.size(); i++) {
					System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
							"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
					index++;
				}
			}
			else {
				for (int i = 0; i < project.size(); i++) {
					if (project.get(i).status == type) {
						System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
								"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
						index++;
					}
				}
			}
			
		}
		else if (filterType == 2) {
			System.out.println("Generate report by: SUPERVISOR");
			System.out.println("Select Supervisor to view:");
			for (int i = 0; i < supervisor.size(); i++) {
				System.out.println("(" + (i + 1) + ")" + " " + supervisor.get(i).getSupervisorName);
			}
			System.out.println("(" + (supervisor.size() + 1) + ") All" );
			
			do {
				System.out.println("Please select a choice:");
				choice = read.nextInt();
				if (choice < 0 || choice > (supervisor.size() + 1)) System.out.println("Invalid choice!");
			} while (choice < 0 || choice > (supervisor.size() + 1));
			
			index = 1;
			if (choice == (supervisor.size() + 1)) {
				Collections.sort(project, p.filterType(2));
				for (int i = 0; i < project.size(); i++) {
					System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
							"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
					index++;
				}
			}
			else {
				for (int i = 0; i < project.size(); i++) {
					if (project.get(i).getSupervisorName == supervisor.get(choice - 1).getSupervisorName) {
						System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
								"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
						index++;
					}
				}
			}
			
		}
		else if (filterType == 3) {
			System.out.println("Generate report by: STUDENT");
			System.out.println("Select Student to view:");
			for (int i = 0; i < student.size(); i++) {
				System.out.println("(" + (i + 1) + ")" + " " + student.get(i).getStudentName);
			}
			System.out.println("(" + (student.size() + 1) + ") All" );
			
			do {
				System.out.println("Please select a choice:");
				choice = read.nextInt();
				if (choice < 0 || choice > (student.size() + 1)) System.out.println("Invalid choice!");
			} while (choice < 0 || choice > (student.size() + 1));
			
			index = 1;
			if (choice == (student.size() + 1)) {
				Collections.sort(project, p.filterType(3));
				for (int i = 0; i < project.size(); i++) {
					System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
							"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
					index++;
				}
			}
			else {
				for (int i = 0; i < project.size(); i++) {
					if (project.get(i).getStudentName == student.get(choice - 1).getStudentName) {
						System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
								"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
						break;
					}
				}
			}
			
		}
		else {
			System.out.println("Generate report by: PROJECT");
			System.out.println("Select Project to view:");
			for (int i = 0; i < project.size(); i++) {
				System.out.println("(" + (i + 1) + ")" + " " + project.get(i).getProjectTitle);
			}
			System.out.println("(" + (project.size() + 1) + ") All" );
			
			do {
				System.out.println("Please select a choice:");
				choice = read.nextInt();
				if (choice < 0 || choice > (project.size() + 1)) System.out.println("Invalid choice!");
			} while (choice < 0 || choice > (project.size() + 1));
			
			index = 1;
			if (choice == (project.size() + 1)) {
				Collections.sort(project, p.filterType(4));
				for (int i = 0; i < project.size(); i++) {
					System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
							"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
					index++;
				}
			}
			else {
				for (int i = 0; i < project.size(); i++) {
					if (project.get(i).getProjectID == project.get(choice - 1).getProjectID) {
						System.out.println(index + "\t" + project.get(i).getStudentName + "\t" + project.get(i).getSupervisorName + 
								"\t" + project.get(i).getStatus + "\t" + project.project.get(i).getProjectID + "\t" + project.get(i).getProjectTitle);
						break;
					}
				}
			}
			
		}
		
		// case 1: filter by status
		// case 2: filter by supervisor
		// case 3: filter by student id
		// case 4: filter by project id
	}
	
	public void viewPendingRequest(ArrayList<Request> request) {
		int index = 1;
		
		System.out.println("Pending requests");
		System.out.println("Request type\tSender\tReceiver");
		for (int i = 0; i < request.size(); i++) {
			if (request.get(i).requestStatus == "Pending") {
				System.out.println(index + "\t" + request.get(i).getRequestType + "\t" + request.get(i).getSender + "\t" + 
			request.get(i).getReveiver);
				index++;
			}
		}
	}
	
	public void viewRequestHistory(ArrayList<Request> request) {
		int index = 1;
		
		System.out.println("Requests history");
		System.out.println("Request type\tSender\tReceiver\tStatus");
		for (int i = 0; i < request.size(); i++) {
			System.out.println(index + "\t" + request.get(i).getRequestType + "\t" + request.get(i).getSender + "\t" + 
		request.get(i).getReveiver + "\t" + request.get(i).getStatus);
			index++;
		}
	}
}
