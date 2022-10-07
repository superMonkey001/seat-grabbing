package cn.hncj.grabbing.mapper;

import cn.hncj.grabbing.pojo.Seats;
import cn.hncj.grabbing.vo.SeatsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2022-10-05
 */
public interface SeatsMapper extends BaseMapper<Seats> {

    List<SeatsVo> findSeatsVo();

    SeatsVo findSeatsVoBySeatsId(Long seatsId);
}
