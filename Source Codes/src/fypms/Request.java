package fypms;

import java.io.IOException;

public abstract class Request {

    private RequestType type;
    private RequestStatus status;
    private String sender;
    private String receiver;
    private int requestID;
    private int projectID;

    public Request() {}

    public Request(RequestType type, String sender, String receiver, int projectID, RequestStatus status) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.projectID = projectID;
        this.status = status;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public RequestType getType() {
        return type;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setSender(String user) {
        this.sender = user;
    }

    public String getSender() {
        return sender;
    }

    public void setReceiver(String user) {
        this.receiver = user;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getRequestID() {
        return requestID;
    }


    public abstract void approve(String param) throws IOException;

    public abstract void reject(Request request) throws IOException;
}