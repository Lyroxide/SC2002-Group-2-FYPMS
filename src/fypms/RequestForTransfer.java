package fypms;

import java.util.ArrayList;
import java.io.IOException;

/**
 * Inherited class from {@link Request} to handle supervisor transfer requests
 * @version 1.0
 */
public class RequestForTransfer extends Request {

	/**
	 * This is new supervisor ID
	 */
    private String supervisorID;

	/**
	 * Constructor for RequestForTransfer
     * See {@link Supervisor} or {@link RequestIO} where this is called
     * @param type request type
     * @param sender sender userID
     * @param receiver receiver userID or FYPCoordinator
     * @param projectID project ID
     * @param status Request Status
     * @param supervisorID new supervisor ID
	 */
    public RequestForTransfer(RequestType type, String sender, String receiver, int projectID, RequestStatus status, String supervisorID) {
        super(type, sender, receiver, projectID, status);
        this.supervisorID = supervisorID;
    }

	/**
	 * Gets new {@link Supervisor} id
	 * @return supervisor id
	 */
    public String getSupervisorID() {
        return supervisorID;
    }

	/**
	 * Approve function that changes project's {@link Supervisor} and checks for {@link Supervisor} MAX
	 * @param supervisorID new supervisor id
	 * @throws IOException IOException
	 */
    public void approve(String supervisorID) throws IOException {
        Project project = null;
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID() == getProjectID()) {
                p.setSupervisorID(supervisorID);
                ProjectIO.modifyProject(p);
                project = p;
                break;
            }
        }
        if (project == null) System.out.println("Project Not Found.");
        else {
            System.out.println("You have approved request.");
            setStatus(RequestStatus.APPROVED);
            int num;
            num = project.superviseNum(project.getSupervisorID());
            if (num >= 2) {
                for (Project p : allProjects) {
                    if (p.getSupervisorID().equals(supervisorID)) {
                        if (!p.getStatus().equals(ProjectStatus.ALLOCATED) && !p.getStatus().equals(ProjectStatus.RESERVED)) {
                            p.setStatus(ProjectStatus.UNAVAILABLE);
                            ProjectIO.modifyProject(p);
                        }
                    }
                }
            }
            num = project.superviseNum(getSender());
            if (num == 1) {
                for (Project p : allProjects) {
                    if (p.getSupervisorID().equals(getSender())) {
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
	 * Implemented Reject function that changes {@link Request} status
	 * @param request {@link Request} instance
	 * @throws IOException IOException
	 */
    public void reject(Request request) throws IOException {
        request.setStatus(RequestStatus.REJECTED);
        RequestIO.modifyRequest(request);
        System.out.println("You have successfully rejected the request.");
    }
}
