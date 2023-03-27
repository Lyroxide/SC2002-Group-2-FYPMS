package fypms;

public enum ProjectChangeStatus {

	AVAILABLE("Project allocation request was denied");
	ALLOCATED("Project has been successfully allocated");
	RESERVED("Project is currently reserved by a student");
	
	private String projectStatus;
	
	ProjectChangeStatus(String status) {
		this.projectStatus = status;
	}
	
	public String returnStatus() {
		return status;
	}
}
