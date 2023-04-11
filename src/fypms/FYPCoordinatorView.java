package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FYPCoordinatorView provides the prompt functions that {@link FYPCoordinator} can call <p></p>
 * Extends {@link SupervisorView}
 * @version 1.0
 */
public class FYPCoordinatorView extends SupervisorView {

    /**
     * Default Constructor for FYPCoordinatorView
     */
    public FYPCoordinatorView() {
        super();
    }

    /**
     * Prompt function to allow {@link FYPCoordinator} to view and approve/reject {@link Request} <p></p>
     * See {@link RequestIO} for printing function and {@link FYPCoordinator} for the respective approve functions
     * @param coordinator {@link FYPCoordinator} instance after login
     * @throws IOException IOException
     */
    public void promptViewRequests(FYPCoordinator coordinator) throws IOException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Request> pendingReqs = coordinator.viewPendingRequests();
        if (pendingReqs.isEmpty()) {
            System.out.println("You currently have no pending requests,");
        }
        else {
            for (Request r : pendingReqs) {
                RequestIO.printRequestInfo(r);
            }
            System.out.print("Select Request ID to Process: ");
            try {
                int id_3 = sc.nextInt();
                sc.nextLine();
                int count = 0;
                for (Request r : pendingReqs) {
                    if (r.getRequestID() == id_3) {
                        System.out.println("(1) Approve | (2) Reject");
                        System.out.print("Enter Choice: ");
                        try {
                            int request_choice = sc.nextInt();
                            if (request_choice == 1) {
                                if (r.getType().equals(RequestType.ALLOCATION))
                                    coordinator.allocateProject(r);
                                else if (r.getType().equals(RequestType.DEREGISTRATION))
                                    coordinator.deregisterStudent(r);
                                else if (r.getType().equals(RequestType.TITLECHANGE))
                                    coordinator.modifyProjectTitle(r);
                                else if (r.getType().equals(RequestType.TRANSFER))
                                    coordinator.changeSupervisor(r);
                            } else if (request_choice == 2) {
                                if (r instanceof RequestForTitle rt) {
                                    rt.reject(rt);
                                } else if (r instanceof RequestForRegistration rr) {
                                    rr.reject(rr);
                                } else if (r instanceof RequestForTransfer rx) {
                                    rx.reject(rx);
                                }

                            } else { System.out.println("Invalid input!"); }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            sc.nextLine();
                        }
                    } else count++;
                }
                if (count == pendingReqs.size()) System.out.println("Request does not exist.");
            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
            }
        }
    }

    /**
     * Prompt function to allow {@link FYPCoordinator} to filter and view {@link Project} <p></p>
     * See {@link FYPCoordinator} for generate report function and {@link Project} for printing function
     * @param coordinator {@link FYPCoordinator} instance after login
     */
    public void promptGenerateReport(FYPCoordinator coordinator) {
        Scanner sc = new Scanner(System.in);
        System.out.println("| Filter by: (1) STATUS | (2) SUPERVISOR | (3) STUDENT | (4) PROJECT |");
        System.out.print("Enter Filter Type: ");
        try {
            int filterType = sc.nextInt();
            sc.nextLine();
            ArrayList<Project> reports = coordinator.generateReport(filterType);
            if (reports.isEmpty()) {
                System.out.println("No project is found.");
            } else if (filterType < 5 && filterType > 0) {
                if (filterType == 3) {
                    int count = 0;
                    for (Project p : reports) {
                        if (p.getStatus() == ProjectStatus.ALLOCATED) {
                            p.printAllocated();
                            count++;
                        }
                    }
                    if (count == 0) System.out.println("No project is found.");
                } else {
                    for (Project p : reports) {
                        p.printProjectInfo();
                    }
                }
            } else {
                System.out.println("Invalid input!");
                sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }
}
