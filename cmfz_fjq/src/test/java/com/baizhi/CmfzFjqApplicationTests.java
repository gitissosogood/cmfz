package com.baizhi;

import com.baizhi.controller.UserController;
import com.baizhi.dao.*;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Menu;
import com.baizhi.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzFjqApplicationTests {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private UserController userController;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() {
        List<Menu> menuList = menuDao.queryAll();
        for (Menu menu : menuList) {
            System.out.println(menu);
        }
    }

    @Test
    public void albumDao() {
       /* List<Album> albums = albumDao.queryAll();
        for (Album album : albums) {
            System.out.println(album);
        }*/
       /*Chapter chapter = new Chapter();
       chapter.setId("67abbb50-c888-4d6f-b881-3397365fb2ad");
        Chapter chapter1 = chapterDao.selectOne(chapter);
        System.out.println(chapter1);*/
        List<Album> albumList = albumDao.selectByTime();
        for (Album album : albumList) {
            System.out.println(album);
        }
    }

    @Test
    public void testBanner() {
        /*Field[] declaredFields = Banner.class.getDeclaredFields();
        System.out.println(declaredFields.toString());*/
        Banner banner = new Banner();
        banner.setStatus("yes");
        int i = bannerDao.selectCount(banner);
        System.out.println(i);
    }

    @Test
    public void testUser() {
        Map scatter = userController.scatter();
        System.out.println(scatter.get("seriesData1"));
    }

    @Test
    public void testMD5() {
        String md5Hex = DigestUtils.md5Hex("123456");
        System.out.println(md5Hex);

    }

    @Test
    public void testRegister() {
        User user = new User();
        user.setSalt("ad");
        user.setPassword("123");
        user.setPhone("123455");

        userDao.add(user);
        System.out.println(user);
    }

}
