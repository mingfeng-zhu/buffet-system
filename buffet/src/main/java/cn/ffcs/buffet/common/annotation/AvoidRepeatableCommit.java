package cn.ffcs.buffet.common.annotation;

import java.lang.annotation.*;

/**
 * 避免重复提交注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AvoidRepeatableCommit {
}
