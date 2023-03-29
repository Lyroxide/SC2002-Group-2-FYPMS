package projectandreq;

import java.io.*;
import java.util.*;
import java.util.Scanner;

import projectandreq.Request.requestType;
import projectandreq.Project;

import java.util.ArrayList;


public class RequestIO {
  public static final String SEPARATOR = "|";
  
  //private File requestToSupervisorFile = new File("RequestsToSupervisor.txt");
  //private File requestToCoordinatorFile = new File("RequestsToCoordinator.txt");
  
  public void readRequest(String RequestsFile) throws IOException {
    ArrayList SupervisorRequests = (ArrayList)read("requestToSupervisorFile");
    ArrayList requests = new ArrayList();
    // Request(String senderID, requestType type, String receiverID, Project project)
    for(int i = 0;i<SupervisorRequests.size();i++) {
      String st = (String)SupervisorRequests.get(i);
      String[] tokens = st.split("|");
      
      Request request = new Request(tokens[0], tokens[1], requestType.valueOf(tokens[2]), tokens[3]);
      
      //how to create request object
    } 
    
  }
  

  public void writeRequest(List data) throws IOException {
    PrintWriter out = new PrintWriter(new FileWriter("RequestsFile"));
    try {
      for(int i=0;i<data.size();i++) {
        out.println((String)data.get(i));
      }
    }
    finally {
      out.close();
    }
  }
  
  public static List read(String filename) throws IOException{
    List requests = new ArrayList();
    Scanner scanner = new Scanner(new FileInputStream(filename));
    try {
      while(scanner.hasNextLine()) {
        requests.add(scanner.nextLine());
      }
    }
    finally {
      scanner.close();
    }
    return requests;
  }

}
