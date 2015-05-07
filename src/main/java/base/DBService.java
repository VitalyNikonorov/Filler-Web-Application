package base;

import base.dataSets.UserDataSet;
import java.util.List;

/**
 * Created by Виталий on 02.05.2015.
 */
public interface DBService {

    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(int id);

    UserDataSet readByEmail(String name);

    List<UserDataSet> readAll();

    List<UserDataSet> getScoreBoard();

    public int getUsersSize();

    public void updateScore(UserDataSet dataSet);

    void shutdown();
}
