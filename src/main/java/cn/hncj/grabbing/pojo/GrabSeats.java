package cn.hncj.grabbing.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-10-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_grab_seats")
public class GrabSeats implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long seatsId;

    private Integer stockCount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


}
