package cn.hncj.grabbing.service.impl;

import cn.hncj.grabbing.mapper.SeatsMapper;
import cn.hncj.grabbing.pojo.Seats;
import cn.hncj.grabbing.service.ISeatsService;
import cn.hncj.grabbing.vo.SeatsVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-10-05
 */
@Service
public class SeatsServiceImpl extends ServiceImpl<SeatsMapper, Seats> implements ISeatsService {

    @Autowired
    private SeatsMapper seatsMapper;

    @Override
    public List<SeatsVo> findSeatsVo() {
        return seatsMapper.findSeatsVo();
    }

    @Override
    public SeatsVo findSeatsVoBySeatsId(Long seatsId) {
        return seatsMapper.findSeatsVoBySeatsId(seatsId);
    }
}
