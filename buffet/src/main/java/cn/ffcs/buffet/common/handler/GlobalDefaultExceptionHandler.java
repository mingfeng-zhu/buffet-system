package cn.ffcs.buffet.common.handler;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;

/**
 * @Description: 全局异常处理类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/21 17:29
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);


    /**
     * 自定义业务异常
     *
     * @param request 请求
     * @param e       异常
     * @return 错误结果
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result bizExceptionHandler(HttpServletRequest request, BusinessException e) {
        LOGGER.error(request.getRequestURI() + " 请求出现业务异常");
        LOGGER.error("业务异常信息：{}", e.getMsg());
        return Result.fail(e.getMsg(), e.getCode());
    }

    /**
     * 404异常
     *
     * @param request 请求
     * @param e       异常
     * @return 错误结果
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Result resolveException(HttpServletRequest request, Exception e) {
        LOGGER.error(request.getRequestURI() + " 请求未找到");
        LOGGER.error("错误信息：", e);
        return Result.fail(request.getRequestURI() + " 请求未找到");
    }

    /**
     * 绑定异常
     *
     * @param request 请求
     * @param e       异常
     * @return Result 错误结果
     */
    @ExceptionHandler(value = BindException.class)
    public Result bindException(HttpServletRequest request, BindException e) {
        LOGGER.error(request.getRequestURI() + " 请求参数异常");
        LOGGER.error("错误信息：", e);
        String message = e.getMessage();
        //抛出错误异常
        return Result.fail(message);
    }


    /**
     * 参数校验处理
     *
     * @param request 请求
     * @param e       参数校验异常
     * @return 校验结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result validationException(HttpServletRequest request, MethodArgumentNotValidException e) {
        LOGGER.error(request.getRequestURI() + " 请求参数异常");
        LOGGER.error("错误信息：", e);
        String message = e.getMessage();
        return Result.fail(message);
    }

    /**
     * 全局异常
     *
     * @param request 请求
     * @param e       异常
     * @return Result 错误结果
     */
    @ExceptionHandler(value = Exception.class)
    public Result exception(HttpServletRequest request, Exception e) {
        LOGGER.error(request.getRequestURI() + " 请求异常");
        LOGGER.error("未捕获异常，错误信息：", e);
        return Result.fail("系统错误");
    }

}
