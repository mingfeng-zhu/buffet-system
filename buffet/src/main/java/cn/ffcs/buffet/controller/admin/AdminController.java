package cn.ffcs.buffet.controller.admin;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.model.po.UserPO;
import cn.ffcs.buffet.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:admin控制器
 * @Author: mingfeng.zhu
 * @Date: 2020/9/8 19:47
 */

@RestController
@RequestMapping(value = "/admin/user")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * 用户服务
     */

    @Autowired
    private UserService userService;

    /**
     * 管理员获取用户信息
     *
     * @param page
     * @param username
     * @param userStatus
     * @return
     */

    @ApiOperation(value = "获取用户列表", notes = "管理员获取用户信息")
    @GetMapping(path = "/list")
    @ResponseBody
    public Result list(Page page, String username, Integer userStatus) {

        PageInfo<UserPO> userList = userService.listUser(page, username, userStatus);
        page.setList(userList.getList());
        page.setTotal(userList.getTotal());
        return Result.success(page);

    }

    /**
     * 管理员修改用户状态
     *
     * @param user 用户类
     * @return
     */
    @ApiOperation(value = "管理员修改用户状态")
    @PostMapping(path = "/updateUser")
    public Result updateUser(@RequestBody UserPO user) {
        Integer num = userService.updateUser(user);
        if (user.getUserId() == null) {
            return Result.fail("缺少用户id");
        }
        if (num > 0) {
            return Result.success();
        }
        return Result.fail("修改失败");
    }

}
