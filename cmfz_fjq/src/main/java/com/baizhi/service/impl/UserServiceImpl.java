package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserScatter;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> selectAll() {
        List<User> userList = userDao.selectAll();
        return userList;
    }

    @Override
    public Map count() {
        Map map = new HashMap<>();
        String[] xAxisData = {"近一周", "近两周", "近三周"};
        map.put("xAxisData", xAxisData);
        Integer count1 = userDao.count(7);
        Integer count2 = userDao.count(14);
        Integer count3 = userDao.count(21);
        int[] count = {count1, count2, count3};
        map.put("seriesData", count);
        return map;
    }

    @Override
    public Map scatter() {
        Map map = new HashMap<>();
        List<UserScatter> userScatterList1 = userDao.scatter(1);
        List<UserScatter> userScatterList2 = userDao.scatter(0);
        map.put("seriesData1", userScatterList1);
        map.put("seriesData2", userScatterList2);
        return map;
    }

    @Override
    public void addUser(User user) {
        user.setCreateDate(new Date());
        userDao.insert(user);
    }

}
