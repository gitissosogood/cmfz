package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("selectAll")
    @ResponseBody
    public Map selectAll(int page, int rows) {
        Map map = bannerService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("addBanner")
    @ResponseBody
    public Map addBanner(MultipartFile file, String title, HttpSession session) throws Exception {
        Map map = new HashMap<>();
        try {
            bannerService.addBanner(file, title, session);
            map.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 2);
        }
        return map;
    }

    @RequestMapping("update")
    @ResponseBody
    public void update(Banner banner) {
        bannerService.update(banner);
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Banner banner) {
        bannerService.delete(banner);
    }

    @RequestMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response) {
        bannerService.exportExcel(response);
    }

    @RequestMapping("selectYesCount")
    @ResponseBody
    public Integer selectYesCount() {
        Integer yesCount = bannerService.selectYesCount();
        return yesCount;
    }

}
