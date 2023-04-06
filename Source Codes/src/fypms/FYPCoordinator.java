package sc2002_assignment;

import java.util.ArrayList;
import java.util.*;
import java.util.Collections;

public class FYPCoordinator extends Supervisor {
	
	private String coordinatorID;
	
	public FYPCoordinator(String coordinatorID) {
		this.coordinatorID = coordinatorID;
	}
	
	public String getCoordinatorID() {
		return this.coordinatorID;
	}
	
	public void setCoordinatorID(String coordinatorID) {
		this.coordinatorID = coordinatorID;
	}
	
	public void changeSupervisor(Request request) {
		
		int newSupID;
		
		if (request instanceof RequestForTransfer) {
			ArrayList<Project> proj = new ArrayList<>();
			proj = ProjectIO.readProjects();
			
			for (int i = 0; i < proj.size(); i++) {
				if (proj.get(i).getProjectID == request.getProjectID) {
					newSupID = proj.get(i).getSupervisorID;
					supNum = superviseNum(newSupID);
					break;
				}
			}
			
			if (supNum < 2) {
				RequestForTransfer.approve(newSupID);
				RequestIO.modifyRequest(request);
				
			}
			else {
				System.out.println("Project cannot be transferred to Supervisor " + newSupID
						+ " because he/she have 2 projects already");
			}
		}
		else {
			System.out.println("Request is of the wrong type");
		}
	}
	
	public void allocateProject (Request request) {
		
		int supNum;
		String supID;
		
		if (request instanceof RequestForRegistration) {
			ArrayList<Project> proj = new ArrayList<>();
			proj = ProjectIO.readProjects();
			
			for (int i = 0; i < proj.size(); i++) {
				if (proj.get(i).getProjectID == request.getProjectID) {
					supID = proj.get(i).getSupervisorID;
					supNum = superviseNum(supID);
					break;
				}
			}
			if (supNum < 2) {
				RequestForRegistration.approve(request.getSender());
				RequestIO.modifyRequest(request);
				
				ArrayList<Student> stu = new ArrayList<>();
				stu = UserIO.getStudents();
				for (int i = 0; i < stu.size(); i++) {
					if (stu.get(i).getStudentID == request.getSender()) {
						stu.get(i).setStatus(Status.REGISTERED);
						stu.get(i).setCurProject(request.getProjectID());
						break;
					}
				}
			}
			else {
				System.out.println("Supervisor " + supID + " is supervising 2 projects currently already. ");
				System.out.println("Current request status will remain as PENDING");
			}
		}
		else {
			System.out.println("Request is of the wrong type");
		}
	}
	
	public void deregisterStudent(Request request) {
		if (request instanceof RequestForRegistration) {
			RequestForRegistration.approve(request.getSenderID());
			RequestIO.modifyRequest(request);
			
			ArrayList<Student> stu = new ArrayList<>();
			stu = UserIO.getStudents();
			for (int i = 0; i < stu.size(); i++) {
				if (stu.get(i).getStudentID == request.getSender()) {
					stu.get(i).setStatus(Status.UNREGISTERED);
					stu.get(i).setCurProject(null);
					break;
				}
			}
		}
		else {
			System.out.println("Request is of the wrong type");
		}
	}
	
	/*public void generateReport(int filterType) {
		
		int choice, i = 0;
		Scanner read = new Scanner(System.in);
		
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
			} while (choice < 0 || choice > 6);
			
			ArrayList<Project> proj = new ArrayList<>();
			proj = ProjectIO.readProjects();
			
			if (choice == 1) {
				while (i < proj.size()) {
					if (proj.get(i).getStatus != Status.AVAILABLE)  proj.remove(i);
					else i++;
				}
			}
			
		}
	}
	
	
	public void generateReport(int filterType) {
		// old one
		int choice, index;
		String type;
		Scanner read = new Scanner(System.in);
		ProjectFilter p = new ProjectFilter();
		
		// argument take in filter type only
		// read in various file
		// perform filter
		// return array list
		
		
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
	}*/
	
	public ArrayList<Request> viewPendingRequest() {
		ArrayList<Request> req = new ArrayList<>();
		int i = 0;
		
		req = RequestIO.readRequests();
		
		while (i < req.size()) {
			if (req.get(i).getStatus != RequestStatus.PENDING) req.remove(i);
			else i++;
		}
		
		return req;
	}
	
	public ArrayList<Request> viewRequestHistory() {
		
		ArrayList<Request> req = new ArrayList<>();
		
		req = RequestIO.readRequests();
		
		return req;
	}
}
