package fypms;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for Student attributes and functions
 * Inherits User Class
 * @version 1.0
 */
 
public class Student extends User {

	/**
	 * student's userID
	 */
    private String studentID;
	
	/**
	 * Student Status
	 */
    private StudentStatus status;
	
	/**
	 * Student's current project ID
	 */
    private int curProject;

	/**
	 * Default constructor
     * See {@link StudentMenu} where this is called
	 */
    public Student() {}

	/**
	 * Student constructor when reading from file
     * See {@link UserIO} where this is called
     * @param name name
     * @param email email
     * @param userID user ID
     * @param password password
     * @param status Student Status
     * @param userType User Type which is student
     * @param curProject current project ID
	 */
    public Student(String name, String email, String userID, String password, StudentStatus status, UserType userType, int curProject) {
        setName(name);
        setEmail(email);
        this.studentID = userID;
        setPassword(password);
        this.status = status;
        setUserType(userType);
        this.curProject = curProject;
    }

	/**
	 * Gets student id
	 * @return studentID
	 */
    public String getStudentID() {
        return studentID;
    }
	
	/**
	 * Sets student status
	 * @param studentStatus student's status
	 */
    public void setStatus(StudentStatus studentStatus) {
        this.status = studentStatus;
    }
	
	/**
	 * Gets student status
	 * @return studentStatus
	 */
    public StudentStatus getStatus() {
        return this.status;
    }

	/**
	 * Gets current project id
	 * @return curProject
	 */
    public int getCurProject() {
        return curProject;
    }

	/**
	 * Sets student project id
	 * @param id student's project id
	 */
    public void setCurProject(int id) {
        curProject = id;
    }

	/**
	 * Sets student id
	 * @param studentID student's id
	 */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
	
	/**
	 * Function that returns all AVAILABLE projects
	 * @return Array List of {@link Project}
     */
    public ArrayList<Project> viewAvailableProjects() {
        ArrayList<Project> availableProjects = new ArrayList<>();
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project project : allProjects) {
            if (project.getStatus().equals(ProjectStatus.AVAILABLE)) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }

	/**
	 * Function that makes an allocation {@link Request} to {@link FYPCoordinator}
	 * @param projectID student wants to register this project ID
	 * @throws IOException IOException
	 */
    public void selectProject(int projectID) throws IOException {
        ArrayList<Project> projects = ProjectIO.readProjects();
        if (projectID > projects.size()) {
            System.out.println("Project does not exist.");
        }
        else {
            int count = 0;
            for (Project p : projects) {
                if (p.getProjectID() ==  projectID && p.getStatus() == ProjectStatus.AVAILABLE) {
                    Request request = new RequestForRegistration(RequestType.ALLOCATION, this.studentID, "FYPCoordinator", projectID, RequestStatus.PENDING);
                    RequestIO.writeRequest(request);
                    setStatus(StudentStatus.PENDING);
                    p.setStatus(ProjectStatus.RESERVED);
                    ProjectIO.modifyProject(p);
                    System.out.println("Project Selection Requested. Please wait for approval.");
                    count++;
                    break;
                }
            }
            if (count == 0) System.out.println("You cannot select this project.");

        }



    }

	/**
	 * Function that makes a de-registration {@link Request} to {@link FYPCoordinator}
	 * @throws IOException IOException
	 */
    public void deregisterProject() throws IOException {
        Request request = new RequestForRegistration(RequestType.DEREGISTRATION, this.studentID, "FYPCoordinator", this.curProject, RequestStatus.PENDING);
        RequestIO.writeRequest(request);
        System.out.println("Project De-registration Requested. Please wait for approval.");
    }

	/**
	 * Function that makes a title change {@link Request} to {@link Supervisor}
	 * @param newTitle new project title
	 * @throws IOException IOException
	 */
    public void changeProjectTitle(String newTitle) throws IOException {
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID() == this.curProject) {
                Request request = new RequestForTitle(RequestType.TITLECHANGE, this.studentID, p.getSupervisorID(), this.curProject, RequestStatus.PENDING, newTitle);
                RequestIO.writeRequest(request);
                System.out.println("Project Title Change Requested. Please wait for approval.");
            }
        }
    }

	/**
	 * Function that returns all processed requests
	 * @param studentID this student's id
	 * @return Array List of {@link Request}
	 */
    public ArrayList<Request> viewRequests(String studentID) {
        ArrayList<Request> ownRequests = new ArrayList<>();
        ArrayList<Request> allRequests = RequestIO.readRequests();
        for (Request r : allRequests) {
            if (r.getSender().equals(studentID) && !r.getStatus().equals(RequestStatus.PENDING)) {
                ownRequests.add(r);
            }
        }
        return ownRequests;
    }
}


