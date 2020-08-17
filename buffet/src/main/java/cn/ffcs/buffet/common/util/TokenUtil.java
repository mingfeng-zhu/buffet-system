package cn.ffcs.buffet.common.util;

import cn.ffcs.buffet.model.dto.UserDTO;
import cn.ffcs.buffet.model.po.UserPO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description: Token工具类
 * @Author: mingfeng.zhu
 * @Date: 2020/8/16 0:15
 */
public class TokenUtil {

    /**
     * token head名字
     */
    public static final String HEAD_NAME = "X-Token";

    /**
     * 用userTel获取token
     *
     * @param user
     * @return
     */
    public static String getToken(UserPO user) {
        Date start = new Date();
        //一天有效时间
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000 * 24;
        Date end = new Date(currentTime);
        String token = "";

        //用username
        token = JWT.create().withAudience(user.getUserId().toString(), user.getUserTel()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUserPassword()));
        return token;
    }


    /**
     * 用户从token中取出用户userId,userTel
     *
     * @return
     */
    public static UserDTO getUserIdAndUserTelOfToken() {
        // 从 http 请求头中取出 token
        if (null != getRequest()) {
            String token = getRequest().getHeader(HEAD_NAME);
            if(token == null){
                return null;
            }
            UserDTO userDTO  = new UserDTO();
            userDTO.setUserId(Integer.parseInt(JWT.decode(token).getAudience().get(0)));
            userDTO.setUserTel(JWT.decode(token).getAudience().get(1));
            return userDTO;
        }
        return null;
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
