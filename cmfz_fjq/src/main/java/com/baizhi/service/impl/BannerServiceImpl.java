package com.baizhi.service.impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public Map selectAll(int page, int rows) {
        Map map = new HashMap<>();
        PageHelper.startPage(page, rows);
        PageInfo<Banner> pageInfo = new PageInfo<>(bannerDao.selectAll());
        List<Banner> bannerList = pageInfo.getList();
        long total = pageInfo.getTotal();
        map.put("rows", bannerList);
        map.put("total", total);
        return map;
    }

    @Override
    public void addBanner(MultipartFile file, String title, HttpSession session) throws Exception {
        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid + oldName.substring(oldName.lastIndexOf("."));
        String realPath = session.getServletContext().getRealPath("/");
        //System.out.println(realPath);
        file.transferTo(new File(realPath + "img\\shouye\\" + newName));
        Banner banner = new Banner();
        banner.setTitle(title);
        banner.setStatus("no");
        banner.setImgPath(newName);
        banner.setCreateDate(new Date());
        banner.setDeleteStatus(0);
        bannerDao.insert(banner);
    }

    @Override
    public void update(Banner banner) {
        String status = banner.getStatus();
        if ("yes".equals(status)) {
            Banner selectBanner = new Banner();
            selectBanner.setStatus("yes");
            int i = bannerDao.selectCount(selectBanner);
            if (i < 5) {
                bannerDao.updateByPrimaryKeySelective(banner);
            }
        } else if ("no".equals(status)) {
            bannerDao.updateByPrimaryKeySelective(banner);
        }
    }

    @Override
    public void delete(Banner banner) {
        Integer id = banner.getId();
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    public void exportExcel(HttpServletResponse response) {
        List<Banner> bannerList = bannerDao.selectAll();
        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setItalic(true);
        //创建日期格式
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        //创建样式1
        CellStyle cellStyle = workbook.createCellStyle();
        //创建字体的样式
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建样式2
        CellStyle cellStyle1 = workbook.createCellStyle();
        //创建日期格式的样式
        cellStyle1.setDataFormat(format);
        //创建工作表
        Sheet sheet = workbook.createSheet("banner");
        //设置列宽
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 40 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        //存储表头数据
        String string[] = {"编号", "标题", "路径", "时间", "状态"};
        //创建行
        Row row = sheet.createRow(0);
        //创建表头
        for (int i = 0; i < string.length; i++) {
            //创建单元格
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            //给单元格赋值
            cell.setCellValue(string[i]);
        }
        //创建数据
        for (int i = 0; i < bannerList.size(); i++) {
            //创建行
            Row row1 = sheet.createRow(i + 1);
            //创建单元格并赋值
            row1.createCell(0).setCellValue(bannerList.get(i).getId());
            row1.createCell(1).setCellValue(bannerList.get(i).getTitle());
            row1.createCell(2).setCellValue(bannerList.get(i).getImgPath());
            Cell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle1);
            cell.setCellValue(bannerList.get(i).getCreateDate());
            row1.createCell(4).setCellValue(bannerList.get(i).getStatus());
        }
        String name = "banner.xls";
        response.setHeader("content-disposition", "attachment;filename=" + name);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Integer selectYesCount() {
        Banner banner = new Banner();
        banner.setStatus("yes");
        int yesCount = bannerDao.selectCount(banner);
        return yesCount;
    }

}
