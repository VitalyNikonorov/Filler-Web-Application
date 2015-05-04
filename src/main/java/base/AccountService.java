package base;

import base.dataSets.UserDataSet;
import main.UserProfile;

/**
 * Created by Виталий on 31.03.2015.
 */
public interface AccountService {

    void addSessions(String sessionId, UserDataSet userProfile);

    int getUsersSize();

    int getAuthUsersSize();

    UserDataSet getUser(String userName);

    UserDataSet getSessions(String sessionId);

    boolean isAuthorised(String sessionId);

    void logout(String sessionId);

    boolean isExist(String sessionId);

    boolean addUser(String userName, UserDataSet userProfile);

    void saveUserName(String sessionId, String name);

    public String getUserName(String sessionId);
}
