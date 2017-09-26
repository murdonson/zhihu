package com.xiangyang.zhihu.dao;

import com.xiangyang.zhihu.model.LoginTicket;

import java.util.Map;

public interface LoginTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginTicket record);

    int updateByPrimaryKey(LoginTicket record);
    LoginTicket selectByTicket(String ticket);

    void updataStatus(Map map);
}