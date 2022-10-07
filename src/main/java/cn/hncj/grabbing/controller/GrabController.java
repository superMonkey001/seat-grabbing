package cn.hncj.grabbing.controller;

import cn.hncj.grabbing.exception.CustomizeExceptionEnum;
import cn.hncj.grabbing.pojo.GrabOrder;
import cn.hncj.grabbing.pojo.Order;
import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.rabbitmq.MQSender;
import cn.hncj.grabbing.service.IGrabOrderService;
import cn.hncj.grabbing.service.IOrderService;
import cn.hncj.grabbing.service.ISeatsService;
import cn.hncj.grabbing.service.IUserService;
import cn.hncj.grabbing.util.CookieUtils;
import cn.hncj.grabbing.vo.CommonResult;
import cn.hncj.grabbing.vo.GrabMessage;
import cn.hncj.grabbing.vo.SeatsVo;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author FanJian
 * @Date 2022/10/6 11:08
 */

@Controller
@RequestMapping("/grab")
public class GrabController implements InitializingBean {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ISeatsService seatsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IGrabOrderService grabOrderService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private MQSender mqSender;

    private Map<Long,Boolean> emptyStockMap = new HashMap<>();

    @Autowired
    private RedisScript script;

    @RequestMapping("/doGrab")
    public String doGrab(HttpServletRequest request, Model model, Long seatsId) {
        User user = (User) request.getAttribute("user");
        model.addAttribute("user", user);
        SeatsVo seats = seatsService.findSeatsVoBySeatsId(seatsId);
        if (seats.getStockCount() < 1) {
            model.addAttribute("errmsg", CustomizeExceptionEnum.EMPTY_STOCK.getMsg());
            return "grabFail";
        }


        // 判断是否重复抢购
        ValueOperations<String, String> op = redisTemplate.opsForValue();
        String jsonOrder = op.get("order:" + user.getId() + ":" + seatsId);
        GrabOrder grabOrder = JSON.parseObject(jsonOrder, GrabOrder.class);
        if (grabOrder != null) {
            model.addAttribute("errmsg", CustomizeExceptionEnum.REPEAT_ERROR.getMsg());
            return "grabFail";
        }

        if (emptyStockMap.get(seatsId) == true) {
            model.addAttribute("errmsg", CommonResult.error(CustomizeExceptionEnum.EMPTY_STOCK));
            return "grabFail";
        }

        // ========
        // 用户只能抢一个座位
        // 1. 在mysql端user表创建已抢的座位数字段
        // 2. 判断用户已抢座位数是否为1
        if (user.getGrabCount() >= 1) {
            return "grabFail";
        }
        // ========
        // 预减剩余座位
        /// Long stock = op.decrement("grabSeats:" + seatsId);

        Long stock = (Long) redisTemplate.execute(script, Collections.singletonList("grabSeats:" + seatsId));

        if (stock < 0) {
            emptyStockMap.put(seatsId,true);
            op.increment("grabSeats:" + seatsId);
            model.addAttribute("errmsg", CommonResult.error(CustomizeExceptionEnum.EMPTY_STOCK));
            return "grabFail";
        }

        user.setGrabCount(1);
        /// 1. 发现缓存不一致问题
        userService.updateById(user);

        /// 2. 解决缓存不一致问题
        String jsonUser = JSON.toJSONString(user);
        String userTicket = CookieUtils.getCookieValue(request, "userTicket");
        // 2.1 如果这时候客户端断开连接。
        if (userTicket == null) {
            return "login";
        } else {
            redisTemplate.opsForValue().set("user:" + userTicket, jsonUser);
        }
        Order order = orderService.grab(user, seats);
        model.addAttribute("order", order);
        model.addAttribute("seats", seats);

        // 交给rabbitMQ消费者消费
        GrabMessage grabMessage = new GrabMessage(user, seatsId);
        mqSender.sendGrabMessage(JSON.toJSONString(grabMessage));


        // --------
        // 最开始的检测是否重复抢购的方式（可加可不加）
        /// GrabOrder grabOrder = grabOrderService.getOne(new QueryWrapper<GrabOrder>().eq("user_id", user.getId()).eq("seats_id", seatsId));
        /// if (grabOrder != null) {
        ///     model.addAttribute("errmsg",CustomizeExceptionEnum.REPEAT_ERROR.getMsg());
        ///     return "grabFail";
        /// }
        // --------

        return "orderDetail";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SeatsVo> list = seatsService.findSeatsVo();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(seatsVo -> {
                Long id = seatsVo.getId();
                redisTemplate.opsForValue().set("grabSeats:" + id, seatsVo.getStockCount() + "");
                emptyStockMap.put(id,false);
            });
        }
    }
}
