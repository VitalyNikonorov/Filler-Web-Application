package mechanics;

import base.GameUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 */
public class GameSession {
    private final long startTime;
    private final GameUser first;
    private final GameUser second;

    private GameUser turn;

    public void setTurn(GameUser turn) {
        this.turn = turn;
    }

    public GameUser getTurn() {
        return turn;
    }


    private boolean overStatus = false;

    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(String user1, String user2) {
        startTime = new Date().getTime();
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setEnemyName(user2);


        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setEnemyName(user1);

        gameUser1.setEnemy(gameUser2);
        gameUser2.setEnemy(gameUser1);

        gameUser1.setPlayer(1);
        gameUser2.setPlayer(2);

        users.put(user1, gameUser1);
        users.put(user2, gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;
    }

    public GameUser getEnemy(String user) {
        String enemyName = users.get(user).getEnemyName();
        return users.get(enemyName);
    }

    public GameUser getSelf(String user) {
        return users.get(user);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public GameUser getFirst() {
        return first;
    }

    public GameUser getSecond() {
        return second;
    }

    public  boolean isFirstWin(){
        return first.getMyScore() > second.getMyScore();
    }

    public  void changeOverStatus(){
        this.overStatus = true;
    }

    public boolean getOverStatus(){ return overStatus; };

}
