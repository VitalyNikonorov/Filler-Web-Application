package frontend;


import base.AccountService;
import main.AccountServiceImpl;
import main.ContextService;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Виталий on 31.03.2015.
 */
public class CheckServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();
    private ContextService contextService;

    public CheckServlet(ContextService contextService) {
        this.contextService = contextService;
        accountService = (AccountService) contextService.get(accountService.getClass());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        JSONObject jsonResponse = new JSONObject();
        Map<String, Object> responseMap =  new HashMap<>();
        HttpSession session = request.getSession();


        if (accountService.isExist(session.getId())) {
            responseMap.put("name", accountService.getSessions(session.getId()).getName());
            responseMap.put("password", "");
            responseMap.put("email", accountService.getSessions(session.getId()).getEmail());
            responseMap.put("id", accountService.getSessions(session.getId()).getId());
            jsonResponse.put("status", 200);
        }else{
            jsonResponse.put("status", 401);
        }
        jsonResponse.put("body", responseMap);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonResponse);

    }
}
