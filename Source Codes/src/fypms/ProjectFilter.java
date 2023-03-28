package sc2002_assignment;

import java.util.Comparator;

public class ProjectFilter implements Comparator<Project>{
	private int filterType;
	
	/*public ProjectFilter(int filterType) {
		this.filterType = filterType;
	}*/
	
	public ProjectFilter filterType(int filterType) {
		this.filterType = filterType;
		return this;
	}
	
	@Override
	public int compare(Project p1, Project p2) {
		if (filterType == 1) return p1.status.compareTo(p2.status);
		else if (filterType == 2) return p1.supervisorName.compareTo(p2.supervisorName);
		else if (filterType == 3) return p1.studentName.compareTo(p2.studentName);
		else return p1.projectID - p2.projectID;
	}
	
}
