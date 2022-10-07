package cn.hncj.grabbing.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author FanJian
 * @Date 2022/10/6 21:06
 */
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendGrabMessage(String message) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("grabExchange","grab.message",message);
    }
}
