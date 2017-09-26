package com.xiangyang.zhihu.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    public static  ThreadLocal<User> users=new ThreadLocal<>();

    public  User getUser() {
        return users.get();
    }

    public void setUser(User user) {
       users.set(user);
    }
    public void clear()
    {
        users.remove();
    }
}
