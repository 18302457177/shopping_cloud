package com.user.interceptor;

import com.alibaba.fastjson.JSON;

import com.user.domain.UserBase;
import com.utils.jwt.JwtUtil;
import com.user.config.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");

        if (token == null){
            return false;
        }
        Claims claims;
        String userId;
        try {
            claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();

        } catch (Exception e) {
            log.debug("token解析出错");
            return false;
        }


        UserBase stuBase = JSON.parseObject(redisCache.getCacheObject("login:" + userId), UserBase.class);
        if (stuBase != null){
            return true;
        }
        log.debug("token无效");
        return false;
    }
}
