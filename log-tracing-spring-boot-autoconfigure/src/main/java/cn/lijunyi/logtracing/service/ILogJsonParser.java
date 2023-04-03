package cn.lijunyi.logtracing.service;

/**
 * @version 1.0.0
 * @className: ILogJsonParser
 * @description: 日志JSON解析器
 * @author: LiJunYi
 * @create: 2023/3/28 16:12
 */
public interface ILogJsonParser {

    /**
     * 序列化json
     *
     * @param object            对象
     * @param excludeParamNames 排除参数名称
     * @return {@link String}
     */
    String toJSONString(Object object, String[] excludeParamNames);
}
