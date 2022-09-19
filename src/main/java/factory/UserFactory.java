package factory;

import model.Request;
import model.User;

public class UserFactory {

    public static User createUserByQueryString(Request request) {
        String userId = request.getQueryString().get("userId");
        String password = request.getQueryString().get("password");
        String name = request.getQueryString().get("name");
        String email = request.getQueryString().get("email");
        return new User(userId, password, name, email);
    }

    public static User createUserByBodyParam(Request request) {
        String userId = request.getBody().get("userId");
        String password = request.getBody().get("password");
        String name = request.getBody().get("name");
        String email = request.getBody().get("email");
        return new User(userId, password, name, email);
    }
}
