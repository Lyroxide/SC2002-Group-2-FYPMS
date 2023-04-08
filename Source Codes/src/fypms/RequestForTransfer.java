package fypms;

import java.util.ArrayList;
import java.io.IOException;

public class RequestForTransfer extends Request {

    private String supervisorID;

    public RequestForTransfer(RequestType type, String sender, String receiver, int projectID, RequestStatus status, String supervisorID) {
        super(type, sender, receiver, projectID, status);
        this.supervisorID = supervisorID;
    }


    public String getSupervisorID() {
        return supervisorID;
    }


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

    public void reject(Request request) throws IOException {
        request.setStatus(RequestStatus.REJECTED);
        RequestIO.modifyRequest(request);
        System.out.println("You have successfully rejected the request.");
    }
}
