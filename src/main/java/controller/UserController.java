package controller;

import factory.ResponseFactory;
import factory.ResponseFactory;
import factory.UserFactory;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.ResourceUtils;

import java.util.List;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    private Response createUser(Request request) {
        User user = UserFactory.createUserByBodyParam(request);
        userService.create(user);
        return ResponseFactory.create200OkResponse();
    }

    private Response getUser(Request request) {
        User user = UserFactory.createUserByQueryString(request);
        userService.create(user);
        return ResponseFactory.create200OkResponse();
    }

    private Response serveResources(Request request) {
        Response response = new Response();
        byte[] body = ResourceUtils.readFile(request.getUrl());
        response.setContentType("html");
        response.setBody(body);
        return response;
    }

    public Response routeUserRequest(Request request) {
        if (request.getUrl().equals("/user/create") && request.getMethod().equals("POST")) {
            return createUser(request);
        }

        if (request.getUrl().equals("/user/create") && request.getMethod().equals("GET")) {
            return getUser(request);
        }

        return serveResources(request);
    }
}
