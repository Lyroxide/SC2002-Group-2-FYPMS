package fypms;

public class Request {
  private enum requestType{
    deregister, allocate, changeTitle, transferStudent
  }
  private requestType type;
  private User sender;
  private User receiver;
  
  public void setType(requestType type) {
    this.type = type;
  }
  
  public void setSender(User user) {
    this.sender = user;
  }
  
  public void setReceiver(User user) {
    this.receiver = user;
  }
  
  public void Approve(requestType type) {
    if(this.type == requestType.allocate) // previously was if(type == type.allocate), I think its supp to be like this instead?? - hamka
    {
      FYPCoordinator.allocateProject(Student student, Project project)
    }
    
    if(this.type == requestType.deregister)  
    {
      FYPCoordinator.deregisterProject(Student student, Project project)
    }
    
    if(this.type == requestType.changeTitle) 
    {
      Supervisor.modifyTitle(Project project, String newTitle );
    }
    
    if(this.type == requestType.transferStudent)
    {
      FYPC.changeSupervisor(Project project, String supervisorID);
    }
  }
  
  public void Reject() {
    System.out.println("Request was rejected");
  }
};