package cn.ffcs.buffet.common.interceptors;

import cn.ffcs.buffet.common.annotation.PassToken;
import cn.ffcs.buffet.common.enums.ExceptionEnum;
import cn.ffcs.buffet.common.exception.BusinessException;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.model.po.UserPO;
import cn.ffcs.buffet.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description: 拦截器
 * @Author: mingfeng.zhu
 * @Date: 2020/8/16 21:30
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    @Autowired
    private UserService userService;

    /**
     * 这个方法是在访问接口之前执行的
     * 我们只需要在这里写验证登陆状态的业务逻辑
     *  就可以在用户调用指定接口之前验证登陆状态了
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 http 请求头中取出 token
        String token = request.getHeader(TokenUtil.HEAD_NAME);
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
                }
            }else{
            // 执行认证
            if (token == null) {
                throw new BusinessException(ExceptionEnum.TOKEN_NOT_EXIST);
                }
            // 获取 token 中的 userTel
            String userTel;
            try {
                userTel = JWT.decode(token).getAudience().get(1);
                } catch (JWTDecodeException j) {
                throw new RuntimeException("401");
                }
            UserPO userPO = new UserPO();
            userPO.setUserTel(userTel);
            UserPO user = userService.checkLogin(userPO);
            if (user == null) {
                throw new BusinessException(ExceptionEnum.USER_NOT_EXIST);
                }
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
            try {
                jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                throw new BusinessException(ExceptionEnum.TOKEN_EXPIRED);
                }
            logger.info("验证通过");
            return true;
        }
        throw new Exception("No Access");
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
