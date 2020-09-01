package cn.ffcs.buffet;

import cn.ffcs.buffet.model.po.ProductCategoryPO;
import cn.ffcs.buffet.service.ProductManageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
class BuffetApplicationTests {

    @Autowired
    ProductManageService productManageService;

    @Test
    void contextLoads() {
//        List<ProductCategoryPO> productCategoryList = productManageService.getProductCategoryList();

        ProductCategoryPO categoryPO = new ProductCategoryPO();
        categoryPO.setProductCategoryId(14);
        categoryPO.setCategoryName("test1");
        categoryPO.setCategoryDesc("test1");
        categoryPO.setCreateTime(new Timestamp(new Date().getTime()));
        categoryPO.setUpdateTime(new Timestamp(new Date().getTime()));
//        Integer s = productManageService.updateProductCategory(categoryPO);
//        Integer integer = productManageService.deleteProductCategory(14);
        ProductCategoryPO categoryPO1 = productManageService.getProductCategoryByproductCategoryId(13);
        return;
    }

}
