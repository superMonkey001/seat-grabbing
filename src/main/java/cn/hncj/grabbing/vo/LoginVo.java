package cn.hncj.grabbing.vo;

import cn.hncj.grabbing.validate.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author FanJian
 * @Date 2022/10/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length
    private String password;
}
