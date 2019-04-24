package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface ChapterService {
    void addChapter(Integer albumId, Chapter chapter, MultipartFile file, HttpSession session) throws Exception;

    Chapter selectOne(String id);
}
