package cn.lijunyi.logtracing.filter;

import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;

/**
 * @version 1.0.0
 * @className: PropertyPreExcludeFilter
 * @description: FastJson2脱敏处理
 * @author: LiJunYi
 * @create: 2023/3/15 10:23
 */
public class PropertyPreExcludeFilter extends SimplePropertyPreFilter {

    public PropertyPreExcludeFilter()
    {
        // TODO document why this constructor is empty
    }

    public PropertyPreExcludeFilter addExcludes(String... filters)
    {
        for (String filter : filters) {
            this.getExcludes().add(filter);
        }
        return this;
    }
}
