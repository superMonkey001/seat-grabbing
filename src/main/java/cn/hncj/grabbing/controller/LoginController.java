package cn.hncj.grabbing.controller;

import cn.hncj.grabbing.service.IUserService;
import cn.hncj.grabbing.vo.CommonResult;
import cn.hncj.grabbing.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author FanJian
 * @Date 2022/10/4
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @ResponseBody
    @RequestMapping("/doLogin")
    public CommonResult doLogin(HttpServletRequest request, HttpServletResponse response, @Valid LoginVo vo) {
        // 向redis和客户端cookie中添加数据
        iUserService.doLogin(request,response,vo);
        return CommonResult.success();
    }

}
