package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import controller.UserController;
import factory.RequestFactory;
import factory.ResponseFactory;
import model.Request;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private UserController userController;
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        Request request = RequestFactory.createRequest(connection);
        if (request == null) {
            logger.error("Invalid Request !");
            return;
        }

        Response response = route(request);
        if (response == null) {
            response = ResponseFactory.create500ErrorResponse();
        }
        try (OutputStream out = connection.getOutputStream(); DataOutputStream dos = new DataOutputStream(out);) {
            response200Header(dos, response.getBody().length);
            responseBody(dos, response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Response route(Request request) {
        if (request.getUrl().startsWith("/user")) {
            return userController.routeUserRequest(request);
        }
        return serveResources(request);
    }

    private Response serveResources(Request request) {
        // resource
        try {
            Response response = new Response();
            byte[] body = Files.readAllBytes(new File("./webapp" + request.getUrl()).toPath());
            response.setBody(body);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}