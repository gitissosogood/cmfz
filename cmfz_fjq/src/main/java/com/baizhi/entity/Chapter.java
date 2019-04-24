package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_chapter")
@ExcelTarget("chapter")
public class Chapter {

    private String id;
    @Excel(name = "章节名")
    private String title;
    @Excel(name = "章节大小")
    private String size;
    @Excel(name = "章节时长")
    private String duration;
    @JSONField(format = "yyyy-MM-dd HH:hh:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:hh:ss")
    @Excel(name = "章节上传时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;
    private String newName;
    private String oldName;

}
