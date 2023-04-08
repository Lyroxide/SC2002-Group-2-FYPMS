package fypms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FYPCoordinatorView extends SupervisorView {

    public FYPCoordinatorView() {
        super();
    }

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

                            }
                        } catch (Exception e) {
                            System.err.println("Invalid input!");
                            sc.nextLine();
                        }
                    } else count++;
                }
                if (count == pendingReqs.size()) System.out.println("Request does not exist.");
            } catch (Exception e) {
                System.err.println("Invalid input!");
                sc.nextLine();
            }
        }
    }

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
            }
            else {
                for (Project p : reports) {
                    p.printProjectInfo();
                }
            }
        } catch (Exception e) {
            System.err.println("Invalid input!");
            sc.nextLine();
        }
    }
}
