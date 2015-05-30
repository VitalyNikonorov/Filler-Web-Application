package frontend;

import base.DBService;
import base.dataSets.UserDataSet;
import main.AccountServiceImpl;
import main.ContextService;
import messageSystem.MessageSystem;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CheckServletTest {
    static DBService dbService = mock(DBService.class);
    static MessageSystem MS = new MessageSystem();
    private final static AccountServiceImpl aS = new AccountServiceImpl(dbService, MS );
    private final static ContextService cS = new ContextService();

    private HttpServletResponse getMockedResponse(StringWriter stringWriter) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        final PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        return response;
    }

    private HttpServletRequest getMockedRequest(String url) {
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getPathInfo()).thenReturn(url);
        return request;
    }


    @Test
    public void ckeckTest1() throws Exception {
        cS.add(AccountServiceImpl.class, aS);
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest("/api/v1/auth/check");
        when(request.getParameter("name")).thenReturn("");
        CheckServlet check = new CheckServlet(cS);
        check.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("401"));
    }

    @Test
    public void ckeckTest2() throws Exception {
        HttpServletRequest request = getMockedRequest("/api/v1/auth/check");

        UserDataSet user = new UserDataSet(1, "Unit", "11@11", "000", 0);
        aS.addSessions(request.getSession().getId(), user);
        cS.add(AccountServiceImpl.class, aS);

        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);

        when(request.getParameter("name")).thenReturn("");

        CheckServlet check = new CheckServlet(cS);

        check.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("200"));
    }
}