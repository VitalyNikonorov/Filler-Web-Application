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
 * Created by Виталий on 06.03.2015.
 */
public class SignOutServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();
    private ContextService contextService;

    public SignOutServlet(ContextService contextService) {
        this.contextService = contextService;
        accountService = (AccountService) contextService.get(accountService.getClass());
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        JSONObject jsonResponse = new JSONObject();
        Map<String, Object> responseMap =  new HashMap<>();

        if (accountService.isExist(session.getId())) {
            jsonResponse.put("status", 200);
        }else{
            jsonResponse.put("status", 401);
        }
        jsonResponse.put("body", responseMap);

        accountService.logout(session.getId());
        response.getWriter().println(jsonResponse);
    }

}