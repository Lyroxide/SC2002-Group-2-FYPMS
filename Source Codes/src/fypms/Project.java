package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Project {

    private ProjectStatus projectStatus;
    private String supervisorID;
    private String studentID;
    private int projectID;
    private String projectTitle;

    public Project(String projectTitle, String supervisorID) {
        // Constructor that sets the project's projectID and supervisor ID
        this.projectTitle= projectTitle;
        this.supervisorID = supervisorID;
    }


    public ProjectStatus getStatus() {
        return this.projectStatus;
    }

    public void setStatus(ProjectStatus status) {
        this.projectStatus = status;
    }

    public String getSupervisorID() {
        return this.supervisorID;
    }

    public void setSupervisorID(String supervisorID) { this.supervisorID = supervisorID; }

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

    public void setProjectTitle(String title) { this.projectTitle = title; }


    public void printProjectInfo() {
        ArrayList<Supervisor> SupervisorArr = UserIO.readSupervisors();
        System.out.printf("Project ID: %d ", projectID);
        System.out.printf("| Project Title: %s\n", projectTitle);
        System.out.printf("Project Status: %s ", projectStatus);
        System.out.printf("| Supervisor ID: %s ", supervisorID);
        for (Supervisor supervisor : SupervisorArr) {
            if (Objects.equals(supervisor.getSupervisorID(), this.supervisorID)) {
                System.out.printf("| Supervisor Name: %s ", supervisor.getName());
                System.out.printf("| Supervisor Email: %s ", supervisor.getEmail());
            }
        }
        System.out.println();
        System.out.println();
    }

    public void printAllocated() {
        ArrayList<Supervisor> SupervisorArr = UserIO.readSupervisors();
        ArrayList<Student> StudentArr = UserIO.readStudents();
        if (projectStatus == ProjectStatus.ALLOCATED) {

            System.out.println("Allocated Project Information: ");
            System.out.printf("Project ID: %d", projectID);
            System.out.printf("| Project Title: %s\n", projectTitle);
            System.out.printf("Project Status: %s ", projectStatus);
            System.out.printf("| Supervisor ID: %s ", supervisorID);
            //iterate through Supervisor array to find name and email
            for (Supervisor supervisor : SupervisorArr) {
                if (Objects.equals(supervisor.getSupervisorID(), this.supervisorID)) {
                    System.out.printf("| Supervisor Name: %s ", supervisor.getName());
                    System.out.printf("| Supervisor Email: %s", supervisor.getEmail());
                }

            }

            //iterates through student Array to get name and email
            for (Student student : StudentArr) {
                if (Objects.equals(student.getStudentID(), this.studentID)) {
                    System.out.printf("| Student Name: %s", student.getName());
                    System.out.printf("| Student Email: %s", student.getEmail());
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    public int superviseNum(String supervisorID) throws IOException {
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
