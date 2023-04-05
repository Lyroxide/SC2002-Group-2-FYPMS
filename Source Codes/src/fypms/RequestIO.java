package fypms;

import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;

public class RequestIO {
	private File requestFile = new File("Database/request_list.txt");
	
	private static ArrayList<Request> requests = new ArrayList<>();
	
	public static ArrayList<Request> readRequests() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(requestFile))) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] tokens = line.split(";");
                int requestID = Integer.parseInt(tokens[0]);
                RequestType type = RequestType.valueOf(tokens[1]);
                String sender = tokens[2];
                String receiver = tokens[3];
                int projectID = Integer.parseInt(tokens[4]);
				RequestStatus status = RequestStatus.valueOf(tokens[5]);
                
                Request request;

				switch(type) {
					case ALLOCATION:
						request = new RequestForAllocation(type, sender, receiver, projectID, status);
						break;
					case DEREGISTRATION:
						request = new RequestForDeregistration(type, sender, receiver, projectID, status);
						break;
					case TITLECHANGE:
						String title = tokens[6];
						request = new RequestForTitleChange(type, sender, receiver, projectID, status, title);
						break;
					case TRANSFER:
						String supervisorID = tokens[6];
						request = new RequestForTransfer(type, sender, receiver, projectID, status, supervisorID);
						break;
					default:
						throw new IllegalArgumentException("Invalid request type: " + type);
				}
				
                request.setRequestID(requestID);
                
                requests.add(request);
            }
        }
        
        return requests;
    }
    
    public static void writeRequest(Request request) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(requestFile, true));
		requests = readRequests();
		int requestID = requests.size() + 1;
		String addToken = "";
		if (request instanceof RequestForTitle) {
			addToken = ((RequestForTitle) request).getProjectTitle();
		}
		else if (request instanceof RequestForTransfer) {
			addToken = ((RequestForTransfer) request).getSupervisorID();
		}
		
		String line = requestID + ";" + request.getType() + ";" + request.getSender() + ";" + request.getReceiver() + ";" + request.getProjectID() + ";" + request.getStatus().name() + ";" + addToken;
		
		writer.newLine();
		writer.write(line);
		writer.close();
	}
	
    
    public static void modifyRequest(Request request) throws IOException {
		int requestID = project.getRequestID();
		String addToken = "";
		if (request instanceof RequestForTitle) {
			addToken = ((RequestForTitle) request).getProjectTitle();
		}
		else if (request instanceof RequestForTransfer) {
			addToken = ((RequestForTransfer) request).getSupervisorID();
		}
		String newLine = requestID + ";" + request.getType() + ";" + request.getSender() + ";" + request.getReceiver() + ";" + request.getProjectID() + ";" + request.getStatus().name() + ";" + addToken;
		
		// Create temporary file
		File tempFile = new File(requestFile.getAbsolutePath() + ".tmp");
		BufferedReader reader = new BufferedReader(new FileReader(requestFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		// Read each line and modify the line corresponding to the project ID
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(String.valueOf(requestID))) {
				writer.write(newLine);
			} else {
				writer.write(line);
			}
			writer.newLine();
		}
		
		reader.close();
		writer.close();
		
		// Replace project file with temporary file
		if (!requestFile.delete()) {
			System.out.println("Failed to delete original file");
			return;
		}
		
		if (!tempFile.renameTo(requestFile)) {
			System.out.println("Failed to rename temporary file");
			return;
		}
	}
		
		
}