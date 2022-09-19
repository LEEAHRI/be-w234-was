package controller;

import factory.RequestFactory;
import factory.ResponseFactory;
import model.Request;
import model.Response;
import service.UserService;

import java.util.Map;

public class UserController {
    private UserService userService;

    private Response createUser(Request request) {
        userService.create(request);
        return ResponseFactory.create200OkResponse();
    }

//    private Response updateUser(Request request) {
//        userService.update(request);
//        return ResponseFactory.create200OkResponse();
//    }

    public Response routeUserRequest(Request request) {
        if (request.getUrl().equals("/user/create")) {
            return createUser(request);
        }
//        if (request.getUrl().equals("/user/update")) {
//            return updateUser(request);
//        }
        return null;
    }
}
