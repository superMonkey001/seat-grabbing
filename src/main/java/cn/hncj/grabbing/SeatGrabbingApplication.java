package cn.hncj.grabbing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author FanJian
 */
@SpringBootApplication
@MapperScan("cn.hncj.grabbing.mapper")
public class SeatGrabbingApplication {

    public static void main(String[] args) {

        SpringApplication.run(SeatGrabbingApplication.class, args);
    }

}
