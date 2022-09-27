package controller;
import factory.ResponseFactory;
import factory.UserFactory;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.ResourceUtils;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private String body;

    public UserController() {
        userService = new UserService();
    }

    private Response postUser(Request request) {
        User user = UserFactory.createUserByBodyParam(request);
        userService.create(user);
        return ResponseFactory.createResponse("302");
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

        if (request.getUrl().equals("/user/create") && request.getMethod().equals("GET")) {
            return getUser(request);
        }

        return serveResources(request);
    }
}
