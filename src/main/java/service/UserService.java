package service;

import db.Database;
import model.Request;

public class UserService {

    public void create(Request request) {

        Database.addUser();
    }

    public void update(Request request) {

    }
}
