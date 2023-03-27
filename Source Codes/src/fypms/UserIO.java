package fypms;

import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;

public class UserIO {

	private File studentFile = new File("Database/student_list.txt");
	private File supervisorFile = new File("Database/faculty_list.txt");
	private File coordinatorFile = new File("Database/fyp_coordinator.txt");
	
	private static ArrayList<Student> students = new ArrayList<>;
	private static ArrayList<Supervisor> supervisors = new ArrayList<>;
	private static ArrayList<FYPCoordinator> coordinators = new ArrayList<>;
	
	private String userID;
	private String name;
	private String email;
	private String password;
	private static int linecounter = 0;
	
	public UserIO() {};
	
	public void countStudent() throws IOException {
		FileReader fr = new FileReader("Database/student_list.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String s;
		while((s=br.readLine()) != null) {
			linecounter++;
		}
		br.close();
	}
	
	public void readStudentFile() throws IOException {
		countStudent();
		
		FileReader fr = new FileReader("Database/student_list.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String s;
		int i = 1;
		while(i <= linecounter) {
			s = br.readLine();
			if (s != null) {
				String[] var = s.split(";");
				this.name = var[0];
				this.email = var[1];
				this.userID = this.email.split("@")[0];
				this.password = var[2];
				addStudent();
			}
			i++;
		}
	}
	
	public void writeStudentPassword(String userID, String newPassword) throws IOException, Exception {
		try {
			countStudent();
			
			BufferedReader reader = new BufferedReader(new FileReader(studentFile));
			ArrayList<String> lines = new ArrayList<String>();
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();


			String domain = "@e.ntu.edu.sg";
			
			while(i <= linecounter) {
				if (lines.get(i).contains(userID + domain)) {
					String[] parts = lines.get(i).split(";");
					parts[2] = newPassword; // Update the password in the line
					lines.set(i, String.join(";", parts)); // Join the parts back into a line and update the ArrayList
				}
				i++;
			}

			FileWriter writer = new FileWriter(studentFile); // Overwrite the file
			for (String str : lines) {
				writer.write(str + "\n"); // Write the updated lines to the file
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addStudent() {
		Student st = new Student();
		
		st.setName(name);
		st.setEmail(email);
		st.setUserID(userID);
		
		students.add(st);
	}
	
	public void countSupervisor() throws IOException {
		FileReader fr = new FileReader("Database/faculty_list.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String s;
		while((s=br.readLine()) != null) {
			linecounter++;
		}
		br.close();
	}
	
	public void readSupervisorFile() throws IOException {
		countSupervsior();
		
		FileReader fr = new FileReader("Database/faculty_list.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String s;
		int i = 1;
		while(i <= linecounter) {
			s = br.readLine();
			if (s != null) {
				String[] var = s.split(";");
				this.name = var[0];
				this.email = var[1];
				this.userID = this.email.split("@")[0];
				this.password = var[2];
				addSupervisor();
			}
			i++;
		}
	}
	
	public void writeSupervisorPassword(String userID, String newPassword) throws IOException, Exception {
		try {
			countSupervisor();
			
			BufferedReader reader = new BufferedReader(new FileReader(supervisorFile));
			ArrayList<String> lines = new ArrayList<String>();
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();


			String domain = "@ntu.edu.sg";
			
			while(i <= linecounter) {
				if (lines.get(i).contains(userID + domain)) {
					String[] parts = lines.get(i).split(";");
					parts[2] = newPassword; // Update the password in the line
					lines.set(i, String.join(";", parts)); // Join the parts back into a line and update the ArrayList
				}
				i++;
			}

			FileWriter writer = new FileWriter(supervisorFile); // Overwrite the file
			for (String str : lines) {
				writer.write(str + "\n"); // Write the updated lines to the file
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSupervisor() {
		Supervisor st = new Supervisor();
		
		st.setName(name);
		st.setEmail(email);
		st.setUserID(userID);
		
		supervisors.add(st);
	}
	
	public void countCoordinator() throws IOException {
		FileReader fr = new FileReader("Database/fyo_coordinator.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String s;
		while((s=br.readLine()) != null) {
			linecounter++;
		}
		br.close();
	}
	
	public void readCoordinatorFile() throws IOException {
		countCoordinator();
		
		FileReader fr = new FileReader("Database/fyp_coordinator.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String s;
		int i = 1;
		while(i <= linecounter) {
			s = br.readLine();
			if (s != null) {
				String[] var = s.split(";");
				this.name = var[0];
				this.email = var[1];
				this.userID = this.email.split("@")[0];
				this.password = var[2];
				addCoordinator();
			}
			i++;
		}
	}
	
	public void writeSupervisorPassword(String userID, String newPassword) throws IOException, Exception {
		try {
			countSupervisor();
			
			BufferedReader reader = new BufferedReader(new FileReader(coordinatorFile));
			ArrayList<String> lines = new ArrayList<String>();
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();


			String domain = "@ntu.edu.sg";
			
			while(i <= linecounter) {
				if (lines.get(i).contains(userID + domain)) {
					String[] parts = lines.get(i).split(";");
					parts[2] = newPassword; // Update the password in the line
					lines.set(i, String.join(";", parts)); // Join the parts back into a line and update the ArrayList
				}
				i++;
			}

			FileWriter writer = new FileWriter(coordinatorFile); // Overwrite the file
			for (String str : lines) {
				writer.write(str + "\n"); // Write the updated lines to the file
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCoordinator() {
		FYPCoordinator fy = new FYPCoordinator();
		
		fy.setName(name);
		fy.setEmail(email);
		fy.setUserID(userID);
		
		supervisors.add(fy);
	}
	
}