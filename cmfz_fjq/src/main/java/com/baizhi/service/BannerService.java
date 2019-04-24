package com.baizhi.service;

import com.baizhi.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface BannerService {

    Map selectAll(int page, int rows);

    void addBanner(MultipartFile file, String title, HttpSession session) throws Exception;

    void update(Banner banner);

    void delete(Banner banner);

    void exportExcel(HttpServletResponse response);

    Integer selectYesCount();
}
