package cn.hncj.grabbing.service;

import cn.hncj.grabbing.pojo.Order;
import cn.hncj.grabbing.pojo.User;
import cn.hncj.grabbing.vo.SeatsVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-10-05
 */
public interface IOrderService extends IService<Order> {
    public Order grab(User user, SeatsVo seats);
}
