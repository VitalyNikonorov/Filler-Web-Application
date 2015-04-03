package base;

/**
 * Created by Виталий on 02.04.2015.
 */
public interface GameMechanics {

    public void addUser(String user);

    public void incrementScore(String userName);

    public void run();
}