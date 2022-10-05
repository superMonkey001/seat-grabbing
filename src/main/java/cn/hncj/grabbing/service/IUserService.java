package cn.hncj.grabbing.service;

import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.vo.CommonResult;
import cn.hncj.grabbing.vo.LoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-10-04
 */
public interface IUserService extends IService<User> {
    CommonResult doLogin(HttpServletRequest request, HttpServletResponse response,LoginVo vo);

    User getUserByCookie(HttpServletRequest request,HttpServletResponse response,String ticket);
}
