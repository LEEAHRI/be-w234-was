package factory;

import model.Request;
import model.User;
import java.util.Map;

public class UserFactory {
    //리뷰반영4
    public static User createUserByQueryString(Request request) {
        Map<String, String> queryString = request.getQueryString();
        String userId = queryString.get("userId");
        String password = queryString.get("password");
        String name = queryString.get("name");
        String email = queryString.get("email");
        return new User(userId, password, name, email);
    }

    public static User createUserByBodyParam(Request request) {
        Map<String, String> body = request.getBody();
        String userId = body.get("userId");
        String password = body.get("password");
        String name = body.get("name");
        String email = body.get("email");
        return new User(userId, password, name, email);
    }
}
