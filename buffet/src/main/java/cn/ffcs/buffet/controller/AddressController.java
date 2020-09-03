package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.annotation.AvoidRepeatableCommit;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.enums.ExceptionEnum;
import cn.ffcs.buffet.common.util.DeleteStatusConstant;
import cn.ffcs.buffet.model.po.AddressPO;
import cn.ffcs.buffet.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:地址控制器
 * @Author: mingfeng.zhu
 * @Date: 2019/9/22 15:26
 */
@RestController
@RequestMapping(value = "/api/address")
@Api(value = "/api/address", tags = "地址服务")
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    /**
     * 地址服务
     */
    @Autowired
    private AddressService addressService;

    /**
     * 获取地址列表
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "获取地址列表", notes = "获取指定用户地址信息")
    @GetMapping(path = "/list")
    public Result list(@RequestParam Integer userId) {

        List<AddressPO> list = addressService.getAddressListByUserId(userId);
        if (list.size() > 0) {
            return Result.success(list);
        }
        return Result.success();

    }

    /**
     * 根据地址id获取地址信息
     *
     * @param id 地址id
     * @return
     */
    @ApiOperation(value = "获取指定id地址信息", notes = "获取指定id地址信息")
    @GetMapping(path = "/getAddressById")
    public Result getAddressById(@RequestParam Integer id) {

        AddressPO address = addressService.getAddressById(id);
        if (null != address) {
            return Result.success(address);
        }
        return Result.success();

    }

    /**
     * 插入地址信息
     *
     * @param addressPO 地址类
     * @return
     */
    @PostMapping(path = "/insert")
    @AvoidRepeatableCommit
    public Result insert(@RequestBody AddressPO addressPO) {

        if (addressService.insert(addressPO) > 0) {
            return Result.success();
        }
        return Result.success();

    }

    /**
     * 更新地址信息
     *
     * @param addressPO 地址类
     * @return
     */
    @PostMapping(path = "/update")
    public Result update(@RequestBody AddressPO addressPO) {

        if (addressService.update(addressPO) > 0) {
            return Result.success();
        }
        return Result.success();
    }

    /**
     * 根据id删除地址信息
     *
     * @param id 地址id
     * @return
     */
    @DeleteMapping(path = "/delete/{id}")
    public Result delete(@PathVariable Integer id) {

        AddressPO addressPO = new AddressPO();
        addressPO.setId(id);
        addressPO.setAddressDelete(DeleteStatusConstant.YES);
        System.out.println(addressPO);
        if (addressService.update(addressPO) > 0) {
            return Result.success();
        }
        return Result.fail("删除地址失败");

    }
}
