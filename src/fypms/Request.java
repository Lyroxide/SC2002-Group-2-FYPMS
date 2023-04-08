package fypms;

import java.io.IOException;

/**
 * Abstract Request class that has common attributes
 * @version 1.0
 */
public abstract class Request {

	/**
	 * This is Request Type
	 */
    private RequestType type;
	
	/**
	 * This is Request Status
	 */
    private RequestStatus status;
	
	/**
	 * This is Request sender
	 */
    private String sender;
	
	/**
	 * This is Request receiver
	 */
    private String receiver;
	
	/**
	 * This is request ID
	 */
    private int requestID;
	
	/**
	 * This is project ID
	 */
    private int projectID;

	/**
	 * Default Request Constructor
	 */
    public Request() {}

	/**
	 * Request Constructor
	 * @param type request type
	 * @param sender sender userID
	 * @param receiver receiver userID or FYPCoordinator
	 * @param projectID project ID
	 * @param status Request Status
	 */
    public Request(RequestType type, String sender, String receiver, int projectID, RequestStatus status) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.projectID = projectID;
        this.status = status;
    }

	/**
	 * Gets Request Type
	 * @return type
	 */
    public RequestType getType() {
        return type;
    }

	/**
	 * Sets Request Status
	 * @param status Request status
	 */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

	/**
	 * Gets Request Status
	 * @return status
	 */
    public RequestStatus getStatus() {
        return status;
    }

	/**
	 * Gets Request Sender
	 * @return sender
	 */
    public String getSender() {
        return sender;
    }

	/**
	 * Gets Request Receiver
	 * @return receiver
	 */
    public String getReceiver() {
        return receiver;
    }

	/**
	 * Gets Request Project ID
	 * @return project id
	 */
    public int getProjectID() {
        return projectID;
    }

	/**
	 * Sets request ID
	 * @param requestID request ID
	 */
    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

	/**
	 * Gets Request ID
	 * @return ID
	 */
    public int getRequestID() {
        return requestID;
    }

	/**
	 * abstract approve method to be implemented in subclass
	 * @param param dummy string
	 * @throws IOException IOException
	 */
    public abstract void approve(String param) throws IOException;

	/**
	 * abstract reject method to be implemented in subclass
	 * @param request Request instance
	 * @throws IOException IOException
	 */
    public abstract void reject(Request request) throws IOException;
}