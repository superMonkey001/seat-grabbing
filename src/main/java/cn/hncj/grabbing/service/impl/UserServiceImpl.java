package cn.hncj.grabbing.service.impl;

import cn.hncj.grabbing.mapper.UserMapper;
import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.service.IUserService;
import cn.hncj.grabbing.util.CookieUtils;
import cn.hncj.grabbing.util.MD5Utils;
import cn.hncj.grabbing.util.UUIDUtils;
import cn.hncj.grabbing.vo.CommonResult;
import cn.hncj.grabbing.exception.CustomizeExceptionEnum;
import cn.hncj.grabbing.vo.LoginVo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-10-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public CommonResult doLogin(HttpServletRequest request, HttpServletResponse response,LoginVo vo) {
        String mobile = vo.getMobile();
        String password = vo.getPassword();

        // 1. 通过手机号找去数据库中查询有没有用户
        User user = userMapper.selectById(mobile);


        // 手机号码或密码有误
        if (user == null) {
            return CommonResult.error(CustomizeExceptionEnum.MOBILE_OR_PASSWORD_ERROR);
        }

        // 密码错误
        if (!MD5Utils.dbPassword(password,user.getSalt()).equals(user.getPassword())) {
            return CommonResult.error(CustomizeExceptionEnum.MOBILE_OR_PASSWORD_ERROR);
        }

        // 2. 向redis中塞入key、value，向客户端设置cookie信息。
        String ticket = UUIDUtils.uuid();
        redisTemplate.opsForValue().set("user:" + ticket,JSON.toJSONString(user));
        CookieUtils.setCookie(request,response,"userTicket",ticket);
        return CommonResult.success();
    }

    @Override
    public User getUserByCookie(HttpServletRequest request, HttpServletResponse response, String ticket) {
        if (StringUtils.isBlank(ticket)) {
            return null;
        }
        String jsonUser = redisTemplate.opsForValue().get("user:" + ticket);
        User user = JSON.parseObject(jsonUser,User.class);
        if (user != null) {
            CookieUtils.setCookie(request,response,"userTicket",ticket);
        }
        return user;
    }

}
