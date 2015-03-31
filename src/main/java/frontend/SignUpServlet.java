package frontend;


import base.AccountService;
import main.UserProfile;
import org.json.JSONObject;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;

/**
 * Created by v.chibrikov on 13.09.2014.
 * Edited by WAATeam
 */
public class SignUpServlet extends HttpServlet {
    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("signUpStatus", "SignUp Page");

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("signupstatus.html", pageVariables));

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

        if (accountService.addUser(jsonRequest.get("name").toString(),
                new UserProfile(jsonRequest.get("name").toString(), jsonRequest.get("password").toString(), jsonRequest.get("email").toString()))) {
            responseMap.put("id", 1);
            responseMap.put("name", jsonRequest.get("name").toString());
            responseMap.put("password", "");
            responseMap.put("email", jsonRequest.get("email").toString());

            jsonResponse.put("body", responseMap);
            jsonResponse.put("status", 200);
        } else {
            jsonResponse.put("status", 400);
            Map<String, Object> nameMap =  new HashMap<>();
            nameMap.put("error", "already exists");
            nameMap.put("value", jsonRequest.get("name").toString());
            responseMap.put("name", nameMap);
            jsonResponse.put("body", responseMap);
        }

        response.getWriter().println(jsonResponse);
    }

}
