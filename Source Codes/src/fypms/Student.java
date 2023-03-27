package fypms;

public class Student extends User{
    
//     private enum Status {
//         newStudent,titleChange, requestProj, projRegistered;
//     }
    
    private String name;
    private String email;
    private String studentID;
    private String curProject;
    private String request;
    private ArrayList<String> requestsHistory;
    private boolean projRegistered;
    private boolean titleChange;
    private boolean requestProj;


    //change constructor when extending from User class
    public Student(String name, String email, String studentID,String request) {
        // Constructor that sets the student's name, email, and studentID
        this.name = name;
        this.email = email;
        this.studentID = super.getUserID;
        request = null;
        this.requestsHistory = new ArrayList<>();
    }

    public String getName() {
        // Returns the student's name
        return this.name;
    }

    public String getEmail() {
        // Returns the student's email
        return this.email;
    }

    public String getStudentID() {
        // Returns the student's studentID
        return this.studentID;
    }
    
    public void printUserInfo(){
        System.out.printf("%s, %s, %s \n",getName(),getEmail(),getStudentID());
    }
    
    //call method from project class
    public ArrayList<String> viewAvailableProjects(){
        //read from project list
        //print projects 
        //return project list
        TextDB txtDB = new TextDB();
        //need to remove supervisor and title in first row of txt file
    	String filename = "rollover project.txt" ;
		try {
			// read file containing Professor records.
			ArrayList projArray = TextDB.readProjects(filename) ;
			for (int i = 0 ; i < projArray.size() ; i++) {
					Project proj = (Project)projArray.get(i);
					System.out.println("Supervisor: " + proj.getName() );
					System.out.println("Title: " + proj.getTitle() +"\n");
			}
			//to add in new prof
			// Professor p1 = new Professor("Joseph","jos@ntu.edu.sg",67909999);
			// al is an array list containing Professor objs
			// al.add(p1);
			// write Professor record/s to file.
			// TextDB.saveProfessors(filename, al);
		}catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
    }
    
    public ArrayList<String> selectProject(){
        //read from project list
        //print projects that have are not taken
        //return project list
        //should i Assign a project ID to each when reading the projects list at initialization
        //students choose projects based off title or project ID
        if(projRegistered)
        {
            System.out.println("Existing project selected, Please deregister before selecting new project");
            break;
        }
        //set request for project selection
        requestProj = true;
        //add request method 
        Request requests = new Request();
        request.add(requests);
        System.out.println("Project Selection Requested. Please wait for approval");
    }
    
    public void deregisterProject(){
        //change request to 0 for deregister request
        //change curproject to nothing
        if(projRegistered)
        {
            System.out.println("No Existing project selected.");
            
        }
        //change request to 0 for deregister request
        //change curproject to nothing
        // this.curProject = null;
        Request requests = new Request();
        request.add(requests);
        System.out.println("Project deregistration Requested. Please wait for approval");
    }

    public String changeTitle(String ProjectTitle){
        //change title change request for that project
        //title request 
        titleChange = true;
        String newtitle = ProjectTitle;
        Request requests = new Request();
        request.add(requests);
        addRequestHistory("Requested to change project Title");
        System.out.println("Title Change requested");
        return newTitle;
    }
    


    public ArrayList<String> viewRequestInfo(){
        return this.requestsHistory;
        
    }
    public void addRequestHistory(String request)
    {
        this.requestsHistory.add(request);
    }
}
