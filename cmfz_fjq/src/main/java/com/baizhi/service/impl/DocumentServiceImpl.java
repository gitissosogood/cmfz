package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import com.baizhi.service.DocumentService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Map first(Integer uid, String type, String sub_type) {
        Map map = new HashMap();
        String errorMessage = "";
        if (uid == null || type == null) {
            errorMessage = "uid和type为必填参数，不能为空";
            map.put("errorMessage", errorMessage);
            return map;
        } else {
            if ("all".equals(type)) {
                //轮播图(5张)
                Banner banner = new Banner();
                banner.setStatus("yes");
                List<Banner> bannerList = bannerDao.select(banner);
                map.put("banners", bannerList);
                //专辑(根据时间最新的6个)
                List<Album> albumList = albumDao.selectByTime();
                map.put("albums", albumList);
                //文章(根据用户id查到上师的最新文章的2个)
                List<Article> articleList = articleDao.selectByMaster(uid);
                map.put("articles", articleList);
                return map;
            } else if ("wen".equals(type)) {
                List<Album> albumList = albumDao.queryAll();
                map.put("album", albumList);
                return map;
            } else {
                if (sub_type == null) {
                    errorMessage = "type为si的时候不能为空";
                    map.put("errorMessage", errorMessage);
                    return map;
                } else {
                    if ("ssyj".equals(sub_type)) {
                        //(所有文章)
                        List<Article> articleList = articleDao.selectAllByMaster(uid);
                        map.put("article", articleList);
                        return map;
                    } else {
                        List<Article> articleList = articleDao.selectAllByOthers(uid);
                        map.put("article", articleList);
                        return map;
                    }
                }
            }
        }
    }

    @Override
    public Map album(Integer id, Integer uid) {
        Map map = new HashMap();
        String errorMessage = "";
        if (id == null || uid == null) {
            errorMessage = "id和uid为必填参数，不能为空";
            map.put("errorMessage", errorMessage);
            return map;
        } else {
            Album album = albumDao.queryOne(id);
            map.put("album", album);
            return map;
        }
    }

    @Override
    public Map register(String phone, String password) {
        Map map = new HashMap();
        String errorMessage = "";
        if (phone == null || password == null) {
            errorMessage = "phone和password为必填参数，不能为空";
            map.put("errorMessage", errorMessage);
            return map;
        } else {
            User user = new User();
            user.setPhone(phone);
            User result = userDao.selectOne(user);
            if (result != null) {
                errorMessage = "该手机号已经存在";
                map.put("errorMessage", errorMessage);
                return map;
            } else {
                String salt = "";
                Random rand = new Random();
                for (int i = 0; i < 6; i++) {
                    int num = rand.nextInt(3);
                    switch (num) {
                        case 0:
                            char c1 = (char) (rand.nextInt(26) + 'a');//生成随机小写字母
                            salt += c1;
                            break;
                        case 1:
                            char c2 = (char) (rand.nextInt(26) + 'A');//生成随机大写字母
                            salt += c2;
                            break;
                        case 2:
                            salt += rand.nextInt(10);//生成随机数字
                    }
                }
                String md5Hex = DigestUtils.md5Hex(password + salt);
                user.setPassword(md5Hex);
                user.setSalt(salt);
                userDao.insert(user);
                map.put("user", user);
                return map;
            }
        }
    }

    @Override
    public Map login(String phone, String password) {
        Map map = new HashMap();
        String errorMessage = "";
        if (phone == null || password == null) {
            errorMessage = "phone和password为必填参数，不能为空";
            map.put("errorMessage", errorMessage);
            return map;
        } else {
            User user = new User();
            user.setPhone(phone);
            User result = userDao.selectOne(user);
            if (result == null) {
                errorMessage = "该用户不存在";
                map.put("errorMessage", errorMessage);
                return map;
            } else {
                String salt = result.getSalt();
                String md5Hex = DigestUtils.md5Hex(password + salt);
                String userPassword = result.getPassword();
                if (userPassword.equals(md5Hex)) {
                    map.put("user", result);
                    return map;
                } else {
                    errorMessage = "密码错误";
                    map.put("errorMessage", errorMessage);
                    return map;
                }
            }
        }
    }

}
