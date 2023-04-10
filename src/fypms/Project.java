package fypms;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Class for Project attributes and functions
 *
 * @version 1.0
 */
 
public class Project {

	/**
	 * This is Project's Status
	 */
    private ProjectStatus projectStatus;
	
	/**
	 * This is Project's supervisorID
	 */
    private String supervisorID;
	
	/**
	 * This is Project's studentID
	 */
    private String studentID;
	
	/**
	 * This is Project's ID
	 */
    private int projectID;
	
	/**
	 * This is Project's Title
	 */
    private String projectTitle;

	/**
	 * Project constructor
     * @param projectTitle project title
     * @param supervisorID supervisor ID
	 */
    public Project(String projectTitle, String supervisorID) {
        // Constructor that sets the project's projectID and supervisor ID
        this.projectTitle = projectTitle;
        this.supervisorID = supervisorID;
        this.projectStatus = ProjectStatus.AVAILABLE;
        this.studentID = "";
    }

	/**
	 * Gets Project's Status
	 * @return this project's status
	 */
    public ProjectStatus getStatus() {
        return this.projectStatus;
    }

	/**
	 * Sets Project's Status
	 * @param status Project status
	 */
    public void setStatus(ProjectStatus status) {
        this.projectStatus = status;
    }

	/**
	 * Gets Supervisor ID
	 * @return this Supervisor ID
	 */
    public String getSupervisorID() {
        return this.supervisorID;
    }

	/**
	 * Sets Supervisor ID
	 * @param supervisorID supervisor ID
	 */
    public void setSupervisorID(String supervisorID) { 
		this.supervisorID = supervisorID; 
	}

	/**
	 * Gets Student ID
	 * @return this student ID
	 */
    public String getStudentID() {
        return this.studentID;
    }

	/**
	 * Sets Student ID
	 * @param studentID student ID
	 */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

	/**
	 * Sets project ID
	 * @param projectID project ID
	 */
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

	/**
	 * Gets Project ID
	 * @return this project ID
	 */
    public int getProjectID() {
        return this.projectID;
    }
	
	/**
	 * Gets Project Title
	 * @return this project title
	 */
    public String getProjectTitle() {
        return this.projectTitle;
    }

	/**
	 * Sets project title
	 * @param title project title
	 */
    public void setProjectTitle(String title) { 
		this.projectTitle = title; 
	}

	/**
	 * Prints project info including name and email
	 */
    public void printProjectInfo() {
        ArrayList<Supervisor> SupervisorArr = UserIO.readSupervisors();
        System.out.printf("Project ID: %d ", projectID);
        System.out.printf("| Project Title: %s\n", projectTitle);
        System.out.printf("Project Status: %s ", projectStatus);
        System.out.printf("| Supervisor ID: %s \n", supervisorID);
        for (Supervisor supervisor : SupervisorArr) {
            if (Objects.equals(supervisor.getSupervisorID(), this.supervisorID)) {
                System.out.printf("Supervisor Name: %s ", supervisor.getName());
                System.out.printf("| Supervisor Email: %s ", supervisor.getEmail());
            }
        }
        System.out.println();
        System.out.println();
    }

	/**
	 * Prints project info only for the student's allocated project
	 */
    public void printAllocated() {
        ArrayList<Supervisor> SupervisorArr = UserIO.readSupervisors();
        ArrayList<Student> StudentArr = UserIO.readStudents();
        if (projectStatus == ProjectStatus.ALLOCATED) {

            System.out.println("Allocated Project Information: ");
            System.out.printf("Project ID: %d ", projectID);
            System.out.printf("| Project Title: %s\n", projectTitle);
            System.out.printf("Project Status: %s ", projectStatus);
            System.out.printf("| Supervisor ID: %s\n", supervisorID);
            //iterate through Supervisor array to find name and email
            for (Supervisor supervisor : SupervisorArr) {
                if (Objects.equals(supervisor.getSupervisorID(), this.supervisorID)) {
                    System.out.printf("Supervisor Name: %s ", supervisor.getName());
                    System.out.printf("| Supervisor Email: %s \n", supervisor.getEmail());
                }
            }
            //iterates through student Array to get name and email
            for (Student student : StudentArr) {
                if (Objects.equals(student.getStudentID(), this.studentID)) {
                    System.out.printf("Student Name: %s ", student.getName());
                    System.out.printf("| Student Email: %s", student.getEmail());
                }
            }
            System.out.println();
            System.out.println();
        }
    }

	/**
	 * Function that counts through the number of supervising projects
	 * @param supervisorID supervisor's id
	 * @return count how many supervisor is supervising
     */
    public int superviseNum(String supervisorID) {
        int count = 0;
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getSupervisorID().equals(supervisorID) && p.getStatus().equals(ProjectStatus.ALLOCATED)) {
                count++;
            }
        }
        return count;
    }
}
