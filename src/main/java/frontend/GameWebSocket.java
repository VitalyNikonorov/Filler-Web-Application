package frontend;

/**
 * Created by Виталий on 02.04.2015.
 */

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;


@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(GameUser user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            jsonStart.put("pole", gameMechanics.getGameFuild());
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(GameUser user, boolean win) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", win);
            session.getRemote().sendString(jsonStart.toString());
            session.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
            try {
                JSONObject jsonMove = new JSONObject(data);
                String username = myName;
                int color = new Integer(jsonMove.get("color").toString());
                gameMechanics.move(username, color);
                session.getRemote().sendString(jsonMove.toString());
            } catch (Exception e) {
                System.out.print(e);
            }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    public void setMyScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", myName);
        jsonStart.put("score", user.getMyScore());
        try {
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void setNewField(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "move");
        jsonStart.put("name", myName);
        jsonStart.put("score", user.getScore());
        jsonStart.put("field", user.getGameField());
        try {
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void setEnemyScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", user.getEnemyName());
        jsonStart.put("score", user.getEnemyScore());
        try {
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }
}
