package cn.lijunyi.logtracing.aspect;


import cn.lijunyi.logtracing.aspect.annotation.SysLog;
import cn.lijunyi.logtracing.aspect.enums.HttpMethod;
import cn.lijunyi.logtracing.config.factory.LogJsonParserFactory;
import cn.lijunyi.logtracing.entity.BaseLogEntity;
import cn.lijunyi.logtracing.entity.BaseUserEntity;
import cn.lijunyi.logtracing.service.IOperationLogService;
import cn.lijunyi.logtracing.util.LogArrayUtils;
import cn.lijunyi.logtracing.util.LogIpUtils;
import cn.lijunyi.logtracing.util.LogServletUtils;
import cn.lijunyi.logtracing.util.LogStringUtils;
import cn.lijunyi.logtracing.util.constant.JsonParserConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @version 1.0.0
 * @className: LogAspect
 * @description: 操作日志切面处理类
 * @author: LiJunYi
 * @create: 2023/3/9 14:58
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    /**
     * 操作日志服务
     */
    private final IOperationLogService operationLogService;
    private final LogJsonParserFactory jsonParserFactory;

    public LogAspect(IOperationLogService operationLogService, LogJsonParserFactory jsonParserFactory) {
        this.operationLogService = operationLogService;
        this.jsonParserFactory = jsonParserFactory;
    }

    /**
     * 处理请求前执行
     *
     * @param joinPoint 连接点
     * @param sysLog    注解
     */
    @Before(value = "@annotation(sysLog)")
    public void doBeforeProcessing(JoinPoint joinPoint, SysLog sysLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行保存操作日志
     *
     * @param joinPoint 连接点
     * @param result    切入方法返回值
     */
    @AfterReturning(pointcut = "@annotation(sysLog)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, SysLog sysLog, Object result) {
        recordOperationLog(joinPoint, sysLog, null, result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 连接点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(sysLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, SysLog sysLog, Exception e) {
        recordOperationLog(joinPoint, sysLog, e, null);
    }

    /**
     * 设置系统日志基本参数数据
     *
     * @param joinPoint 连接点
     * @param sysLog    日志注解
     * @param e         异常
     * @param result    调用的方法返回结果
     */
    private void recordOperationLog(JoinPoint joinPoint, SysLog sysLog, Exception e, Object result) {
        try {
            BaseLogEntity baseLogEntity = new BaseLogEntity();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            // 获取自定义的系统用户信息
            BaseUserEntity user = operationLogService.getSysUserInfo();
            String operateIp = LogStringUtils.isEmpty(user.getOperateIp()) ? LogIpUtils.getIpAddr() : user.getOperateIp();
            Integer operatorType = Optional.ofNullable(user.getOperatorType()).orElse(sysLog.operatorType());
            // 操作模块
            baseLogEntity
                    // 请求url
                    .setRequestUrl(LogStringUtils.substring(LogServletUtils.getRequest().getRequestURI(), 0, 255))
                    // 设置方法名称
                    .setMethod(className + "." + methodName + "()")
                    // 设置请求方式
                    .setRequestMethod(LogServletUtils.getRequest().getMethod())
                    // 操作者ip地址
                    .setOperateIp(operateIp)
                    // 操作时间
                    .setOperateTime(new Date())
                    // 操作者id
                    .setOperateUserId(user.getOperateUserId())
                    // 操作者类别
                    .setOperatorType(operatorType)
                    // 操作者用户名
                    .setOperateUserName(user.getOperateUserName())
                    // 操作者部门名
                    .setOperateDeptName(user.getOperateDeptName())
                    // 先设置错误信息为空
                    .setErrorMsg(LogStringUtils.EMPTY);
            if (e != null) {
                // 异常信息
                baseLogEntity.setErrorMsg(LogStringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置日志注解上的信息
            getLogAnnotationInformation(joinPoint,sysLog,baseLogEntity,result);
            // 设置消耗时间
            baseLogEntity.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());
            // 保存日志
            operationLogService.recordOperateLog(baseLogEntity);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }


    /**
     * 获取日志注释信息
     *
     * @param joinPoint     连接点
     * @param sysLog        日志注解
     * @param result        调用的方法返回结果
     * @param baseLogEntity 日志对象
     */
    private void getLogAnnotationInformation(JoinPoint joinPoint, SysLog sysLog,BaseLogEntity baseLogEntity, Object result) {
        // 操作模块
        baseLogEntity.setOperateModule(sysLog.operateModule())
                // 业务类型
                .setBusinessType(sysLog.businessType())
                // 操作描述
                .setOperateDescription(sysLog.operateDescription());
        // 是否保存请求的参数
        if (sysLog.isSaveRequestData()) {
            setRequestValue(joinPoint, baseLogEntity,getExcludeProperties(sysLog.excludeParamNames()));
        }
        // 是否需要保存response
        if (sysLog.isSaveResponseData() && LogStringUtils.isNotNull(result))
        {
            baseLogEntity.setJsonResult(LogStringUtils.substring(jsonParserFactory.getParser().toJSONString(result,getExcludeProperties(sysLog.excludeParamNames())), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到baseLogEntity中
     *
     * @param joinPoint         连接点
     * @param baseLogEntity     日志基础对象
     * @param excludeParamNames 排除参数名称
     */
    private void setRequestValue(JoinPoint joinPoint, BaseLogEntity baseLogEntity, String[] excludeParamNames) {
        String requestMethod = baseLogEntity.getRequestMethod();
        Map<?, ?> paramsMap = LogServletUtils.getParamMap(LogServletUtils.getRequest());
        if (LogStringUtils.isEmpty(paramsMap) && (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))) {
            String params = argsArrayToString(joinPoint.getArgs(),excludeParamNames);
            baseLogEntity.setRequestParam(LogStringUtils.substring(params, 0, 2000));
        } else {
            baseLogEntity.setRequestParam(LogStringUtils.substring(jsonParserFactory.getParser().toJSONString(paramsMap,excludeParamNames), 0, 2000));
        }
    }

    /**
     * 参数拼装
     *
     * @param paramsArray       参数数组
     * @param excludeParamNames 排除参数名称
     * @return {@link String}
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (LogStringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        String jsonObj = jsonParserFactory.getParser().toJSONString(o, excludeParamNames);
                        params.append(jsonObj).append(" ");
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 获取忽略敏感属性
     *
     * @param excludeParamNames 排除参数名称
     * @return {@link String[]}
     */
    private String[] getExcludeProperties(String[] excludeParamNames){
        return LogArrayUtils.addAll(JsonParserConstant.EXCLUDE_PROPERTIES, excludeParamNames);
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
