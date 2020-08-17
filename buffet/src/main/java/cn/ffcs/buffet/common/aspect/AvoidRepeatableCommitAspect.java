package cn.ffcs.buffet.common.aspect;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.enums.ExceptionEnum;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 避免重复提交AOP
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/21 10:29
 */
@Aspect
@Configuration
public class AvoidRepeatableCommitAspect {
    /**
     * 缓存，在3秒内最大并发10000
     */
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 设置并发级别为cpu核心数
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            // 设置初始容量为100
            .initialCapacity(100)
            // 最大缓存1000个  1000之后就会按照LRU最近少使用算法来移除缓存项
            .maximumSize(1000)
            // 设置缓存写后3秒后过期
            .expireAfterWrite(3, TimeUnit.SECONDS).build();

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AvoidRepeatableCommitAspect.class);

    @Pointcut("@annotation(cn.ffcs.buffet.common.annotation.AvoidRepeatableCommit)")
    public void controllerAspect() {
    }

    /**
     * aop拦截有AvoidRepeatableCommit的注释的controller
     *
     * @param pjp
     * @return
     */
    @Around("controllerAspect()")
    public Object interceptor(ProceedingJoinPoint pjp) {
        String key = getKey();
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) {
                LOGGER.info("key:" + key + ",重复提交请求");
                return Result.fail(ExceptionEnum.AVOID_REPEATABLE_COMMIT.getMsg(), ExceptionEnum.AVOID_REPEATABLE_COMMIT.getCode());
            }
            // 如果是第一次请求,就将 key 当前对象压入缓存中
            CACHES.put(key, key);
            ;
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        }
    }

    /**
     * key 的生成策略,ip + url
     *
     * @return 生成的key
     */
    private String getKey() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String keyExpress = ip + url;
        LOGGER.info("key:" + keyExpress);
        return keyExpress;
    }

}
