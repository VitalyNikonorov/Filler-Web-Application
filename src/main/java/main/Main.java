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

import javax.servlet.Servlet;

/**
 * @author v.chibrikov
 * Edited by WAATeam
 */
public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        AccountService accountService = new AccountServiceImpl();

        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);
        Servlet profile = new ProfileServlet(accountService);
        Servlet logOut = new SignOutServlet(accountService);
        Servlet check = new CheckServlet(accountService);
        Servlet score = new ScoresServlet(accountService);
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
        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanics, webSocketService)), "/gameplay");
        context.addServlet(new ServletHolder(new GameServlet(gameMechanics, accountService)), "/game.html");


        context.addServlet(new ServletHolder(new AdminPageServlet(accountService)), AdminPageServlet.adminPageURL);
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
}