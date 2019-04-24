package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<Album> queryAll() {
        List<Album> albums = albumService.queryAll();
        return albums;
    }

    @RequestMapping("addAlbum")
    @ResponseBody
    public Map addAlbum(Album album, MultipartFile file, HttpSession session) {
        Map map = new HashMap();
        try {
            String oldName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String extension = FilenameUtils.getExtension(oldName);
            String newName = uuid + "." + extension;
            String realPath = session.getServletContext().getRealPath("/");
            //System.out.println(realPath);
            file.transferTo(new File(realPath + "img\\audioCollection\\" + newName));
            album.setImgPath(newName);
            albumService.addAlbum(album);
            map.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", 2);
        }
        return map;
    }

    @RequestMapping("exportAlbum")
    @ResponseBody
    public void exportAlbum(HttpServletResponse response, HttpSession session) {
        String name = "album.xls";
        response.setHeader("content-disposition", "attachment;filename=" + name);
        try {
            List<Album> albums = albumService.queryAll();
            String realPath = session.getServletContext().getRealPath("/");
            for (Album album : albums) {
                String imgPath = album.getImgPath();
                album.setImgPath(realPath + "img\\audioCollection\\" + imgPath);
            }
            ServletOutputStream outputStream = response.getOutputStream();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑", "专辑"),
                    Album.class, albums);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
