package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: demo
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/21 10:29
 */
@Api(value = "/api/demo", tags = "demo服务")
@Validated
@RestController
@RequestMapping(value = "/api/demo")
public class DemoController {

    /**
     * demo****
     * @return
     */
    @ApiOperation(value = "获取index", notes = "首页")
    @GetMapping(path = "/index")
    public Result Index(){
        int i = 1/0;
        return Result.success();
    }
}
