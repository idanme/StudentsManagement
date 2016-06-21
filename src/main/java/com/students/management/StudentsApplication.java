package com.students.management;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

/**
 * Created by idanm on 18/06/2016.
 */

public class StudentsApplication {
    private static HttpServer server;

    private static void startServer(){
        // Start the tomcat server
        server = null;
        try {
            server = HttpServerFactory.create("http://localhost:8080/");
            server.start();
            System.out.println("Server running");
            System.out.println("Visit: http://localhost:8080/students");
            System.out.println("Hit return to stop...");
        } catch (IOException e) {
            System.err.println("Failed to start the server." + e);
            e.printStackTrace();
        }
    }

    private static void stopServer (){
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }

//    public static void main(String[] args) throws IOException {
//        startServer();
//        System.in.read();
//        stopServer();
//    }
}
