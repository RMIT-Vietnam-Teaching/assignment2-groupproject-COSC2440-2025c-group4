
package oop_clean;
/**
 * @author 
 */
public class SessionPresenter {
    private final String sessionId;
    private final String presenterId;

    public SessionPresenter(String sessionId, String presenterId) {
        this.sessionId = sessionId;
        this.presenterId = presenterId;
    }

    public String getSessionId() { return sessionId; }
    public String getPresenterId() { return presenterId; }
}
