package fypms;

import java.io.*;
import java.util.ArrayList;

/**
 * Class to read project file and write and modify
 * @version 1.0
 */
public class ProjectIO {
	
	/**
	 * This is project file
	 */
    private static final File projectFile = new File("Database/rollover_project.txt");

    /**
     * Default constructor
     */
    public ProjectIO() {}

	/**
	 * Function to read project file
	 * @return Array List of Project
	 * @throws IOException IOException
	 */
    public static ArrayList<Project> readProjects() throws IOException {
        ArrayList<Project> projects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(projectFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(";");
                int projectID = Integer.parseInt(tokens[0]);
                String status = tokens[1];
                String supervisorID = tokens[2];
                String studentID = tokens[3];
                String projectTitle = tokens[4];

                Project project = new Project(projectTitle, supervisorID);
                project.setProjectID(projectID);
                project.setStatus(ProjectStatus.valueOf(status));
                project.setStudentID(studentID);

                projects.add(project);
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }

        return projects;
    }

	/**
	 * write new project to the file
	 * @param project project instance
	 * @throws IOException IOException
	 */
    public static void writeProject(Project project) throws IOException {
        ArrayList<Project> projects = readProjects();
        BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile, true));
        int projectID = projects.size() + 1; // Assign new project ID based on current number of projects

        String line = projectID + ";" + project.getStatus() + ";" + project.getSupervisorID() + ";" + project.getStudentID() + ";" + project.getProjectTitle();

        writer.newLine();
        writer.write(line);
        writer.close();
    }

	/**
	 * modifies a specific line in project file
	 * @param project Project instance
	 * @throws IOException IOException
	 */
    public static void modifyProject(Project project) throws IOException {
        int projectID = project.getProjectID();

        String newLine = project.getProjectID() + ";" + project.getStatus() + ";" + project.getSupervisorID() + ";" + project.getStudentID() + ";" + project.getProjectTitle();

        // Create temporary file
        File tempFile = new File(projectFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(projectFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // Read each line and modify the line corresponding to the project ID
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(String.valueOf(projectID))) {
                writer.write(newLine);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        reader.close();
        writer.close();

        // Replace project file with temporary file
        if (!projectFile.delete()) {
            System.out.println("Failed to delete original file");
            return;
        }

        if (!tempFile.renameTo(projectFile)) {
            System.out.println("Failed to rename temporary file");
            return;
        }
    }


}