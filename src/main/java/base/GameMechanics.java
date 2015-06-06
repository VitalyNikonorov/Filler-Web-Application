package base;

/**
 * Created by Виталий on 02.04.2015.
 */
public interface GameMechanics {

    public Integer[][] getGameFuild();

    public void addUser(String user);

    public void incrementScore(String userName);

    public void move(String userName, int color);

    public void run();

    public void delGame(String userName);
}