package fypms;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Inherited class from {@link Request} to handle title change requests
 * @version 1.0
 */
public class RequestForTitle extends Request {

	/**
	 * This is project title
	 */
    private String projectTitle;

	/**
	 * Constructor <p></p>
     * See {@link Student} and {@link RequestIO} where this is called
     * @param type request type
     * @param sender sender userID
     * @param receiver receiver userID or FYPCoordinator
     * @param projectID project ID
     * @param status Request Status
     * @param projectTitle new project title
	 */
    public RequestForTitle(RequestType type, String sender, String receiver, int projectID, RequestStatus status, String projectTitle) {
        super(type, sender, receiver, projectID, status);
        this.projectTitle = projectTitle;
    }

	/**
	 * Gets {@link Project} title
	 * @return project title
	 */
    public String getProjectTitle() {
        return projectTitle;
    }
	
	/**
	 * Approve function that changes {@link Project}'s attributes
	 * @param title new title
	 * @throws IOException IOException
	 */
    public void approve(String title) throws IOException {
        Project project = null;
        ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID() == getProjectID()) {
                p.setProjectTitle(title);
                ProjectIO.modifyProject(p);
                project = p;
                break;
            }
        }
        if (project == null) System.out.println("Project Not Found.");
        else {
            System.out.println("You have approved request.");
            setStatus(RequestStatus.APPROVED);
        }
    }

	/**
	 * Implemented Reject function that changes request status
	 * @param request {@link Request} instance
	 * @throws IOException IOException
	 */
    public void reject(Request request) throws IOException {
        request.setStatus(RequestStatus.REJECTED);
        RequestIO.modifyRequest(request);
        System.out.println("You have successfully rejected the request.");
    }
}
