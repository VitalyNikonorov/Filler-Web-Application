package admin;

import base.AccountService;
import main.AccountServiceImpl;
import main.ContextService;
import main.TimeHelper;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Виталий on 06.03.2015.
 */
public class AdminPageServlet extends HttpServlet {
    private AccountService accountService;
    private ContextService contextService;

    public AdminPageServlet(ContextService contextService) {
        this.contextService = contextService;
        accountService = (AccountService) contextService.get(accountService.getClass());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String timeString = request.getParameter("shutdown");

        if (timeString != null) {
            int timeMS = Integer.valueOf(timeString);
            System.out.print("Server will be down after: "+ timeMS + " ms");
            TimeHelper.sleep(timeMS);
            System.out.print("\nShutdown");
            System.exit(0);
        }
        String numOfUsers = Integer.toString(accountService.getUsersSize());
        String numOfAuthUsers = Integer.toString(accountService.getAuthUsersSize());

        pageVariables.put("status", "run");
        pageVariables.put("numOfUsers", numOfUsers);
        pageVariables.put("numOfAuthUsers", numOfAuthUsers);
        response.getWriter().println(PageGenerator.getPage("admin.tml", pageVariables));
    }
}
