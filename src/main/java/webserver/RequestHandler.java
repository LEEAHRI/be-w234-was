
package webserver;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import controller.UserController;
import factory.RequestFactory;
import factory.ResponseFactory;
import model.Request;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResourceUtils;


public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private UserController userController;
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userController = new UserController();
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
        logger.debug("response : {}", response.getStatus());
        if (response == null) {
            logger.error("Invalid Response !");
            response = ResponseFactory.create500ErrorResponse();
        }

        try (OutputStream out = connection.getOutputStream(); DataOutputStream dos = new DataOutputStream(out)) {
            if (response.getStatus().equals("302")) {
                response302Header(dos);
            } else {
                response200Header(dos, response.getContentLength(), response.getContentType());
                if (response.getBody() != null) {
                    responseBody(dos, response.getBody());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type:" + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: /index.html\r\n");
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
        Response response = new Response();
        byte[] body = ResourceUtils.readFile(request.getUrl());
        String extension = ResourceUtils.getExtension(request.getUrl());
        response.setContentType("text/" + extension);
        response.setStatus("200");
        response.setBody(body);
        return response;
    }
}
