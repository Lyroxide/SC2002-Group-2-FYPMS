package fypms;

import java.io.IOException;
import java.util.ArrayList;

public class RequestForTitle extends Request {

    private String projectTitle;

    public RequestForTitle(RequestType type, String sender, String receiver, int projectID, RequestStatus status, String projectTitle) {
        super(type, sender, receiver, projectID, status);
        this.projectTitle = projectTitle;
    }

    public void setProjectTitle(String title) {
        projectTitle = title;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

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
            setStatus(RequestStatus.APPROVED);
        }
    }

    public void reject(Request request) throws IOException {
        request.setStatus(RequestStatus.REJECTED);
        RequestIO.modifyRequest(request);
        System.out.println("You have successfully rejected the request.");
    }
}
