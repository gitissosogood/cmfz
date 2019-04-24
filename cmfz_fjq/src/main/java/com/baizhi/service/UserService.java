package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> selectAll();

    Map count();

    Map scatter();

    void addUser(User user);
}
