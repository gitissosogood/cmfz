package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("addChapter")
    @ResponseBody
    public Map addChapter(Integer albumId, Chapter chapter, MultipartFile file, HttpSession session) {
        Map map = new HashMap<>();
        try {
            chapterService.addChapter(albumId, chapter, file, session);
            map.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 2);
        }
        return map;
    }

    @RequestMapping("download")
    public void download(String id, HttpServletResponse response, HttpSession session) throws Exception {
        Chapter chapter = chapterService.selectOne(id);
        String oldName = chapter.getOldName();
        String newName = chapter.getNewName();
        String realPath = session.getServletContext().getRealPath("/");
        InputStream inputStream = new FileInputStream(realPath + "music\\" + newName);
        String t1 = URLEncoder.encode(oldName, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + t1);
        OutputStream os = response.getOutputStream();
        while (true) {
            int i = inputStream.read();
            if (i == -1) break;
            os.write(i);
        }
    }

}
