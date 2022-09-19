package service;

import db.Database;
import model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void create(User user) {
        Database.addUser(user);
        logger.debug("User: {}", user);
    }

    public List<User> getUsers() {
        return (List<User>) Database.findAll();
    }
}