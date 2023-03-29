package projectandreq;

import projectandreq.Project.Status;

/* Request represents a request object being sent from one user to another
 */

public class Request {
  public enum requestType{
    deregister, allocate, changeTitle, transferStudent
  }
  private requestType type;
  private User sender;
  private User receiver;
  private Project project;
  
  /* Constructor that sets the request's senderID, receiverID, and requestType
   * @param senderID ID of requester
   * @param type request type
   * @param receiverID ID of receiver
   */
  public Request(String senderID, String receiverID, requestType type, Project project) {
    this.type = type;
    this.sender.setName(senderID);
    this.receiver.setName(receiverID);
    this.project = project;
    //RequestIO.readRequest()
  }

  public void Approve(requestType type, Project project) {
    if(type == type.allocate)
    {
      
      project.projectStatus = Status.allocated;
      //set student ID 
    
    }
    
    if(type == type.deregister)
    {
      
      project.projectStatus = Status.available;
      project.studentID = null;
    }
    
    if(type == type.changeTitle) 
    {
      
      project.setProjectTitle();
    }
    
    if(type == type.transferStudent)
    {
      FYPC.changeSupervisor(Project project, String supervisorID);
    }
  }
  
  public void Reject() {
    System.out.println("Request was rejected");
  }
  
  
}
