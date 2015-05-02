package main;

import admin.AdminPageServlet;
import base.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import chat.WebSocketChatServlet;
import frontend.*;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.json.JSONObject;
import sax.ReadXMLFileSAX;
import xpath.xpathAdapter;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 * Edited by WAATeam
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JSONObject resources = null;
        resources = saxExample();
        //int port = resources.getInt("port");
        int port = new Integer(xpathAdapter.getValue("test1.xml", "/class/port" ));

        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        AccountService accountService = new AccountServiceImpl();
        ContextService contextService = new ContextService();

        contextService.add(accountService.getClass(), accountService);
        contextService.add(gameMechanics.getClass(), gameMechanics);
        contextService.add(webSocketService.getClass(), webSocketService);

        Servlet signIn = new SignInServlet(contextService);
        Servlet signUp = new SignUpServlet(contextService);
        Servlet profile = new ProfileServlet(contextService);
        Servlet logOut = new SignOutServlet(contextService);
        Servlet check = new CheckServlet(contextService);
        Servlet score = new ScoresServlet(contextService);
        Servlet chat = new WebSocketChatServlet();

        //Sockets

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signIn), "/api/v1/auth/signin");
        context.addServlet(new ServletHolder(signUp), "/api/v1/auth/signup");
        context.addServlet(new ServletHolder(check), "/api/v1/auth/check");
        context.addServlet(new ServletHolder(score), "/api/v1/scores");

        context.addServlet(new ServletHolder(profile), "/profile");
        context.addServlet(new ServletHolder(logOut), "/logout");

        //Sockets
        context.addServlet(new ServletHolder(chat), "/chat");

        //for game example

        context.addServlet(new ServletHolder(new WebSocketGameServlet(contextService)), "/gameplay");

        context.addServlet(new ServletHolder(new GameServlet(contextService)), "/game.html");  //ошибка в GameServlet

        context.addServlet(new ServletHolder(new AdminPageServlet(contextService)), AdminPageServlet.adminPageURL);
        //Статика в public
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        gameMechanics.run();
    }


    private static JSONObject saxExample() {
        JSONObject jsonResponse = null;
        SerializationObject object = (SerializationObject) ReadXMLFileSAX.readXML("test.xml");
        if (object != null) {
            jsonResponse = new JSONObject(object);
        }

        return jsonResponse;
    }
}

