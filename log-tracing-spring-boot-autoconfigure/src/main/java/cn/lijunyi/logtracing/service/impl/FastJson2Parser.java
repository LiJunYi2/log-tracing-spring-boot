package cn.lijunyi.logtracing.service.impl;

import cn.lijunyi.logtracing.filter.PropertyPreExcludeFilter;
import cn.lijunyi.logtracing.service.ILogJsonParser;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @className: FastJson2Parser
 * @description:
 * @author: LiJunYi
 * @create: 2023/3/28 16:15
 */
@Component
@ConditionalOnClass({com.alibaba.fastjson2.JSON.class})
public class FastJson2Parser implements ILogJsonParser {

    private static final Logger log = LoggerFactory.getLogger(FastJson2Parser.class);

    /**
     * Serialize Java Object to JSON
     *
     * @param o                 o
     * @param excludeParamNames 排除参数名称
     * @return {@link String}
     */
    @Override
    public String toJSONString(Object o, String[] excludeParamNames) {
        log.info("use FastJson2Parser Serialize Java Object to JSON");
        return JSON.toJSONString(o, excludePropertyPreFilter(excludeParamNames));
    }

    /**
     * 忽略敏感属性
     */
    public PropertyPreExcludeFilter excludePropertyPreFilter(String[] excludeParamNames) {
        return new PropertyPreExcludeFilter().addExcludes(excludeParamNames);
    }

}
