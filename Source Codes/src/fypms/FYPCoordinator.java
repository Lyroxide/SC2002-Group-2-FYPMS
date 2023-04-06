package fypms;

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
	
	public void generateReport(int filterType) {
		
		int choice, i = 0;
		Scanner read = new Scanner(System.in);
		
		if (filterType == 1) {
			ArrayList<Project> proj = new ArrayList<>();
			proj = ProjectIO.readProjects();
			
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
			
			if (choice == 1) {
				while (i < proj.size()) {
					if (proj.get(i).getStatus != Status.AVAILABLE)  proj.remove(i);
					else i++;
				}
			}
			else if (choice == 2) {
				while (i < proj.size()) {
					if (proj.get(i).getStatus != Status.UNAVAILABLE)  proj.remove(i);
					else i++;
				}
			}
			else if (choice == 3) {
				while (i < proj.size()) {
					if (proj.get(i).getStatus != Status.RESERVED)  proj.remove(i);
					else i++;
				}
			}
			else if (choice == 4) {
				while (i < proj.size()) {
					if (proj.get(i).getStatus != Status.ALLOCATED)  proj.remove(i);
					else i++;
				}
			}
			
			return proj;
		}
		else if (filterType == 2) {
			
			ArrayList<Supervisor> sup = new ArrayList<>();
			sup = UserIO.readSupervisors();
			
			System.out.println("Generate report by: SUPERVISOR");
			System.out.println("Select Supervisor to view:");
			for (i = 0; i < sup.size(); i++) {
				System.out.println("(" + i + 1 + ")" + sup.get(i).getName());
			}
			System.out.println("(" + sup.size() + 1 + ") All");
			
			do {
				System.out.println("Please select a choice:");
				choice = read.nextInt();
				if (choice < 0 || choice > sup.size()) System.out.println("Invalid choice!");
			} while (choice < 0 || choice > sup.size());
			
			if (choice <= sup.size()) {
				i = 0;
				while (i < sup.size()) {
					if (sup.get(i).getName() != sup.get(choice - 1).getName()) sup.remove(i);
					else i++;
				}
			}
			return sup;
		}
		else if (filterType == 3) {
			
			ArrayList<Student> stu = new ArrayList<>();
			stu = UserIO.readStudents();
			
			System.out.println("Generate report by: STUDENT");
			System.out.println("Select Student to view:");
			for (i = 0; i < stu.size(); i++) {
				System.out.println("(" + i + 1 + ")" + stu.get(i).getName());
			}
			System.out.println("(" + stu.size() + 1 + ") All");
			
			do {
				System.out.println("Please select a choice:");
				choice = read.nextInt();
				if (choice < 0 || choice > stu.size()) System.out.println("Invalid choice!");
			} while (choice < 0 || choice > stu.size());
			
			if (choice <= stu.size()) {
				i = 0;
				while (i < stu.size()) {
					if (stu.get(i).getName() != stu.get(choice - 1).getName()) stu.remove(i);
					else i++;
				}
			}
			return stu;
		}
		else {
			ArrayList<Project> proj = new ArrayList<>();
			proj = ProjectIO.readProjects();
			
			System.out.println("Generate report by: PROJECTS");
			System.out.println("Select Projects to view:");
			for (i = 0; i < proj.size(); i++) {
				System.out.println("(" + i + 1 + ")" + proj.get(i).getName());
			}
			System.out.println("(" + proj.size() + 1 + ") All");
			
			do {
				System.out.println("Please select a choice:");
				choice = read.nextInt();
				if (choice < 0 || choice > proj.size()) System.out.println("Invalid choice!");
			} while (choice < 0 || choice > proj.size());
			
			if (choice <= proj.size()) {
				i = 0;
				while (i < proj.size()) {
					if (proj.get(i).getName() != proj.get(choice - 1).getName()) proj.remove(i);
					else i++;
				}
			}
			return proj;
		}
	}
	
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
