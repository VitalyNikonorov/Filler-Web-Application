package frontend;

/**
 * Created by Виталий on 02.04.2015.
 */

import main.AccountServiceImpl;
import main.ContextService;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

import base.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/gameplay"})
public class WebSocketGameServlet extends WebSocketServlet {
   private final static int IDLE_TIME = 60 * 1000;
    private AccountService accountService = new AccountServiceImpl();
    private WebSocketService webSocketService = new WebSocketServiceImpl();
    private GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
    private ContextService contextService;

    public WebSocketGameServlet(ContextService contextService) {
        this.contextService = contextService;
        this.accountService = (AccountService) contextService.get(accountService.getClass());
        this.gameMechanics = (GameMechanics) contextService.get(gameMechanics.getClass());
        this.webSocketService = (WebSocketService) contextService.get(webSocketService.getClass());
    }
/**/

    public WebSocketGameServlet(AccountService accountService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new GameWebSocketCreator(accountService, gameMechanics, webSocketService));
    }
}
