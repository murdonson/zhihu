package com.xiangyang.zhihu.util;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class PageUtil {

    public static String getPagination(String url,
                                       int totalNum,
                                       int size,
                                       int currentPage)
    {
        StringBuffer sb=new StringBuffer();

        int totalPage=totalNum%size==0?totalNum/size:totalNum/size+1;
        if(totalPage==0)
        {
            return null;
        }
        else
        {

            if(currentPage>1)
            {
                sb.append("<li><a href='"+url+"?page=1'>首页</a></li>");
                sb.append("<li><a href='"+url+"?page="+(currentPage-1)+"'>上一页</a></li>");
            }
            else
            {
                sb.append("<li><a href='"+url+"?page=1'>首页</a></li>");
            }
            for(int i=currentPage-2;i<currentPage+2;i++)
            {
                if(i<1||i>totalPage)
                {
                    continue;
                }
                if(i==currentPage)
                {
                    sb.append("<li class='active'><a href='"+url+"?page="+i+"'>"+i+"</a></li>");
                }
                else
                {
                    sb.append("<li ><a href='"+url+"?page="+i+"'>"+i+"</a></li>");
                }
            }
            if(currentPage < totalPage) {
               sb.append("<li><a href='" + url+ "?page=" + (currentPage+1) + "'>下一页</a></li>");
                sb.append("<li><a href='" + url + "?page=" + totalPage +"'>尾页</a></li>");
            }else{

                sb.append("<li><a href='" + url + "?page=" + totalPage +"'>尾页</a></li>");
            }

        }
        return sb.toString();


    }



}
