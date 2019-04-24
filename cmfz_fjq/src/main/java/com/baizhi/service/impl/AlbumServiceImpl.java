package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Override
    public List<Album> queryAll() {
        List<Album> albums = albumDao.queryAll();
        return albums;
    }

    @Override
    public void addAlbum(Album album) {
        album.setAmount(0);
        album.setScore(0.0);
        album.setPublishDate(new Date());
        albumDao.insert(album);
    }

}
