package factory;

import model.Request;
import model.User;

public class UserFactory {

    public static User createUser(Request request) {
        return User();
    }
}
