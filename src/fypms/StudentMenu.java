package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMenu {
    public static void studentMenu(String userID,String password){
        Scanner sc = new Scanner(System.in);
        Student student = new Student();
        boolean pwChanged = false;
        ArrayList<Student> students = UserIO.readStudents();

        for (Student s : students) {
            if (s.getStudentID().equals(userID))
                student = s;
        }
        int student_choice = 0;
        do {
            System.out.println("----------------------------");
            System.out.println("Welcome to the Student Menu!");
            System.out.println("(1) View Available Projects");
            System.out.println("(2) Select Project");
            System.out.println("(3) View Current Allocated Project");
            System.out.println("(4) Request To Change Project Title");
            System.out.println("(5) Request To Deregister FYP");
            System.out.println("(6) View Outgoing Request History");
            System.out.println("(7) Change Password");
            System.out.println("(8) Logout");
            System.out.print("Enter your choice: ");

            try {
                student_choice = sc.nextInt();
                sc.nextLine();
                StudentView studentView = new StudentView();
                switch (student_choice) {
                    case 1: //view available projects
                        studentView.promptViewAvailableProjects(student);
                        break;
                    case 2: //select project
                        studentView.promptSelectProject(student);
                        break;
                    case 3: //view curr project
                        studentView.promptViewCurrProject(student);
                        break;
                    case 4: //r: change title
                        studentView.promptChangeTitle(student);
                        break;
                    case 5: //r: deregister
                        studentView.promptDeregister(student);
                        break;
                    case 6: //view request history
                        studentView.promptViewRequestHistory(student);
                        break;
                    case 7: //change pw
                        System.out.print("Enter old password: ");
                        String oldPW = sc.nextLine();
                        System.out.print("Enter new password: ");
                        String newPW = sc.nextLine();
                        System.out.print("Re-enter new password: ");
                        String newPW_2 = sc.nextLine();

                        if (!oldPW.equals(password))
                            System.out.println("You have entered wrong password.");
                        else if (!newPW.equals(newPW_2))
                            System.out.println("New password fields do not match.");
                        else if (newPW.equals(password))
                            System.out.println("You cannot re-use the same password!");
                        else if (oldPW.isEmpty() || newPW.isEmpty())
                            System.out.println("You cannot input empty fields.");
                        else {
                            pwChanged = student.changePW(newPW);
                            System.out.println("You have successfully changed password.");
                        }
                        break;
                    case 8: //logout
                        break;
                    default:
                        System.err.println("Invalid input!");
                }
            } catch (IOException e) {
                System.err.println("Invalid input!");
            }
        } while (student_choice != 8 && !pwChanged);
    }
}