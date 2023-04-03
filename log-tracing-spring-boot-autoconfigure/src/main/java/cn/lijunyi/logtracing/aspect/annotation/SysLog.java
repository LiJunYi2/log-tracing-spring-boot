package cn.lijunyi.logtracing.aspect.annotation;

import java.lang.annotation.*;


/**
 * 系统日志注解
 * ElementType.METHOD：可注解在方法级别上
 * RetentionPolicy.RUNTIME：运行时
 * @author LiJunYi
 * @date 2023/03/09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 操作模块
     *
     * @return {@link String}
     */
    String operateModule() default "";

    /**
     * 业务操作类型
     * 例如：0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入
     */
    int businessType() default 0;

    /**
     * 操作者类别
     * 例如：0=其它,1=后台用户,2=手机端用户
     */
    int operatorType() default 0;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

    /**
     * 操作描述
     */
    String operateDescription() default "";

    /**
     * 排除指定的请求参数
     */
    public String[] excludeParamNames() default {};
}
