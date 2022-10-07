package cn.hncj.grabbing.rabbitmq;

import cn.hncj.grabbing.exception.CustomizeExceptionEnum;
import cn.hncj.grabbing.pojo.GrabOrder;
import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.service.IOrderService;
import cn.hncj.grabbing.service.ISeatsService;
import cn.hncj.grabbing.vo.GrabMessage;
import cn.hncj.grabbing.vo.SeatsVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @Author FanJian
 * @Date 2022/10/6 21:15
 */

@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private ISeatsService seatsService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "grabQueue")
    public void receive(String msg) {
        log.info("接收消息；" + msg);
        GrabMessage grabMessage = JSON.parseObject(msg, GrabMessage.class);
        Long seatsId = grabMessage.getSeatsId();
        User user = grabMessage.getUser();
        SeatsVo seatsVo = seatsService.findSeatsVoBySeatsId(seatsId);
        if (seatsVo.getStockCount() < 1) {
            return;
        }

        // 判断是否重复抢购
        ValueOperations<String, String> op = redisTemplate.opsForValue();
        String jsonOrder = op.get("order:" + user.getId() + ":" + seatsId);
        GrabOrder grabOrder = JSON.parseObject(jsonOrder,GrabOrder.class);
        if (grabOrder != null) {
            return;
        }
        orderService.grab(user,seatsVo);
    }
}
