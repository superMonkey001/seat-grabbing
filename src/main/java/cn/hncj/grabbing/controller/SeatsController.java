package cn.hncj.grabbing.controller;

import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @Author FanJian
 * @Date 14:03
 */

@Controller
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/toList")
    public String toList(HttpServletRequest request, Model model) {
        User user = (User) request.getAttribute("user");
        model.addAttribute("user",user);
        return "seatsList";
    }
}
