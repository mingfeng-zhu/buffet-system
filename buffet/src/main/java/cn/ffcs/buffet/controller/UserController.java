package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.redis.constant.StaticValue;
import cn.ffcs.buffet.common.redis.utils.RedisCommands;
import cn.ffcs.buffet.common.util.CodeUtils;
import cn.ffcs.buffet.common.util.Constant;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.model.ao.LoginUserAO;
import cn.ffcs.buffet.model.ao.SignUserAO;
import cn.ffcs.buffet.model.vo.UserVO;
import cn.ffcs.buffet.model.po.UserAddress;
import cn.ffcs.buffet.model.po.UserPO;
import cn.ffcs.buffet.service.UserAddressService;
import cn.ffcs.buffet.service.UserService;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: user
 * @Author: wx.zhu@ffcs.cn
 * @Date: 2020/8/5 11:45
 */
@Api(value = "/api/user", tags = "用户服务")
@RestController
@RequestMapping(value = "/api/user")
@Validated
public class UserController {
    /**
     * 用户服务
     */
    @Autowired
    UserService userService;

    @Autowired
    UserAddressService userAddressService;

    /**
     * redis
     */
    @Autowired
    private RedisCommands redisCommands;


    /**
     * 登录
     *
     * @param user 用户类
     * @return
     */
    @ApiOperation(value = "用户登录，登录前请发送手机验证码（目前可先不发，之后用作密码找回？）")
    @PostMapping(path = "/login")
    public Result userLogin(@RequestBody LoginUserAO user, HttpServletRequest request) {
        UserPO checkUser = new UserPO();
        checkUser.setUserTel(user.getUserTel());
        checkUser.setUserPassword(user.getUserPassword());
        checkUser.setUserRole(user.getUserRole());
        UserPO loginUser = userService.checkLogin(checkUser);
        if (loginUser == null) {
            return Result.fail("登录失败!账户不存在");
        }
        if (loginUser.getUserState().equals(Constant.USER_STATUS.forbidden.getIndex())) {
            return Result.fail("登录失败!用户被禁用");
        }
        if (loginUser.getUserPassword().equals(user.getUserPassword())) {
            UserVO userVO = new UserVO();
            userVO.setUserPO(loginUser);
            userVO.setToken(TokenUtil.getToken(loginUser));
            return Result.success(userVO);
        } else {
            return Result.fail("登录失败!密码错误");
        }

    }

    /**
     * 登录 通过手机号
     *
     * @param user 用户类
     * @return
     */
    @ApiOperation(value = "用户登录，登录前请发送手机验证码 ,填写验证码登录")
    @ApiParam(name = "userTel", value = "userTel 用户手机号", required = true)
    @PostMapping(path = "/loginByPhoneNumber")
    public Result loginByPhoneNumber(@RequestBody LoginUserAO user, HttpServletRequest request) {
        // TODO 得改成用redis
        String sessionCode = (String) redisCommands.get(StaticValue.USER_CODE, user.getUserTel());
        if (user.getCode() == null || sessionCode == null) {
            return Result.fail("请发送验证码");
        }
        if (!user.getCode().equals(sessionCode)) {
            return Result.fail("登录失败!验证码错误");
        }
        UserPO checkUser = new UserPO();
        checkUser.setUserTel(user.getUserTel());
        checkUser.setUserPassword(user.getUserPassword());
        UserPO loginUser = userService.checkLogin(checkUser);
        if (loginUser == null) {
            return Result.fail("登录失败!账户不存在");
        }
        if (loginUser.getUserState().equals(Constant.USER_STATUS.forbidden.getIndex())) {
            return Result.fail("登录失败!用户被禁用");
        }
        UserVO userVO = new UserVO();
        userVO.setUserPO(loginUser);
        userVO.setToken(TokenUtil.getToken(loginUser));
        return Result.success(userVO);
    }

    /**
     * 注册
     *
     * @param user 用户类
     * @return
     */
    @ApiOperation(value = "用户注册-请先发送手机验证码，并保证手机号不重复")
    @PostMapping(path = "/signUp")
    public Result signUpUser(@RequestBody SignUserAO user, HttpServletRequest request) {
        String sessionCode = (String) redisCommands.get(StaticValue.USER_CODE, user.getUserTel());
        if (user.getCode() == null || sessionCode == null) {
            return Result.fail("请发送验证码");
        }
        if (sessionCode.equals(user.getCode())) {
            UserPO signUser = new UserPO();
            BeanUtils.copyProperties(user, signUser);
            UserPO checkuser = userService.checkLogin(signUser);
            if (checkuser == null) {
                userService.signUpUser(signUser);
                return Result.success();
            } else {
                return Result.fail("该手机已注册账号");
            }

        } else {
            return Result.fail("验证码出错");
        }
    }

    /**
     * 获取手机验证码
     *
     * @param userTel 手机号
     * @return
     */
    @GetMapping("getCode")
    @ApiOperation(value = "获取手机验证码")
    public void loginCode(@RequestParam String userTel, HttpServletRequest request) {
        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appSecret = "b32ef9e2-f91e-4d8d-9410-d726fff18b95";
        String appId = "104131";
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        Map<String, String> params = new HashMap<String, String>();
        String code = CodeUtils.getCode(4, CodeUtils.NUMBER_CODE);
        // 放入redis, 10分钟后过期
        Boolean set = redisCommands.set(StaticValue.USER_CODE, userTel, code, 10 * 60);
        System.out.println("set:" + set);
        params.put("message", "验证码为: " + code + "；10分钟后过期。");
        params.put("number", userTel);
        try {
            client.send(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改用户信息
     *
     * @param user 用户类
     * @return
     */
    @ApiOperation(value = "【登录状态】修改用户信息，1.修改用户基本信息 2.修改默认地址 3.修改密码")
    @PostMapping(path = "/updateUser")
    public Result updateUser(@RequestBody UserPO user, HttpServletRequest request) {
        user.setUserId(TokenUtil.getUserIdAndUserTelOfToken().getUserId());
        Integer num = userService.updateUser(user);
        if (user.getUserId() == null) {
            return Result.fail("缺少用户id");
        }
        if (num > 0) {
            return Result.success();
        }
        return Result.fail("修改失败");
    }

    /**
     * 用户名获取用户所有信息
     *
     * @return
     */
    @ApiOperation(value = "一个用户", notes = "【登录状态】用户id获取用户所有信息")
    @GetMapping(path = "/getUserInfo")
    public Result getUserInfo() {

        UserPO user = userService.getUserInfo();
        if (user != null) {
            return Result.success(user);
        }
        return Result.success();
    }

}
