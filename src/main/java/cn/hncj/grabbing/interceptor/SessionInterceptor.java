package cn.hncj.grabbing.interceptor;

import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.service.IUserService;
import cn.hncj.grabbing.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author FanJian
 * @Date 2022/10/5 16:37
 */
@Configuration
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是方法的请求（换句话说，如果是请求静态资源）的话，就放行
        if(!(handler instanceof HandlerMethod))
        {
            return true;
        }

        String userTicket = CookieUtils.getCookieValue(request, "userTicket");

        if (userTicket == null) {
            response.sendRedirect("/login/toLogin");
            return false;
        }

        User user = userService.getUserByCookie(request,response,userTicket);

        // 如果用户为空，那么说明没有登录，就应该跳转到登录页
        if (user == null) {
            response.sendRedirect("/login/toLogin");
            return false;
        }
        else {
            // 因为toList方法跳转到seatsList页面，而该页面需要传递user对象，因此需要这次判断
            String methodName = ((HandlerMethod) handler).getMethod().getName();
            if (methodName.equals("toList") || methodName.equals("toDetail") || methodName.equals(("doGrab"))) {
                request.setAttribute("user",user);
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
