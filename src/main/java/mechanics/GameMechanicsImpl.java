package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import main.TimeHelper;

import java.util.*;

/**
 * @author v.chibrikov
 */
public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private static final int gameTime = 3000 * 1000;

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

    private Integer[][] gameField = new Integer[20][15];

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }
    public GameMechanicsImpl() { }

    public void addUser(String user) {
        if (waiter != null) {
            starGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();
        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();
        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
    }

    public void move(String userName, int color) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        myUser.move(color);
        //GameUser enemyUser = myGameSession.getEnemy(userName);
        //enemyUser.incrementEnemyScore();

        webSocketService.notifyNewGameField(myUser);
        //webSocketService.notifyMyNewScore(myUser);
        //webSocketService.notifyEnemyNewScore(enemyUser);
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > gameTime) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
            }
        }
    }

    private void starGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        gameField = generateField(20, 15);

        gameSession.getFirst().setGameField(gameField);
        gameSession.getFirst().setCells();
        gameSession.getSecond().setGameField(gameField);
        gameSession.getSecond().setCells();

        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }

    public Integer[][] generateField(int x, int y) {
        Date now = new Date();
        Integer[][] field = new Integer[x][y];

        Random random = new Random(now.getTime());
        for(int j = 0; j < x; j++){
            for(int i = 0; i < y; i++){
                field[j][i] = (1 + random.nextInt(5));
            }
        }
        return field;
    }

    public Integer[][] getGameFuild(){
        return gameField;
    }
}
