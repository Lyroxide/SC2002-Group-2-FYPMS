package fypms;

public abstract class RequestForRegistration extends Request {
	
	public RequestForRegistration(RequestType type, String sender, String receiver, int projectID, RequestStatus status) {
		super(request);
	}
	
	public void approve(String studentID) {
		Project project = null;
		ArrayList<Project> allProjects = ProjectIO.readProjects();
        for (Project p : allProjects) {
            if (p.getProjectID().equals(projectID)) {
                p.setStudentID(studentID);
				if (type.equals(RequestType.ALLOCATION) p.setStatus(Status.ALLOCATED);
				else if (type.equals(RequestType.DEREGISTRATION) p.setStatus(Status.AVAILABLE);
				ProjectIO.modifyProject(p);
				project = p;
                break;
            }
        }
		if (project == null) System.out.println("Project Not Found.");
		else {
			setStatus(RequestStatus.APPROVED);
			supervisorID = project.getSupervisorID();
			int num;
			num = project.superviseNum(supervisorID);
			if (num => 2) {
				for (Project p : allProjects) {
					if (p.getSupervisorID().equals(supervisorID)) {
						if (!p.getStatus().equals(Status.ALLOCATED) && !p.getStatus().equals(Status.RESERVED)) {
							p.setStatus(Status.UNAVAILABLE);
							ProjectIO.modifyProject(p);
						}
					}
				}
			} else if (num == 1 && type.equals(RequestType.DEREGISTRATION)) {
				for (Project p : allProjects) {
					if (p.getSupervisorID().equals(supervisorID)) {
						if (!p.getStatus().equals(Status.ALLOCATED) && !p.getStatus().equals(Status.RESERVED)) {
							p.setStatus(Status.UNAVAILABLE);
							ProjectIO.modifyProject(p);
						}
					}
				}
			}
		}
	}
	
	public void reject(Request request) {
		request.setStatus(RequestStatus.REJECTED);
		RequestIO.modifyRequest(request);
		ArrayList<Student> allStudents = UserIO.getStudents();
		for (Student s : allStudents) {
			if (s.getStudentID().equals(request.getSender())) {
				if (type.equals(RequestType.ALLOCATION) {
					s.setStatus(Status.NEW);
				}
			}
		}
		System.out.println("You have successfully rejected the request.");
	}
}