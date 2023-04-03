package cn.lijunyi.logtracing.config.factory;

import cn.lijunyi.logtracing.config.autoconfigure.JsonParserProperties;
import cn.lijunyi.logtracing.service.ILogJsonParser;
import cn.lijunyi.logtracing.service.impl.FastJson2Parser;
import cn.lijunyi.logtracing.service.impl.JacksonParser;
import cn.lijunyi.logtracing.util.constant.JsonParserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @className: LogJsonParserFactory
 * @description: json工厂
 * @author: LiJunYi
 * @create: 2023/3/29 10:27
 */
@Component
public class LogJsonParserFactory {

    private final FastJson2Parser fastJson2Parser;
    private final JacksonParser jacksonParser;
    private final JsonParserProperties jsonParserProperties;

    @Autowired(required = false)
    public LogJsonParserFactory(FastJson2Parser fastJson2Parser, JacksonParser jacksonParser, JsonParserProperties jsonParserProperties) {
        this.fastJson2Parser = fastJson2Parser;
        this.jacksonParser = jacksonParser;
        this.jsonParserProperties = jsonParserProperties;
    }

    /**
     * 获取解析器
     *
     * @return {@link ILogJsonParser}
     */
    public ILogJsonParser getParser(){
        String jsonParserName = jsonParserProperties.getJsonParserName();
        if (JsonParserConstant.fastjson2.equals(jsonParserName)) {
            return fastJson2Parser;
        }else if (JsonParserConstant.jackson.equals(jsonParserName)) {
            return jacksonParser;
        }else {
            // 兜底json解析
            return jacksonParser;
        }
    }
}
