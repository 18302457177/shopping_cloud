package com.user.controller;


import api.common.Result;
import com.user.config.RedisCache;
import com.user.service.MSGService;
import com.utils.message.RandomUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user/message")
@Api(tags = "短信接口")
public class MSGController {

    @Autowired
    private MSGService jwtService;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone){

        String codeCache = (String)redisCache.getCacheObject(phone);
        if (codeCache!=null&&!codeCache.isEmpty()){
            return Result.success();
        }
        //生成随机数
        String code = RandomUtil.getFourBitRandom();
        Map map = new HashMap();
        map.put("code",code);
        boolean b = jwtService.send(map,phone);
        if (b){
            //如果发送成功，就把验证码存到redis里，设置5分钟有效时间
            redisCache.setCacheObject(phone,code,5, TimeUnit.MINUTES);
            return Result.success();
        }else {
            return Result.fail("失败");
        }
    }
}