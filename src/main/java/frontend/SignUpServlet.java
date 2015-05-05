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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;

public class SignUpServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();
    private ContextService contextService;
    private DBService dbService = new DBServiceImpl(0);

    public SignUpServlet(ContextService contextService) {
        this.contextService = contextService;
        accountService = (AccountService) contextService.get(accountService.getClass());
        dbService = (DBService) contextService.get(dbService.getClass());
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/  }

        JSONObject jsonRequest = new JSONObject(jb.toString()); //Запрос в JSON
        JSONObject jsonResponse = new JSONObject();
        Map<String, Object> responseMap =  new HashMap<>();

        try {
            dbService.save(new UserDataSet(jsonRequest.get("name").toString(), jsonRequest.get("email").toString(), jsonRequest.get("password").toString()));
            UserDataSet profile = dbService.readByEmail(jsonRequest.get("name").toString());
            responseMap.put("id", profile.getId());
            responseMap.put("name", profile.getName());
            responseMap.put("password", "");
            responseMap.put("email", profile.getEmail());

            jsonResponse.put("body", responseMap);
            jsonResponse.put("status", 200);
            response.getWriter().println(jsonResponse);
        }catch (Exception e){
                jsonResponse.put("status", 400);
                Map<String, Object> nameMap = new HashMap<>();
                nameMap.put("error", "already exists");
                nameMap.put("value", jsonRequest.get("email").toString());
                responseMap.put("name", nameMap);
                jsonResponse.put("body", responseMap);
                response.getWriter().println(jsonResponse);
        }
    }

}
