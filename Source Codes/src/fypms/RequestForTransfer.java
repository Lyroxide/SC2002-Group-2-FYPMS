package fypms;

public abstract class RequestForTransfer extends Request {
	
	private String supervisorID;
	
	public RequestForTitle(RequestType type, String sender, String receiver, int projectID, RequestStatus status, String supervisorID) {
		super(request);
		this.supervisorID = supervisorID;
	}
	
	public void setSupervisorID(String title) {
		supervisorID = title;
	}
	
	public String getSupervisorID() {
		return supervisorID;
	}
	
	public void approve(String supervisorID) {
		Project project = null;
		ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID().equals(projectID)) {
                p.setSupervisorID(supervisorID);
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

	public void reject(Request request) {
		request.setStatus(RequestStatus.REJECTED);
		RequestIO.modifyRequest(request);
		System.out.println("You have successfully rejected the request.")
	}
}