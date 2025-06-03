package org.example.master.servers;

import org.example.master.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class SimpleSMTPServer {
    private static final Logger logger = LoggerFactory.getLogger(SimpleSMTPServer.class);
    private static final int SMTP_PORT = 25;

    private ServerSocket serverSocket;
    private boolean running = true;

    public void run(){
        try {
            serverSocket = new ServerSocket(SMTP_PORT);
            System.out.println("SMTP Server is listening on port " + SMTP_PORT);
            while (running) {
                Socket socket = serverSocket.accept();
                //new SimpleSMTPServerHandler(socket).start();
            }
        } catch (IOException ex) {
            if (running) {
                logger.error("An error occurred while running the SMTP server", ex);
            }
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                logger.error("Failed to close server socket", ex);
            }
        }
        System.out.println("SMTP Server stopped.");
    }
}
