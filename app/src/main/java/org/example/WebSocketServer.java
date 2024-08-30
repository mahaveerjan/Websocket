package org.example;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/endpoint")
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Client connected");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);
        session.getAsyncRemote().sendText("Echo: " + message);
    }

    public static void main(String[] args) {
        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 8080, "", null, WebSocketServer.class);

        try {
            server.start();
            System.out.println("Server started...");
            Thread.sleep(60000); // Keep server running for 60 seconds
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}

