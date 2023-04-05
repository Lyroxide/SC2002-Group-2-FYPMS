package fypms;

public abstract class RequestForTitle extends Request {
	
	private String projectTitle;
	
	public RequestForTitle(RequestType type, String sender, String receiver, int projectID, RequestStatus status, String projectTitle) {
		super(request);
		this.projectTitle = projectTitle;
	}
	
	public void setProjectTitle(String title) {
		projectTitle = title;
	}
	
	public String getProjectTitle() {
		return projectTitle;
	}
	
	public void approve(String title) {
		Project project = null;
		ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID().equals(projectID)) {
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
	
	public void reject(Request request) {
		request.setStatus(RequestStatus.REJECTED);
		RequestIO.modifyRequest(request);
		System.out.println("You have successfully rejected the request.");
	}
}