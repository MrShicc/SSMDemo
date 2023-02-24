package com.mrjiang.ssmdemo.core.interceptors;

import com.mrjiang.ssmdemo.mybatis.domain.user.UserDO;
import org.apache.ibatis.jdbc.Null;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模块名称:自定义拦截器
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/24
 * 联系时间:15912181467
 */
public class MyTestInterceptor implements HandlerInterceptor {
    // 在调用目标controller方法“前”会被调用
    // 返回值：true代表放行，false代表拦截住不继续往下执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        System.out.println("拦截器：preHandle");

        Object userName = request.getSession().getAttribute("userName");

        Object userState = request.getSession().getAttribute("state");

        //拦截翻墙访问
        if (userName == null && userState == null){


            request.getSession().setAttribute("Code",1);


            response.setHeader("status","loginOut");
//            request.getRequestDispatcher("/toInt").forward(request, response);
            return false;
        }

        return true;
    }

    // 在调用目标controller方法“后”，解析视图之"前"
    // 可以让编程者，控制最终视图的走向、以及视图中携带的数据内容是否发生更改
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器：postHandle");
    }

    // 在调用目标controller方法“后”，以及视图渲染完成，数据返回成功才执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器：afterCompletion");

    }

}
