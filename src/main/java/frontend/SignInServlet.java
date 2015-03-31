package frontend;

import base.AccountService;
import main.UserProfile;
import org.json.JSONObject;
import templater.PageGenerator;

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
    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("loginStatus", "SignIn Page");

        response.getWriter().println(PageGenerator.getPage("authstatus.html", pageVariables));
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
            UserProfile profile = accountService.getUser(jsonRequest.get("name").toString());
            if (profile != null) {
                if (profile.getPassword().equals(jsonRequest.get("password").toString())) {
                    accountService.addSessions(session.getId(), accountService.getUser(jsonRequest.get("name").toString()));
                    jsonResponse.put("status", 200);
                    responseMap.put("id", 1);
                    responseMap.put("name", profile.getLogin());
                    responseMap.put("password", "");
                    responseMap.put("email", profile.getEmail());

                    jsonResponse.put("body", responseMap);
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
