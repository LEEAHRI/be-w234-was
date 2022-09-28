package repository;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void addUser(User user) {
        Database.users.put(user.getUserId(), user);
    }
    public User findUserById(String userId) {
        return Database.users.get(userId);
    }
    public List<User> findAll() {
        return new ArrayList<>(Database.users.values());
    }
}
