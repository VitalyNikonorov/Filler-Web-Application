package main;

import base.AccountService;
import base.dataSets.UserDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class AccountServiceImpl implements AccountService {

    private Map<String, UserDataSet> users = new HashMap<>();
    private Map<String, UserDataSet> sessions = new HashMap<>();
    private Map<String, String> userSessions = new HashMap<>();

    public boolean addUser(String userName, UserDataSet userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(String sessionId, UserDataSet userProfile) {
        sessions.put(sessionId, userProfile);
    }

    public int getUsersSize() {
            return users.size();
    }

    public int getAuthUsersSize() {
        return sessions.size();
    }

    public UserDataSet getUser(String userName) {
        return users.get(userName);
    }

    public UserDataSet getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    public boolean isAuthorised(String sessionId) { return sessions.containsKey(sessionId); }

    public void logout(String sessionId) { sessions.remove( sessionId); }

    public boolean isExist(String sessionId) { return  sessions.containsKey(sessionId);}

    public void saveUserName(String sessionId, String name) {
        userSessions.put(sessionId, name);
    }

    public String getUserName(String sessionId) {
        return userSessions.get(sessionId);
    }
}
