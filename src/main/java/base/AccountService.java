package base;

import main.UserProfile;

/**
 * Created by Виталий on 31.03.2015.
 */
public interface AccountService {

    void addSessions(String sessionId, UserProfile userProfile);

    int getUsersSize();

    int getAuthUsersSize();

    UserProfile getUser(String userName);

    UserProfile getSessions(String sessionId);

    boolean isAuthorised(String sessionId);

    void logout(String sessionId);

    boolean isExist(String sessionId);

    boolean addUser(String userName, UserProfile userProfile);

    void saveUserName(String sessionId, String name);

    public String getUserName(String sessionId);
}
