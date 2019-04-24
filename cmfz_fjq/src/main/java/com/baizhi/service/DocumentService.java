package com.baizhi.service;

import java.util.Map;

public interface DocumentService {

    /**
     * 首页
     *
     * @param uid      用户唯一标识 必填
     * @param type     请求数据类型（首页：all，闻：wen，思：si） 必填
     * @param sub_type 上师言教：ssyj，显密法要：xmfy 选填（当type为si的时候传入）
     * @return
     */
    Map first(Integer uid, String type, String sub_type);

    /**
     * 专辑
     *
     * @param id  专辑id，由上一页面列表传过来 必填
     * @param uid 用户id 必填
     * @return
     */
    Map album(Integer id, Integer uid);

    /**
     * 注册
     *
     * @param phone    用户名（手机号） 必填
     * @param password MD5后的用户密码  必填
     * @return
     */
    Map register(String phone, String password);

    /**
     * 登录
     *
     * @param phone    用户名（手机号） 必填
     * @param password MD5后的用户密码 必填
     * @return
     */
    Map login(String phone, String password);
}
