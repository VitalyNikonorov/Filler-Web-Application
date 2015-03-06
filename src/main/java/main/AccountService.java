package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class AccountService {

    private Map<String, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> sessions = new HashMap<>();

    public AccountService() {
        this.addUser("admin", new UserProfile("admin", "admin", ""));
        this.addUser("test", new UserProfile("test", "test", ""));
    }

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    public int getUsersSize() {
            return users.size();
    }

    public int getAuthUsersSize() {
        return sessions.size();
    }

    public UserProfile getUser(String userName) {
        return users.get(userName);
    }

    public UserProfile getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    public boolean isAuthorised(String sessionId) { return sessions.containsKey(sessionId); };

    public void logout(String sessionId) { sessions.remove( sessionId); };
}
