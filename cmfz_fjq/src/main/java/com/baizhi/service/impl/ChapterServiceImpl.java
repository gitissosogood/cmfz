package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;

    @Override
    public void addChapter(Integer albumId, Chapter chapter, MultipartFile file, HttpSession session) throws Exception {
        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(oldName);
        String newName = uuid + "." + extension;
        String realPath = session.getServletContext().getRealPath("/");
        File music = new File(realPath + "music\\" + newName);
        file.transferTo(music);
        String size = FileUtils.getPrintSize(file.getSize());
        Long time = FileUtils.getDuration(music);
        String duration = FileUtils.formatSeconds(time);
        chapter.setId(uuid);
        chapter.setSize(size);
        chapter.setDuration(duration);
        chapter.setNewName(newName);
        chapter.setOldName(oldName);
        chapter.setPublishDate(new Date());
        chapterDao.add(chapter, albumId);
        Album album = albumDao.selectByPrimaryKey(albumId);
        Integer oldAmount = album.getAmount();
        album.setAmount(oldAmount + 1);
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public Chapter selectOne(String id) {
        Chapter chapter1 = new Chapter();
        chapter1.setId(id);
        Chapter chapter = chapterDao.selectOne(chapter1);
        return chapter;
    }
}
