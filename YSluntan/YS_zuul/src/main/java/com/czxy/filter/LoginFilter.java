package com.czxy.filter;

import com.czxy.domain.User;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import utils.JwtUtils;
import utils.RasUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 鉴权
 */
@Component
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 拦截用户所有的操作true将会跳转到run方法
     */
    @Override
    public boolean shouldFilter() {
        //获取上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取请求体
        HttpServletRequest request = requestContext.getRequest();
        //获取URI
        String requestURI = request.getRequestURI();
        if (requestURI.contains("Ryan")){
            //如果是已经登录的方法就进行一次鉴权,不是登录的就拦截掉
            return true;
        }
        return false;
    }

    private static final String pubKeyPath = "D:\\temp\\password.pub";

    /**
     * 在这里对核心业务进行判断.............
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取请求体
        HttpServletRequest request = requestContext.getRequest();
        //获取网页的token数据
        String token = request.getHeader("authorization");
        try {
            //这个地方进行鉴权
            User user = JwtUtils.getObjectFromToken(token, RasUtils.getPublicKey(pubKeyPath), User.class);
            //将uid放入请求头
            requestContext.addZuulRequestHeader("uid",user.getUid()+"");
        } catch (Exception e) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(429);
//            requestContext.setResponseBody("未检测到登录状态,请登录");
        }
        return null;
    }
}
