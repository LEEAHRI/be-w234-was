package service;

import factory.RequestFactory;
import factory.UserFactory;
import db.Database;
import model.Request;
import model.User;
import util.HttpRequestUtils;

import java.util.Map;

import static model.User.isValid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public static void create(Request request) {

        int index = request.getUrl().indexOf("?");
        String queryString = request.getUrl().substring(index + 1);
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
        try {
            if (isValid(params.get("userId"), params.get("password"), params.get("name"), params.get("email"))) {
                User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
                Database.addUser(user);
                logger.debug("{}", user);
            }
        } catch (exception.CreateUserException e) {
            logger.error("CreateUserException : {}", e.getMessage());

        }
    }
}
