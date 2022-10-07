package cn.hncj.grabbing.vo;

import cn.hncj.grabbing.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author FanJian
 * @Date 2022/10/6 21:01
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrabMessage {
    private User user;
    private Long seatsId;
}
