package cn.ffcs.buffet;

import cn.ffcs.buffet.model.dto.ProductDTO;
import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;
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
        /*List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        List<Integer> list1 = new ArrayList<>();
        list1.add(3);
        list1.add(4);

        Integer integer = productModuleService.updateProductStorage(list, list1);*/

//        List<ProductDTO> productDTOList = productModuleService.selectProductDetailAndCommentList(1);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(22);

        List<ProductSpecificationDTO> productSpecificationDTOList = productModuleService.selectSpecificationByProductSpecificationIdList(list);

        return;
    }

}
