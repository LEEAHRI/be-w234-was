package service;

import exception.LoginFailException;
import model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    /**
     * User 생성
     * @param user
     */
    public void create(User user) {
        userRepository.addUser(user);
        logger.debug("User: {}", user);
    }

    /**
     * UserID로 User조회
     * @param userId
     * @return
     */
    public User getUserByUserId(String userId) {
        return userRepository.findUserById(userId);
    }

    /**
     * User 조회
     * @return
     */
    public List<User> getUser() {
        return userRepository.findAll();
    }

    public void login(User loginUser) {
        User user = getUserByUserId(loginUser.getUserId());
        if (user == null) {
            throw new LoginFailException();
        }

        if (!user.getPassword().equals(loginUser.getPassword())) {
            throw new LoginFailException();
        }
    }
}
