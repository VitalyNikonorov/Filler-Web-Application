package frontend;

import base.AccountService;
import base.GameMechanics;
import templater.PageGenerator;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 */
public class GameServlet extends HttpServlet {

    private GameMechanics gameMechanics;
    private AccountService authService;

    public GameServlet(GameMechanics gameMechanics, AccountService authService) {
        this.gameMechanics = gameMechanics;
        this.authService = authService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String name = request.getParameter("name");

        String safeName = name == null ? "NoName" : name;
        authService.addSessions(request.getSession().getId(), authService.getUser(name));
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        String name = request.getParameter("name");

        String safeName = name == null ? "NoName" : name;
        authService.addSessions(request.getSession().getId(), authService.getUser(name));
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
