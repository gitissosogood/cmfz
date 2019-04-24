package com.baizhi.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name = "t_menu")
public class Menu {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String title;
    private String iconCls;
    private String jspUrl;
    @Transient
    private List<Menu> menuList;

}
