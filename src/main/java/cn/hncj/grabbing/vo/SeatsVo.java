package cn.hncj.grabbing.vo;

import cn.hncj.grabbing.pojo.Seats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author FanJian
 * @Date 2022/10/5 21:28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatsVo extends Seats {
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
