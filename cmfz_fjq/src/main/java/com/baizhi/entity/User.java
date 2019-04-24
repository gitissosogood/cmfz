package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name = "t_user")
public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String name;
    private Integer sex;
    private String province;
    @JSONField(format = "yyyy-MM-dd HH:hh:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:hh:ss")
    private Date createDate;
    private String phone;
    private String password;
    private String salt;
    @Transient
    private Master master;

}
