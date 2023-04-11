package fypms;

import java.io.*;
import java.util.ArrayList;

/**
 * Inherited class from {@link Request} to handle registration/de-registration requests
 * @version 1.0
 */
public class RequestForRegistration extends Request {

	/**
	 * Constructor <p></p>
     * See {@link Student} and {@link RequestIO} where this is called
     * @param type request type
     * @param sender sender userID
     * @param receiver receiver userID or FYPCoordinator
     * @param projectID project ID
     * @param status Request Status
	 */
    public RequestForRegistration(RequestType type, String sender, String receiver, int projectID, RequestStatus status) {
        super(type, sender, receiver, projectID, status);
    }

	/**
	 * Approve function that changes {@link Project}'s attributes while checking for {@link Supervisor} MAX
	 * @param studentID id of {@link Student}'s request
	 * @throws IOException IOException
	 */
    public void approve(String studentID) throws IOException {
        Project project = null;
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID() == getProjectID()) {
                if (getType().equals(RequestType.ALLOCATION)) {
                    p.setStatus(ProjectStatus.ALLOCATED);
                    p.setStudentID(studentID);
                }
                else if (getType().equals(RequestType.DEREGISTRATION)) {
                    p.setStatus(ProjectStatus.AVAILABLE);
                    p.setStudentID("");
                }
                ProjectIO.modifyProject(p);
                project = p;
                break;
            }
        }
        if (project == null) System.out.println("Project Not Found.");
        else {
            System.out.println("You have approved request.");
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
                            p.setStatus(ProjectStatus.AVAILABLE);
                            ProjectIO.modifyProject(p);
                        }
                    }
                }
            }
        }
    }

	/**
	 * Implemented Reject function that changes {@link Request} status and {@link Student} status if is allocation request
	 * @param request {@link Request} instance
	 * @throws IOException IOException
	 */
    public void reject(Request request) throws IOException {
        request.setStatus(RequestStatus.REJECTED);
        RequestIO.modifyRequest(request);
        ArrayList<Student> allStudents = UserIO.readStudents();
        for (Student s : allStudents) {
            if (s.getStudentID().equals(request.getSender())) {
                if (getType().equals(RequestType.ALLOCATION)) {
                    s.setStatus(StudentStatus.NEW);
                    UserIO.writeStudentStatus(s);
                }
            }
        }
        System.out.println("You have successfully rejected the request.");
    }
}