package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * SupervisorView provides the prompt functions that {@link Supervisor} can call
 * @version 1.0
 */
public class SupervisorView {

    /**
     * Default Constructor for SupervisorView
     */
    public SupervisorView() {}

    /**
     * Prompt function to allow {@link Supervisor} or {@link FYPCoordinator} to create {@link Project}
     * See {@link Supervisor} for create project function
     * @param supervisor {@link Supervisor} or {@link FYPCoordinator} instance after login
     * @throws IOException IOException
     */
    public void promptCreateProject(Supervisor supervisor) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter New Project Title: ");
        String projTitle = sc.nextLine();
        if (!projTitle.isEmpty()) {
            if (supervisor instanceof FYPCoordinator c) c.createProject(projTitle);
            else supervisor.createProject(projTitle);
        }
        else System.out.println("Invalid Input.");
    }

    /**
     * Prompt function to allow {@link Supervisor} or {@link FYPCoordinator} to view own {@link Project}
     * See {@link Project} for printing function
     * @param supervisor {@link Supervisor} or {@link FYPCoordinator} instance after login
     * @throws IOException IOException
     */
    public void promptViewOwnProjects(Supervisor supervisor) throws IOException {
        Supervisor s;
        if (supervisor instanceof FYPCoordinator c) s=c;
        else s=supervisor;
        ArrayList<Project> ownProj = s.viewOwnProjects();
        if (ownProj.isEmpty()) {
            System.out.println("You have no projects.");
        }
        else {
            for (Project p : ownProj) {
                p.printProjectInfo();
            }
        }
    }

    /**
     * Prompt function to allow {@link Supervisor} or {@link FYPCoordinator} to update {@link Project} title
     * See {@link Supervisor} for update title function
     * @param supervisor {@link Supervisor} or {@link FYPCoordinator} instance after login
     * @throws IOException IOException
     */
    public void promptUpdateTitle(Supervisor supervisor) throws IOException {
        Scanner sc = new Scanner(System.in);
        Supervisor s;
        if (supervisor instanceof FYPCoordinator c) s=c;
        else s=supervisor;
        ArrayList<Project> ownProj = s.viewOwnProjects();
        if (ownProj.isEmpty()) {
            System.out.println("You have no projects.");
        }
        else {
            System.out.print("Enter Project ID: ");
            try {
                int id = sc.nextInt();
                sc.nextLine();
                int count = 0;
                for (Project p : ownProj) {
                    if (p.getProjectID() == id) {
                        System.out.print("Enter New Project Title: ");
                        String title = sc.nextLine();
                        if (!title.isEmpty()) {
                            s.updateTitle(id, title);
                            count++;
                            break;
                        } else {
                            System.out.println("Invalid input!");
                            count++;
                        }
                    }
                }
                if (count == 0) System.out.println("You are not supervising that project.");
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
            }
        }
    }

    /**
     * Prompt function to allow {@link Supervisor} or {@link FYPCoordinator} to transfer {@link Project}
     * See {@link Supervisor} for transfer function
     * @param supervisor {@link Supervisor} or {@link FYPCoordinator} instance after login
     * @throws IOException IOException
     */
    public void promptTransfer(Supervisor supervisor) throws IOException {
        Scanner sc = new Scanner(System.in);
        Supervisor s;
        if (supervisor instanceof FYPCoordinator c) s=c;
        else s=supervisor;
        ArrayList<Project> ownProj = s.viewOwnProjects();
        ArrayList<Request> allReq = RequestIO.readRequests();
        int cnt = 0;
        for (Request r : allReq) {
            if (r.getSender().equals(s.getSupervisorID()) && r.getType() == RequestType.TRANSFER) {
                 if (r.getStatus() == RequestStatus.PENDING) cnt++;
            }
        }
        if (ownProj.isEmpty()) {
            System.out.println("You have no projects.");
        }
        else if (cnt != 0) {
            System.out.println("Please wait for your request for transfer to be processed first.");
        }
        else {
            ArrayList<Project> allocated_proj = s.viewAllocatedProjects();
            if (!allocated_proj.isEmpty()) {
                System.out.print("Enter Project ID: ");
                try {
                    int id = sc.nextInt();
                    sc.nextLine();
                    int count = 0;
                    for (Project p : allocated_proj) {
                        if (p.getProjectID() == id) {
                            System.out.print("Enter New Project Supervisor: ");
                            String new_supervisor = sc.nextLine();
                            if (!new_supervisor.isEmpty()) {
                                count++;
                                s.transferProject(id, new_supervisor);
                                break;
                            } else System.out.println("Invalid input!");
                        }
                    } if (count == 0) System.out.println("Either you chose a non-existing project, or the project is not available.");
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                    sc.nextLine();
                }
            } else System.out.println("None of your projects are allocated.");
        }
    }

    /**
     * Prompt function to allow {@link Supervisor} to view and approve/reject {@link Request}
     * See {@link RequestIO} for printing function and {@link Supervisor} for modify project title function
     * See {@link FYPCoordinatorView} for the override function
     * @param supervisor {@link Supervisor} instance after login
     * @throws IOException IOException
     */
    public void promptViewRequests(Supervisor supervisor) throws IOException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Request> pendingReqs = supervisor.viewPendingRequests();
        if (pendingReqs.isEmpty()) {
            System.out.println("You currently have no pending requests,");
        }
        else {
            for (Request r : pendingReqs) {
                RequestIO.printRequestInfo(r);
            }
            System.out.print("Select Request ID to Process: ");
            int id = sc.nextInt();
            sc.nextLine();
            int count = 0;
            for (Request r : pendingReqs) {
                if (r.getRequestID() == id) {
                    System.out.println("Approve (1) / Reject (2)");
                    System.out.print("Enter Choice: ");
                    try {
                        int request_choice = sc.nextInt();
                        sc.nextLine();
                        if (request_choice == 1) {
                            supervisor.modifyProjectTitle(r);
                        } else if (request_choice == 2) {
                            if (r instanceof RequestForTitle rt) {
                                rt.reject(rt);
                            }
                        } else { System.out.println("Invalid input!"); }
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                        sc.nextLine();
                    }
                } else count++;
            }
            if (count == pendingReqs.size()) System.out.println("Request does not exist.");
        }
    }

    /**
     * Prompt function to allow {@link Supervisor} or {@link FYPCoordinator} to view processed {@link Request}
     * See {@link RequestIO} for printing function
     * @param supervisor {@link Supervisor} or {@link FYPCoordinator} instance after login
     * @throws IOException IOException
     */
    public void promptViewReqHistory(Supervisor supervisor) throws IOException {
        Supervisor s;
        if (supervisor instanceof FYPCoordinator c) s=c;
        else s=supervisor;
        ArrayList<Request> allReq = s.viewRequests();
        if (allReq.isEmpty()) {
            System.out.println("You have not made any requests.");
        }
        else {
            for (Request r : allReq) {
                RequestIO.printRequestInfo(r);
            }
        }
    }
}
