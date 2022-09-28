package service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * User 생성
     *
     * @param user (UserId, UserPassword, UserName, UserEmail)
     */
    public void create(User user) {
        Database.addUser(user);
        logger.debug("Create User Successed!\n UserId: {} \n, UserPassword:{}\n, UserName:{}\n, UserEmail:{}\n", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    /**
     * User 조회
     *
     * @return userList 반환
     */

