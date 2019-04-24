package com.baizhi.controller;

import com.alibaba.fastjson.JSON;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("selectAll")
    @ResponseBody
    public List<User> selectAll() {
        List<User> userList = userService.selectAll();
        return userList;
    }

    @RequestMapping("count")
    @ResponseBody
    public Map count() {
        Map map = userService.count();
        return map;
    }

    @RequestMapping("scatter")
    @ResponseBody
    public Map scatter() {
        Map map = userService.scatter();
        return map;
    }

    @RequestMapping("addUser")
    @ResponseBody
    public Map addUser(User user) {
        Map map = new HashMap<>();
        try {
            userService.addUser(user);
            Map map1 = userService.count();
            String string = JSON.toJSONString(map1);
            GoEasy goEasy = new GoEasy("rest-hangzhou.goeasy.io", "BC-21fc0feadbbc4494ba8b0f630be6c016");
            goEasy.publish("count", string);
            map.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 2);
        }
        return map;
    }

}
