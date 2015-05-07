package frontend;

import base.AccountService;
import base.DBService;
import base.dataSets.UserDataSet;
import dbService.DBServiceImpl;
import main.AccountServiceImpl;
import main.ContextService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 * Edited by WAATeam
 */
public class SignInServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();
    private ContextService contextService;
    private DBService dbService;

    public SignInServlet(ContextService contextService) {
        this.contextService = contextService;
        accountService = (AccountService) contextService.get(accountService.getClass());
        dbService = (DBService) contextService.get(DBServiceImpl.class);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonRequest = new JSONObject(jb.toString()); //Запрос в JSON
        JSONObject jsonResponse = new JSONObject();
        Map<String, Object> responseMap =  new HashMap<>();
        HttpSession session = request.getSession();

        if( jsonRequest.has("name")) {
            UserDataSet profile = dbService.readByEmail(jsonRequest.get("name").toString());
            if (profile != null) {
                if (profile.getPassword().equals(jsonRequest.get("password").toString())) {
                    jsonResponse.put("status", 200);
                    responseMap.put("id", 1);
                    responseMap.put("name", profile.getName());
                    responseMap.put("password", "");
                    responseMap.put("email", profile.getEmail());
                    jsonResponse.put("body", responseMap);

                    session.setAttribute("User", profile);
                    accountService.addSessions(session.getId(), profile);
                } else {
                    jsonResponse.put("status", 400);
                    Map<String, Object> passMap = new HashMap<>();
                    passMap.put("error", "badvalue");
                    passMap.put("value", "");
                    responseMap.put("password", passMap);
                    jsonResponse.put("body", responseMap);

                }
            } else {
                jsonResponse.put("status", 400);
                Map<String, Object> nameMap = new HashMap<>();
                nameMap.put("error", "doesn't exist");
                nameMap.put("value", "");
                responseMap.put("name", nameMap);
                jsonResponse.put("body", responseMap);
            }
        }else{
            jsonResponse.put("status", 400);
            Map<String, Object> nameMap = new HashMap<>();
            nameMap.put("error", "required");
            nameMap.put("value", "");
            responseMap.put("name", nameMap);
            jsonResponse.put("body", responseMap);
        }



        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(jsonResponse);
    }
}
