
package oop_clean;
/**
 * @author
 */
public class SessionMaterial {
    private final String materialId; 
    private final String sessionId;
    private final String presenterId;

    public SessionMaterial(String materialId, String sessionId, String presenterId) {
        this.materialId = materialId;
        this.sessionId = sessionId;
        this.presenterId = presenterId;
    }

    public String getMaterialId() { return materialId; }
    public String getSessionId() { return sessionId; }
    public String getPresenterId() { return presenterId; }
}
