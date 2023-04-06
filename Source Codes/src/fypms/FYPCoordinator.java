package fypms;

import java.util.ArrayList;
import java.util.*;
import java.util.Collections;

public class FYPCoordinator extends Supervisor {
	
	private String coordinatorID;
	
	public FYPCoordinator(String coordinatorID) {
		this.coordinatorID = coordinatorID;
	}
	
	public void changeSupervisor(Supervisor s1, Supervisor s2, Project project) {
		ArrayList<Project> proj = new ArrayList<>();
		proj = s1.getProjectsSupervising;
		
		for (int i = 0; i < 2; i++) {
			if (proj.get(i).getProjectID == project.getProjectID) {
				s1.setProjectsSupervising(null);
				s2.setProjectsSupervising(project);
				project.setSupervisorID(s2.getSupervisorID);
			}
		}
		s1.superviseMax();
		s2.superviseMax();
	}
	
	public void allocateProject(Student student, Supervisor supervisor, Project project) {
		student.setCurProject(project.getProjectID);
		project.setStudentID(student.getStudentID);
		supervisor.projectsSupervising.add(project.getProjectID);
		supervisor.superviseMax();
	}
	
	public void deregisterStudent(Project project, Student student, Supervisor supervisor) {
		ArrayList<Project> proj = new ArrayList<>();
		proj = supervisor.getProjectsSupervising;
		
		student.setCurProject(null);
		student.setProjRegistered(false);
		project.setProjectStatus("Available");
		project.setStudentID(null);
		for (int i = 0; i < 2; i++) {
			if (proj.get(i).getProjectID == project.getProjectID) {
				supervisor.setProjectsSupervising(null);
			}
		}
		supervisor.superviseMax();
	}
	
	
	public void generateReport(ArrayList<Project> project, ArrayList<Supervisor> supervisor, ArrayList<Student> student, int filterType) {
		
		int choice, index;
		String type;
		Scanner read = new Scanner(System.in);
		ProjectFilter p = new ProjectFilter();
		
		// for Project, can readProject() into arraylist and print
		// but students and supervisor, how do i access all students and supervisor?
		
		
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
	
	public ArrayList<Request> viewPendingRequest() {
		ArrayList<Request> req = new ArrayList<>();
		int i = 0;
		
		req = readRequests();
		
		while (i < req.size()) {
			if (req.get(i).getStatus != RequestStatus.PENDING) req.remove(1);
			else i++;
		}
		
		return req;
	}
	
	public ArrayList<Request> viewRequestHistory() {
		
		ArrayList<Request> req = new ArrayList<>();
		
		req = readRequests();
		
		return req;
	}
}
