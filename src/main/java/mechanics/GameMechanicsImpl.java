package mechanics;

import base.*;
import base.dataSets.UserDataSet;
import main.MsgUpScore;
import main.ThreadSettings;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import xpath.xpathAdapter;

import java.util.*;

public class GameMechanicsImpl implements GameMechanics, Abonent, Runnable {
    private static final int HEIGHT = 19;
    private static final int WIDTH = 15;

    private final Address address = new Address();
    private final MessageSystem messageSystem;

    private static final int gameTime = new Integer(xpathAdapter.getValue("resources/game.xml", "/class/matchTime")) * 1000;

    private DBService dbService;

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

    private Integer[][] gameField = new Integer[HEIGHT][WIDTH];

    public GameMechanicsImpl(WebSocketService webSocketService, DBService dbService, MessageSystem messageSystem) {
        this.dbService = dbService;
        this.webSocketService = webSocketService;
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().registerGameMechanics(this);
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }

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
        GameUser enemyUser = myGameSession.getEnemy(userName);
        if ((myUser.getColor() != color) && (enemyUser.getColor() != color) ) {
            myUser.move(color);
            myUser.setColor(color);
            webSocketService.notifyNewGameField(myUser);
        }else{
            myUser.setScore(-100);
            myGameSession.changeOverStatus();
        }
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(ThreadSettings.SERVICE_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if ( (session.getSessionTime() > 35000)//gameTime)
                    || (session.getFirst().getMyScore() + session.getFirst().getEnemyScore()) >= (HEIGHT*WIDTH)
                    || (session.getFirst().getMyScore() + session.getSecond().getMyScore()) >= (HEIGHT*WIDTH)
                    || session.getOverStatus() ) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);

                Message upScore = new MsgUpScore(getAddress(), messageSystem.getAddressService().getAccountServiceAddress(), session);
                messageSystem.sendMessage(upScore);

                //Message messageGetUser2 = new MsgUpScore(getAddress(), messageSystem.getAddressService().getAccountServiceAddress(), session.getSecond().getMyName(), session.getFirst().getMyScore());
                //messageSystem.sendMessage(messageGetUser2);
                //allSessions.remove(session);
            }
        }
    }

    public void upScore(UserDataSet user){

        dbService.updateScore(user);

    }

    public void removeGame(GameSession session){
        System.out.println("Remove game " + session.getFirst().getMyName() + " - " + session.getSecond().getMyName());
        nameToGame.remove(session.getFirst());
        nameToGame.remove(session.getSecond());
        allSessions.remove(session);
    }

    private void starGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        gameField = generateField(HEIGHT, WIDTH);

        gameSession.getFirst().setGameField(gameField);
        gameSession.getFirst().setCells();

        gameSession.getSecond().setGameField(gameField);
        gameSession.getSecond().setCells();

        gameSession.getFirst().setColor(gameField[gameField.length - 1][0]);
        gameSession.getSecond().setColor(gameField[0][gameField[0].length - 1]);

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
