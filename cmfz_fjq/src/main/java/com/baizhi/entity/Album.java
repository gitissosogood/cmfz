package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "t_album")
@ExcelTarget(value = "album")
public class Album {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Excel(name = "名字", needMerge = true)
    private String title;
    @Excel(name = "集数", needMerge = true)
    private Integer amount;
    @Excel(name = "评分", needMerge = true)
    private Double score;
    @Excel(name = "作者", needMerge = true)
    private String author;
    @Excel(name = "播音", needMerge = true)
    private String boardcast;
    @JSONField(format = "yyyy-MM-dd HH:hh:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:hh:ss")
    private Date publishDate;
    @Excel(name = "描述", needMerge = true)
    private String brief;
    //private String downloadPath;
    @Excel(name = "图片", type = 2, needMerge = true)
    private String imgPath;
    @Transient
    @ExcelCollection(name = "章节")
    private List<Chapter> children;

}
