package cn.lijunyi.logtracing.aspect.annotation;

import cn.lijunyi.logtracing.aspect.enums.SensitiveStrategy;
import cn.lijunyi.logtracing.config.sensitive.SensitiveJsonSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏注解
 * @author LiJunYi
 * @date 2023/03/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Sensitive {

    /**
     * 脱敏策略
     *
     * @return {@link SensitiveStrategy}
     */
    SensitiveStrategy strategy();
}
