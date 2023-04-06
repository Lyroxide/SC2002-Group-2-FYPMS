package fypms;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


/**
 * UI for the Main Application
 * 
 * @version 1.0
 */
public class MainApp {
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        UserIO u = new UserIO();
        u.readStudentFile();
        u.readSupervisorFile();
        u.readCoordinatorFile();



        int choice = 0;
        do {
        	  System.out.println("-------------------");
              System.out.println("Welcome to FYPMS!");
              System.out.println("1: Log in");
              System.out.println("2: Quit");
              System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.err.println("Invalid input!");
                sc.nextLine();
            }

            if (choice == 1) {
                sc.nextLine();
                System.out.println("Please enter your User ID and password.");
                System.out.print("User ID: ");
                String userID = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                
                boolean validUser = false;
                boolean validUserAndPass = false;
        		
        		try {
        			UserLogin ul = new UserLogin(userID, password);
                    validUser = ul.verifyUser(u.getStudents(), u.getSupervisors(), u.getFYPCoordinators());
                    if (validUser) {
                        validUserAndPass = ul.verifyUserAndPassword(u.getStudents(), u.getSupervisors(), u.getFYPCoordinators());
                    }

        		} catch(IOException e) {
        			e.printStackTrace();
        		}
                // for supervisor generate report, ask for filter type, return array list to call projects' printinfo
                // Valid login
                if (validUserAndPass) {
                    String userType = ul.getUserType();
                    switch(userType) {
                    // Student Interface
                    case "student":
                        Student student = new Student(userID, ul);
                        int student_choice = 0;
	                    do {
	                    	System.out.println("----------------------------");
	                        System.out.println("Welcome to the Student Menu!");
	                        System.out.println("1: View Available Projects");
	                        System.out.println("2: Select Project");
	                        System.out.println("3: View Current Allocated Project");
	                        System.out.println("4: Request To Change Project Title");
	                        System.out.println("5: Request To Deregister FYP");
                            System.out.println("6: View Request History");
                            System.out.println("7. Change Password");
                            System.out.println("8. Logout");
	                        System.out.print("Enter your choice: ");
	
                            
	                        try {
	                            student_choice = sc.nextInt();
                                sc.nextLine();
	                            switch (student_choice) {
	                                case 1:
                                        ArrayList<Project> proj = new ArrayList<Project>();
	                                    proj = student.viewAvailableProjects();
                                        if (proj != null) {
                                            for (Project p : proj) {
                                                p.printProjectInfo();
                                            }
                                        }
	                                    break;
	                                case 2:
	                                    System.out.print("Enter Project ID: ");
                                        int project_choice = sc.nextInt();
                                        sc.nextLine();
                                        try {
                                            student.selectProject(project_choice);
                                        } catch(IOException e) {
                                            e.printStackTrace();
                                        }
	                                    break;
	                                case 3:
	                                    ArrayList<Project> allProj = new ArrayList<Project>();
                                        allProj = ProjectIO.readProjects();
                                        if (proj != null) {
                                            for (Project p : proj) {
                                                if (p.getStudentID() == student.getStudentID()) {
                                                    p.printAllocated();
                                                    break;
                                                }
                                            }
                                        }
                                        break;
	                                case 4:
	                                	System.out.print("Enter New Title: ");
                                        String newTitle = sc.nextLine();
                                        sc.nextLine();
                                        student.changeProjectTitle(student.getStudentID(), newTitle);
	                                    break;
	                                case 5:
                                        student.deregisterProject();
	                                    break;
                                    case 6:
                                        student.viewRequests(); //incomplete
                                        break;
                                    case 7:
                                        System.out.print("Enter new password: ");
                                        String newPW = sc.nextLine();
                                        sc.nextLine();
                                        boolean changed = student.changePW(newPW);
                                        if (changed) validUserAndPass = false;
                                        break;
	                            }
	                        } catch (Exception e) {
	                            System.err.println("Invalid input!");
	                            sc.nextLine();
	                        }
	                    } while (student_choice != 8);
	                    System.out.println("Returning to main screen...");
                        break;
                    // Member user interface
                    case 2:
                    	int member_choice = 0;
                        MemberFunctions memberFunctions = new MemberFunctions();

                        do {
                        	System.out.println("----------------------------");
                            System.out.println("Welcome to MovieGoer Module!");
                            System.out.println("1. Search / List movies and view movie details");
                            System.out.println("2. Check seat availability and selection of seat/s");
                            System.out.println("3. Book and purchase tickets");
                            System.out.println("4. View your booking history");
                            System.out.println("5. List the top 5 movies ranked by ticket sales OR by overall reviewers’ ratings");
                            System.out.println("6. Quit");  
                            System.out.print("Enter your choice: ");
                            try {
                                member_choice = sc.nextInt();

                                switch (member_choice) {
                                    case 1:
                                    	memberFunctions.ViewMovies(movie_list, movie_list.size());
                                        break;
                                    case 2:
                                    	memberFunctions.CheckSeats(movie_list, cineplex_list);
                                        break;
                                    case 3:
                                    	memberFunctions.BookTickets(login, movie_list, cineplex_list);
                                        break;
                                    case 4:
                                    	memberFunctions.viewBookingHistory(login);
                                        break;
                                    case 5:
                                    	memberFunctions.PopularMovies(movie_list);
                                        break;
                                    case 6:
                                        break;
                                    default:
                                        System.err.println("Invalid input!");
                                }
                            } catch (Exception e) {
                                System.err.println("Invalid input!");
                                sc.nextLine();
                            }
                        } while (member_choice != 6);
                        System.out.println("Returning to main screen...");
                        break;
                    }
                }
                // Invalid login
                else if (validUser == false) {
                    System.out.println("Wrong username entered! Returning to main screen...");
                }
                else if (validUserAndPass == false) {
                    System.out.println("Wrong password entered! Returning to main screen...");
                }
            }

            // Movie goer (guest)
            else if (choice == 2) {
                int menuChoice = 0;
                MovieGoerFunctions mainFunctions = new MovieGoerFunctions();

                do {
                	System.out.println("----------------------------");
                    System.out.println("Welcome to MovieGoer Module!");
                    System.out.println("1. Search / List movies and view movie details");
                    System.out.println("2. Check seat availability and selection of seat/s");
                    System.out.println("3. Book and purchase tickets");
                    System.out.println("4. View your booking history");
                    System.out.println("5. List the top 5 movies ranked by ticket sales OR by overall reviewers’ ratings");
                    System.out.println("6. Quit");
                    System.out.print("Enter your choice: ");
                    try {
                    	menuChoice = sc.nextInt();

                        switch (menuChoice) {
                            case 1:
                                mainFunctions.ViewMovies(movie_list, movie_list.size());
                                break;
                            case 2:
                                mainFunctions.CheckSeats(movie_list, cineplex_list);
                                break;
                            case 3:
                                mainFunctions.BookTickets(movie_list, cineplex_list);
                                break;
                            case 4:
                            	mainFunctions.viewBookingHistory();
                                break;
                            case 5:
                                mainFunctions.PopularMovies(movie_list);
                                break;
                            case 6:
                                break;
                            default:
                                System.err.println("Invalid input!");
                        }
                    } catch (Exception e) {
                        System.err.println("Invalid input!");
                        sc.nextLine();
                    }
                } while (menuChoice != 6);
                System.out.println("Returning to main screen...");
            }

        } while (choice != 3);
        System.out.println("Thank you for using FYPMS!");
        System.exit(0);
    }
    
}