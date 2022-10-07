package cn.hncj.grabbing.controller;

import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.service.ISeatsService;
import cn.hncj.grabbing.service.IUserService;
import cn.hncj.grabbing.vo.SeatsVo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
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

    @Autowired
    private ISeatsService seatsService;

    @RequestMapping("/toList")
    public String toList(HttpServletRequest request, Model model) {
        User user = (User) request.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("seatsList",seatsService.findSeatsVo());
        return "seatsList";
    }

    @RequestMapping("/toDetail/{seatsId}")
    public String toDetail(HttpServletRequest request, Model model, @PathVariable("") Long seatsId) {
        User user = (User) request.getAttribute("user");
        model.addAttribute("user",user);
        SeatsVo seatsVo  = seatsService.findSeatsVoBySeatsId(seatsId);
        Date startDate = seatsVo.getStartDate();
        Date endDate = seatsVo.getEndDate();
        Date nowDate = new Date();
        // 抢座状态
        int grabStatus;
        // 抢座倒计时
        int remainSeconds;
        // 未开始
        if (nowDate.before(startDate)) {
            grabStatus = 0;
            remainSeconds = (int) (startDate.getTime() - nowDate.getTime()) / 1000;
        }
        // 已结束
        else if (nowDate.after(endDate)) {
            grabStatus = 2;
            remainSeconds = -1;
        }
        // 进行中
        else {
            grabStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("grabStatus",grabStatus);
        model.addAttribute("seats",seatsService.findSeatsVoBySeatsId(seatsId));
        model.addAttribute("seats",seatsVo);
        return "seatsDetail";
    }
}
