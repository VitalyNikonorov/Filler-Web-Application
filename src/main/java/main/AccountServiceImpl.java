package main;

import base.AccountService;
import base.DBService;
import base.dataSets.UserDataSet;
import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements AccountService {

    private DBService dbService;
    private Map<String, UserDataSet> sessions = new HashMap<>();
    private Map<String, String> userSessions = new HashMap<>();

    public void addSessions(String sessionId, UserDataSet userProfile) {
        sessions.put(sessionId, userProfile);
    }

    public int getUsersSize() {
            return dbService.getUsersSize();
    }

    public int getAuthUsersSize() {
        return sessions.size();
    }

    public UserDataSet getUser(String email) {
        return dbService.readByEmail(email);
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

    public AccountServiceImpl(DBService dbService){
        this.dbService = dbService;
    }

    public AccountServiceImpl(){}
}
