package cn.ffcs.buffet;

import cn.ffcs.buffet.service.ProductModuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BuffetApplicationTests {

    @Autowired
    ProductModuleService productModuleService;

    @Test
    void contextLoads() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return;
    }

}
