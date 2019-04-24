package com.baizhi.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

@Data
public class Master {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String dhama;
    private Integer status;
    private String headImg;

}
