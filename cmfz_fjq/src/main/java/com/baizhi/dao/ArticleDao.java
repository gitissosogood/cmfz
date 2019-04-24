package com.baizhi.dao;

import com.baizhi.entity.Article;

import java.util.List;

public interface ArticleDao {

    List<Article> selectByMaster(Integer uid);

    List<Article> selectAllByMaster(Integer uid);

    List<Article> selectAllByOthers(Integer uid);

}
