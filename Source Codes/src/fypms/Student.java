public class Student {
    private String name;
    private String email;
    private String studentID;
    private String curProject;
    private boolean projRegistered;
    private ArrayList<String> requestsHistory;
    private boolean titleChange;


    public Student(String name, String email, String studentID) {
        // Constructor that sets the student's name, email, and studentID
        this.name = name;
        this.email = email;
        this.studentID = studentID;
        this.requestsHistory = new ArrayList<String>();
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

    public ArrayList<String> viewAllProjects(){
        //read from project list
        //print projects 
        //return project list
    }

    public ArrayList<String> selectProjects(){
        //read from project list
        //print projects that have are not taken
        //return project list
    }

    public String changeTitle(String ProjectTitle){
        //change title change request for that project
        //title request 
        titleChange = true;
        String newtitle = ProjectTitle;
        return newtitle;
    }

    public void deregisterProject(){
        //change request to 0 for deregister request
        //change curproject to nothing
        this.curProject = null;
    }

    public ArrayList<String> viewRequestInfo(){
        return this.requestsHistory;
        
    }
    public void addRequestHistory(String request)
    {
        this.requestsHistory.add(request);
    }
}
