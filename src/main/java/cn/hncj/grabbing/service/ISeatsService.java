package cn.hncj.grabbing.service;

import cn.hncj.grabbing.pojo.Seats;
import cn.hncj.grabbing.vo.SeatsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-10-05
 */
public interface ISeatsService extends IService<Seats> {

    List<SeatsVo> findSeatsVo();

    SeatsVo findSeatsVoBySeatsId(Long seatsId);
}
