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
import java.util.List;
import java.util.Map;

/**
 * Created by Виталий on 31.03.2015.
 */
public class ScoresServlet extends HttpServlet {
    private AccountService accountService;
    private DBService dbService;


    public ScoresServlet(ContextService contextService) {
        accountService = (AccountService) contextService.get(accountService.getClass());
        dbService = (DBService) contextService.get(DBServiceImpl.class);
    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        JSONObject jsonResponse = new JSONObject();
        Map<String, Object> responseMap =  new HashMap<>();

        List<UserDataSet> dataSets = dbService.getScoreBoard();

        //responseMap.put("users", data);
        jsonResponse.put("status", 200);
        jsonResponse.put("users", dataSets);

        response.getWriter().println(jsonResponse);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        JSONObject jsonResponse = new JSONObject();
        Map<String, Object> responseMap =  new HashMap<>();
        Map<String, Object> sortMap =  new HashMap<>();

        String status = dbService.getLocalStatus();
        System.out.println(status);

        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        //dbService.shutdown();

        jsonResponse.put("status", 200);
        jsonResponse.put("body", responseMap);

        response.getWriter().println(jsonResponse);
    }

}