package fypms;

import java.io.*;
import java.util.ArrayList;

public class RequestForRegistration extends Request {

    public RequestForRegistration(RequestType type, String sender, String receiver, int projectID, RequestStatus status) {
        super(type, sender, receiver, projectID, status);
    }

    public void approve(String studentID) throws IOException {
        Project project = null;
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID() == getProjectID()) {
                p.setStudentID(studentID);
                if (getType().equals(RequestType.ALLOCATION)) p.setStatus(ProjectStatus.ALLOCATED);
                else if (getType().equals(RequestType.DEREGISTRATION)) p.setStatus(ProjectStatus.AVAILABLE);
                ProjectIO.modifyProject(p);
                project = p;
                break;
            }
        }
        if (project == null) System.out.println("Project Not Found.");
        else {
            setStatus(RequestStatus.APPROVED);
            String supervisorID = project.getSupervisorID();
            int num;
            num = project.superviseNum(supervisorID);
            if (num >= 2) {
                for (Project p : allProjects) {
                    if (p.getSupervisorID().equals(supervisorID)) {
                        if (!p.getStatus().equals(ProjectStatus.ALLOCATED) && !p.getStatus().equals(ProjectStatus.RESERVED)) {
                            p.setStatus(ProjectStatus.UNAVAILABLE);
                            ProjectIO.modifyProject(p);
                        }
                    }
                }
            } else if (num == 1 && getType().equals(RequestType.DEREGISTRATION)) {
                for (Project p : allProjects) {
                    if (p.getSupervisorID().equals(supervisorID)) {
                        if (!p.getStatus().equals(ProjectStatus.ALLOCATED) && !p.getStatus().equals(ProjectStatus.RESERVED)) {
                            p.setStatus(ProjectStatus.UNAVAILABLE);
                            ProjectIO.modifyProject(p);
                        }
                    }
                }
            }
        }
    }

    public void reject(Request request) throws IOException {
        request.setStatus(RequestStatus.REJECTED);
        RequestIO.modifyRequest(request);
        ArrayList<Student> allStudents = UserIO.readStudents();
        for (Student s : allStudents) {
            if (s.getStudentID().equals(request.getSender())) {
                if (getType().equals(RequestType.ALLOCATION)) {
                    s.setStatus(StudentStatus.NEW);
                }
            }
        }
        System.out.println("You have successfully rejected the request.");
    }
}