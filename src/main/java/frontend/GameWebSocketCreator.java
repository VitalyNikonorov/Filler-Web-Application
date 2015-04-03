package frontend;

import base.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import javax.servlet.http.HttpSession;

/**
 * Created by Виталий on 02.04.2015.
 */
public class GameWebSocketCreator implements WebSocketCreator {


    private AccountService accountService;
    public GameWebSocketCreator(AccountService accountService) {
        this.accountService = accountService;
    }
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocketCreator(AccountService accountService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = accountService.getSessions(req.getHttpServletRequest().getSession().getId()).getLogin();;
        return new GameWebSocket(name, gameMechanics, webSocketService);
    }
}
