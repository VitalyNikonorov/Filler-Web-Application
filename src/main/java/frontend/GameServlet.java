package frontend;

import base.AccountService;
import base.GameMechanics;
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
public class GameServlet extends HttpServlet {

    private GameMechanics gameMechanics;
    private AccountService accountService;

    public GameServlet(GameMechanics gameMechanics, AccountService accountService) {
        this.gameMechanics = gameMechanics;
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
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
        response.setStatus(HttpServletResponse.SC_OK);

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
        accountService.addSessions(request.getSession().getId(), accountService.getUser(name));
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
