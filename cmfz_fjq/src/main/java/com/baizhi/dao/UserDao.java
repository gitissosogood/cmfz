package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserScatter;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    Integer count(int i);

    List<UserScatter> scatter(int i);

    void add(User user);

}
