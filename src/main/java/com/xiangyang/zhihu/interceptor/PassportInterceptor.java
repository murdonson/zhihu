package com.xiangyang.zhihu.interceptor;

import com.xiangyang.zhihu.dao.LoginTicketMapper;
import com.xiangyang.zhihu.model.HostHolder;
import com.xiangyang.zhihu.model.LoginTicket;
import com.xiangyang.zhihu.model.User;
import com.xiangyang.zhihu.service.UserService;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTicketMapper loginTicketMapper;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;

    LoginTicket loginTicket=null;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(httpServletRequest.getCookies()!=null)
        {
            for(Cookie cookie:httpServletRequest.getCookies())
            {
                if(cookie.getName().equals("ticket"))
                {
                   loginTicket=loginTicketMapper.selectByTicket(cookie.getValue());
                }
            }
            if(hostHolder==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!=0)
            {
                hostHolder.clear();
                return true;
            }
            User user=userService.selectByPrimaryKey(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
            if(modelAndView!=null&&hostHolder.getUser()!=null)
            {
                modelAndView.addObject("user",hostHolder.getUser());
            }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
