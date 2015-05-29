package main;

import base.AccountService;
import base.DBService;
import base.dataSets.UserDataSet;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements AccountService, Abonent, Runnable {

    private DBService dbService;
    private Map<String, UserDataSet> sessions = new HashMap<>();
    private Map<String, String> userSessions = new HashMap<>();
    private final Address address = new Address();
    private final MessageSystem messageSystem;

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

    public UserDataSet getUserByName(String name) {
        return dbService.readByName(name);
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

    @Override
    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public AccountServiceImpl(DBService dbService, MessageSystem messageSystem){
        this.dbService = dbService;
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().registerAccountService(this);
    }

    @Override
    public void run() {
        while (true){
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(ThreadSettings.SERVICE_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
