package cn.hncj.grabbing.service.impl;

import cn.hncj.grabbing.mapper.OrderMapper;
import cn.hncj.grabbing.pojo.GrabOrder;
import cn.hncj.grabbing.pojo.GrabSeats;
import cn.hncj.grabbing.pojo.Order;
import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.service.IGrabOrderService;
import cn.hncj.grabbing.service.IGrabSeatsService;
import cn.hncj.grabbing.service.IOrderService;
import cn.hncj.grabbing.vo.SeatsVo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.jsqlparser.util.deparser.UpdateDeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-10-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    IGrabSeatsService grabSeatsService;
    @Autowired
    IGrabOrderService grabOrderService;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Transactional
    @Override
    public Order grab(User user, SeatsVo seats) {
        GrabSeats grabSeats = grabSeatsService.getOne(new QueryWrapper<GrabSeats>().eq("seats_id", seats.getId()));
        grabSeats.setStockCount(grabSeats.getStockCount() - 1);
        boolean success = grabSeatsService.update(new UpdateWrapper<GrabSeats>().setSql("stock_count = stock_count - 1").eq("seats_id", seats.getId()).gt("stock_count", 0));
        if (!success) {
            return null;
        }
        grabSeatsService.updateById(grabSeats);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setSeatsId(seats.getId());
        order.setSeatsName(seats.getSeatsName());
        order.setSeatsCount(1);
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        GrabOrder grabOrder = new GrabOrder();
        grabOrder.setUserId(user.getId());
        grabOrder.setSeatsId(seats.getId());
        grabOrder.setOrderId(order.getId());
        grabOrderService.save(grabOrder);
        String jsonOrder = JSON.toJSONString(grabOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + seats.getId(),jsonOrder);
        return order;
    }
}
