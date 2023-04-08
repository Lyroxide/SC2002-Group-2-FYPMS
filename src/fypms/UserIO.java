package fypms;

import java.io.*;
import java.util.ArrayList;

/**
 * Class to read user files, update status/password for all users
 * 
 * @version 1.0
 */
public class UserIO {
	
	/**
	 * file name to read all students
	 */
    private static final File studentFile = new File("Database/student_list.txt");
	
	/**
	 * file name to read all supervisors
	 */
    private static final File supervisorFile = new File("Database/faculty_list.txt");
	
	/**
	 * file name to read all coordinators
	 */
    private static final File coordinatorFile = new File("Database/fyp_coordinator.txt");

    /**
     * Default Constructor
     */
    public UserIO() {}

	/**
	 * read students
	 * 
	 * @return array list of students
	 */
    public static ArrayList<Student> readStudents() {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(studentFile))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(";");
                String name = tokens[0];
                String email = tokens[1];
                String userID = tokens[1].split("@")[0];
                String password = tokens[2];
                StudentStatus status = StudentStatus.valueOf(tokens[3]);
                int curProject = Integer.parseInt(tokens[4]);

                Student student = new Student(name, email, userID, password, status, UserType.STUDENT, curProject);
                students.add(student);
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }

        return students;
    }
	
	/**
	 * write new student's password
	 * @param name student name
	 * @param newPassword new password
     * @throws IOException IOException
	 */
    public static void writeStudentPassword(String name, String newPassword) throws IOException {
        ArrayList<Student> students = readStudents();
        Student student = new Student();
        for (Student s : students) {
            if (s.getName().equals(name)) {
                student = s;
                break;
            }
        }

        String newLine = student.getName() + ";" + student.getEmail() + ";" + newPassword + ";" + student.getStatus() + ";" + student.getCurProject();

        // Create temporary file
        File tempFile = new File(studentFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(studentFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // Read each line and modify the line corresponding to the project ID
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(String.valueOf(student.getName()))) {
                writer.write(newLine);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        reader.close();
        writer.close();

        // Replace project file with temporary file
        if (!studentFile.delete()) {
            System.out.println("Failed to delete original file");
            return;
        }

        if (!tempFile.renameTo(studentFile)) {
            System.out.println("Failed to rename temporary file");
            return;
        }
    }

	/**
	 * write new student's status
	 * @param student the specific student that has been modified
     * @throws IOException IOException
	 */
    public static void writeStudentStatus(Student student) throws IOException {

        String newLine = student.getName() + ";" + student.getEmail() + ";" + student.getPassword() + ";" + student.getStatus() + ";" + student.getCurProject();

        // Create temporary file
        File tempFile = new File(studentFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(studentFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // Read each line and modify the line corresponding to the project ID
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(String.valueOf(student.getName()))) {
                writer.write(newLine);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        reader.close();
        writer.close();

        // Replace project file with temporary file
        if (!studentFile.delete()) {
            System.out.println("Failed to delete original file");
            return;
        }

        if (!tempFile.renameTo(studentFile)) {
            System.out.println("Failed to rename temporary file");
            return;
        }
    }

	/**
	 * read supervisors
     * @return Array List of Supervisors
	 */
    public static ArrayList<Supervisor> readSupervisors() {
        ArrayList<Supervisor> supervisors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(supervisorFile))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(";");
                String name = tokens[0];
                String email = tokens[1];
                String userID = tokens[1].split("@")[0];
                String password = tokens[2];

                Supervisor supervisor = new Supervisor(name, email, userID, password, UserType.SUPERVISOR);
                supervisors.add(supervisor);
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }

        return supervisors;
    }

	/**
	 * write new supervisor's password
	 * @param name name
	 * @param newPassword new password
     * @throws IOException IOException
	 */
    public static void writeSupervisorPassword(String name, String newPassword) throws IOException {
        ArrayList<Supervisor> supervisors = readSupervisors();
        Supervisor supervisor = new Supervisor();
        for (Supervisor s : supervisors) {
            if (s.getName().equals(name)) {
                supervisor = s;
                break;
            }
        }

        String newLine = supervisor.getName() + ";" + supervisor.getEmail() + ";" + newPassword;

        // Create temporary file
        File tempFile = new File(supervisorFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(supervisorFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // Read each line and modify the line corresponding to the project ID
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(String.valueOf(supervisor.getName()))) {
                writer.write(newLine);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        reader.close();
        writer.close();

        // Replace project file with temporary file
        if (!supervisorFile.delete()) {
            System.out.println("Failed to delete original file");
            return;
        }

        if (!tempFile.renameTo(supervisorFile)) {
            System.out.println("Failed to rename temporary file");
            return;
        }
    }

	/**
	 * read fyp coordinators
     * @return Array List of FYPCoordinators
	 */
    public static ArrayList<FYPCoordinator> readFYPCoordinator() {
        ArrayList<FYPCoordinator> coordinators = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(coordinatorFile))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(";");
                String name = tokens[0];
                String email = tokens[1];
                String userID = tokens[1].split("@")[0];
                String password = tokens[2];

                FYPCoordinator coordinator = new FYPCoordinator(name, email, userID, password, UserType.FYPCOORDINATOR);
                coordinators.add(coordinator);
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }

        return coordinators;
    }

	/**
	 * write coordinator password
	 * @param name name
	 * @param newPassword new password
     * @throws IOException IOException
	 */
    public static void writeCoordinatorPassword(String name, String newPassword) throws IOException {
        ArrayList<FYPCoordinator> coordinators = readFYPCoordinator();
        FYPCoordinator coordinator = new FYPCoordinator();
        for (FYPCoordinator f : coordinators) {
            if (f.getName().equals(name)) {
                coordinator = f;
                break;
            }
        }

        String newLine = coordinator.getName() + ";" + coordinator.getEmail() + ";" + newPassword;

        // Create temporary file
        File tempFile = new File(coordinatorFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(coordinatorFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // Read each line and modify the line corresponding to the project ID
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(String.valueOf(coordinator.getName()))) {
                writer.write(newLine);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        reader.close();
        writer.close();

        // Replace project file with temporary file
        if (!coordinatorFile.delete()) {
            System.out.println("Failed to delete original file");
            return;
        }

        if (!tempFile.renameTo(coordinatorFile)) {
            System.out.println("Failed to rename temporary file");
            return;
        }
    }

}