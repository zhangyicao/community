package com.yicao.community.Service;

import com.yicao.community.constant.ActivationStatus;
import com.yicao.community.entity.User;

import java.util.Map;

public interface UserService {

    User findUserById(int id);

    Map<String, Object> register(User user);

    ActivationStatus activation(int userId, String code);

    Map<String, Object> login(String username, String password, int expiredSeconds);

    void logout(String ticket);
}
