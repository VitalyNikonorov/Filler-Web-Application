package base;

import frontend.GameWebSocket;

/**
 * Created by Виталий on 02.04.2015.
 */
public interface WebSocketService {

        void addUser(GameWebSocket user);

        void notifyMyNewScore(GameUser user);

        void notifyNewGameField(GameUser user);

        void notifyEnemyNewScore(GameUser user);

        void notifyStartGame(GameUser user);

        void notifyGameOver(GameUser user, boolean win);

}
