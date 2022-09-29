package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            Request request;
            request = RequestFactory.createRequest(br);

            if (request == null) {
                logger.error("Invalid Request !");
                return;

            }
            Response response = route(request);
            logger.debug("response : {}", response.getStatus());
            if (response == null) {
                logger.error("Invalid Response !");
            } else {
                writeResponse(response, dos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void writeResponse(Response response, DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 " + response.getStatus() + "\r\n");
            if (response.getStatus() != null && response.getStatus().equals("302 FOUND")) {
                dos.writeBytes("Location: " + response.getLocation() + "\r\n");
            }
            if (response.getContentType() != null) {
                dos.writeBytes("Content-Type: " + response.getContentType() + ";charset=utf-8\r\n");
            }
            dos.writeBytes("Content-Length: " + response.getContentLength() + "\r\n");
            dos.writeBytes("\r\n");
            if (response.getBody() != null) {
                byte[] body = response.getBody();
                dos.write(body, 0, body.length);
            }
            if (response.getCookies() != null) {
                Map<String, String> cookies = response.getCookies();
                for (String key : response.getCookies().keySet()) {
                    dos.writeBytes("Set-Cookie: " + key + "=" + cookies.get(key) + "; Path=/\r\n");
                }
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Response route(Request request) {
        if (request.getUrl().startsWith("/user")) {
            return userController.routeUserRequest(request);
        } else if (request.getUrl().equals("/user/login")) {
            return userController.routeUserRequest(request);
        }
        return serveResources(request);
    }

    public Response serveResources(Request request) {
        Response response = new Response();
        byte[] body = ResourceUtils.readFile(request.getUrl());
        String extension = ResourceUtils.getExtension(request.getUrl());
        response.setContentType("text/" + extension);
        response.setStatus("200 OK");
        response.setBody(body);
        return response;
    }
}
