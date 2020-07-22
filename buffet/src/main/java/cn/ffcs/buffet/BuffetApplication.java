package cn.ffcs.buffet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = "cn.ffcs.buffet.mapper")
@SpringBootApplication
public class BuffetApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuffetApplication.class, args);
    }

}
