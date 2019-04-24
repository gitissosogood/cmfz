package com.baizhi.controller;

import com.baizhi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping("first")
    public Map first(Integer uid, String type, String sub_type) {
        Map map = documentService.first(uid, type, sub_type);
        return map;
    }

    @RequestMapping("album")
    public Map album(Integer id, Integer uid) {
        Map map = documentService.album(id, uid);
        return map;
    }

    @RequestMapping("register")
    public Map register(String phone, String password) {
        Map map = documentService.register(phone, password);
        return map;
    }

    @RequestMapping("login")
    public Map login(String phone, String password) {
        Map map = documentService.login(phone, password);
        return map;
    }

}
