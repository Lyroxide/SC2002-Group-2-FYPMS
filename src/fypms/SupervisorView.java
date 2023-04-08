package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SupervisorView {


    public SupervisorView() {}

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
                        } else System.out.println("Invalid Input.");
                    }
                }
                if (count == 0) System.out.println("You are not supervising that project.");
            } catch (IOException e) {
                System.err.println("Invalid input");
                sc.nextLine();
            }
        }
    }

    public void promptTransfer(Supervisor supervisor) throws IOException {
        Scanner sc = new Scanner(System.in);
        Supervisor s;
        if (supervisor instanceof FYPCoordinator c) s=c;
        else s=supervisor;
        ArrayList<Project> ownProj = s.viewOwnProjects();
        if (ownProj.isEmpty()) {
            System.out.println("You have no projects.");
        }
        else {
            ArrayList<Project> allocated_proj = s.viewAllocatedProjects();
            if (!allocated_proj.isEmpty()) {
                System.out.print("Enter Project ID: ");
                try {
                    int id = sc.nextInt();
                    sc.nextLine();
                    for (Project p : allocated_proj) {
                        if (p.getProjectID() == id) {
                            System.out.print("Enter New Project Supervisor: ");
                            String new_supervisor = sc.nextLine();
                            if (!new_supervisor.isEmpty()) {
                                s.transferProject(id, new_supervisor);
                                break;
                            } else System.out.println("Invalid Input.");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Invalid input");
                    sc.nextLine();
                }
            } else System.out.println("None of your projects are allocated.");
        }
    }

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
                        if (request_choice == 1) {
                            supervisor.modifyProjectTitle(r);
                        } else if (request_choice == 2) {
                            if (r instanceof RequestForTitle rt) {
                                rt.reject(rt);
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Invalid input");
                        sc.nextLine();
                    }
                } else count++;
            }
            if (count == pendingReqs.size()) System.out.println("Request does not exist.");
        }
    }

    public void promptViewReqHistory(Supervisor supervisor) throws IOException {
        Supervisor s;
        if (supervisor instanceof FYPCoordinator c) s=c;
        else s=supervisor;
        ArrayList<Request> allReq = s.viewRequests();
        if (allReq.isEmpty()) {
            System.out.println("Either you have not made any requests, or none of your requests has been processed.");
        }
        else {
            for (Request r : allReq) {
                RequestIO.printRequestInfo(r);
            }
        }
    }
}
