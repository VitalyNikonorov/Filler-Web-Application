package frontend;

import base.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import main.AccountServiceImpl;
import main.ContextService;
import mechanics.GameMechanicsImpl;
import templater.PageGenerator;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 */

/*
public class GameServlet extends HttpServlet {

    private GameMechanics gameMechanics;
    private AccountService authService;

    public GameServlet(GameMechanics gameMechanics, AccountService authService) {
        this.gameMechanics = gameMechanics;
        this.authService = authService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        String name = request.getParameter("name");
        String safeName = name == null ? "NoName" : name;
        authService.saveUserName(request.getSession().getId(), name);
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}*/



public class GameServlet extends HttpServlet {
    private AccountService accountService;
    //private WebSocketService webSocketService = new WebSocketServiceImpl();
    private GameMechanics gameMechanics;
    public ContextService contextService;

    public GameServlet(ContextService contextService) {
        this.contextService = contextService;
        this.accountService = (AccountService) contextService.get((new AccountServiceImpl()).getClass());
        this.gameMechanics = (GameMechanics) contextService.get((new GameMechanicsImpl()).getClass());
        //this.webSocketService = (WebSocketService) contextService.get(webSocketService.getClass());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        /*Map<String, Object> pageVariables = new HashMap<>();
        HttpSession session = request.getSession();
        String name = "";
        if (accountService.isExist(session.getId())) {
            name = accountService.getSessions(session.getId()).getLogin();
        }

        String safeName = name == null ? "NoName" : name;
        accountService.addSessions(request.getSession().getId(), accountService.getUser(name));
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);*/

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        HttpSession session = request.getSession();

        String name = "";
        if (accountService.isExist(session.getId())) {
            name = accountService.getSessions(session.getId()).getLogin();
        }

        String safeName = name == null ? "NoName" : name;
        accountService.saveUserName(session.getId(), name);
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

