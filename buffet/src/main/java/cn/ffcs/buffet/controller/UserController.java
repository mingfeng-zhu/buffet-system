package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.CodeUtils;
import cn.ffcs.buffet.model.po.UserAddress;
import cn.ffcs.buffet.model.po.UserPO;
import cn.ffcs.buffet.service.UserAddressService;
import cn.ffcs.buffet.service.UserService;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.Api;
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
     * 登录
     *
     * @param user 用户类
     * @return
     */
    @PostMapping(path = "/login")
    public Result userLogin(UserPO user,String code, HttpServletRequest request) {
        HttpSession session=request.getSession();
        String sessionCode= (String) session.getAttribute("code");
        if(code==null || sessionCode==null){
            return Result.fail("请发送验证码");
        }
        if(!code.equals(session.getAttribute("code"))){
            return Result.fail("登录失败!验证码错误");
        }
        UserPO loginUser=userService.checkLogin(user);
        if(loginUser==null){
            return Result.fail("登录失败!账户不存在");
        }
        if(loginUser.getUserState()==0){
            return Result.fail("登录失败!用户被禁用");
        }
        if(loginUser.getUserPassword().equals(user.getUserPassword())){
            session.setAttribute("user",loginUser);
            return Result.success(loginUser);
        }else{
            return Result.fail("登录失败!密码错误");
        }

    }

    /**
     * 注册
     *
     * @param user 用户类
     * @return
     */
    @RequestMapping(path = "/signUp")
    public Result signUpUser(UserPO user,String code,HttpServletRequest request) {
        HttpSession session=request.getSession();
        String sessionCode= (String) session.getAttribute("code");
        if(code==null || sessionCode==null){
            return Result.fail("请发送验证码");
        }
        if(session.getAttribute("code").equals(code)){
            UserPO checkuser=userService.checkLogin(user);
            if(checkuser==null){
                userService.signUpUser(user);
                return Result.success();
            }else{
                return Result.fail("该手机已注册账号");
            }

        }else{
            return Result.fail("验证码出错");
        }
    }

    /**
     * 获取手机验证码
     *
     * @param userTel 手机号
     * @return
     */
    @RequestMapping("getCode")
    public void loginCode(String userTel, HttpServletRequest request) {
        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appSecret = "b32ef9e2-f91e-4d8d-9410-d726fff18b95";
        String appId = "104131";
        HttpSession session=request.getSession();
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        Map<String, String> params = new HashMap<String, String>();
        String code= CodeUtils.getCode(4,CodeUtils.NUMBER_CODE);
        session.setAttribute("code",code);
        session.setAttribute("tel",userTel);
        params.put("message", "验证码为: " + code);
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
    @RequestMapping(path = "/updateUser")
    public Result updateUser(UserPO user,HttpServletRequest request) {
        Integer num=userService.updateUser(user);
        if(user.getUserId()==null){
            return  Result.fail("缺少用户id");
        }
        if(num>0){
            return Result.success();
        }
        return  Result.fail("修改失败");
    }

    /**
     * 新增用户送货地址
     *
     * @param userAddress 用户地址类
     * @return
     */
    @RequestMapping(path = "/addUserAddress")
    public Result addUserAddress(UserAddress userAddress, HttpServletRequest request) {
        Integer num=userAddressService.addUserAddress(userAddress);
        if(num>0){
            return Result.success();
        }
        return  Result.fail("新增失败");
    }

    /**
     * 查询当前用户的送货地址
     *
     * @param userId 用户ID
     * @return
     */
    @RequestMapping(path = "/selectUserAddress")
    public Result selectUserAddress(Integer userId, HttpServletRequest request) {
        List<UserAddress> list =userAddressService.selectUserAddress(userId);
        if(list!=null){
            return Result.success(list);
        }
        return  Result.fail("查询失败");
    }

    /**
     * 修改用户送货地址
     *
     * @param userAddress 用户地址类
     * @return
     */
    @RequestMapping(path = "/updateUserAddress")
    public Result updateUserAddress(UserAddress userAddress, HttpServletRequest request) {
        Integer num=userAddressService.updateUserAddress(userAddress);
        if(num>0){
            return Result.success();
        }
        return  Result.fail("修改失败");
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("exit")
    public Result exit(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

}
