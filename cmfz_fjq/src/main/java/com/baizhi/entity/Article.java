package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Data
public class Article {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String title;
    private String content;
    private String imgPath;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    private Integer status;
    @Transient
    private Master master;

}
