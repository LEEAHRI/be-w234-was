package controller;
import exception.LoginFailException;
import factory.ResponseFactory;
import factory.UserFactory;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.ResourceUtils;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    private Response postUser(Request request) {
        User user = UserFactory.createUserByBodyParam(request);
        userService.create(user);
        Response response = ResponseFactory.createResponse("302");
        response.setLocation("http://localhost:8080/index.html");
        return response;
    }
    private Response postLogin(Request request) {
        User user = UserFactory.createUserByBodyParam(request);
        try {
            userService.login(user);
        } catch (LoginFailException e) {
            Response response = ResponseFactory.createResponse("302");
            response.setLocation("http://localhost:8080/user/login_failed.html");
            response.getCookies().put("logined", "false");
            return response;
        }
        Response response = ResponseFactory.createResponse("302");
        response.setLocation("http://localhost:8080/index.html");
        response.getCookies().put("logined", "true");
        return response;
    }
    private Response getUser(Request request) {
        User user = UserFactory.createUserByQueryString(request);
        userService.create(user);
        return ResponseFactory.createResponse("200");
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
    /**
     * Post 기능구현 추가
     *
     * @param request
     * @return
     */
    public Response routeUserRequest(Request request) {
        logger.debug("url : {}, method : {}", request.getUrl(), request.getMethod());
        if (request.getUrl().equals("/user/create") && request.getMethod().equals("POST")) {
            logger.debug("passed");
            return postUser(request);
        }
        if (request.getUrl().equals("/user/login") && request.getMethod().equals("POST")) {
            logger.debug("passed");
            return postLogin(request);
        }

        if (request.getUrl().equals("/user/create") && request.getMethod().equals("GET")) {
            return getUser(request);
        }

        return serveResources(request);
    }
