package cn.lijunyi.logtracing.config.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @version 1.0.0
 * @className: JsonParserProperties
 * @description: JSON解析器配置文件
 * @author: LiJunYi
 * @create: 2023/3/30 16:19
 */
@ConfigurationProperties(prefix = "log.tracing")
public class JsonParserProperties {

    /**
     * 启用的JSON解析器名称
     */
    private String jsonParserName;

    public String getJsonParserName() {
        return jsonParserName;
    }

    public JsonParserProperties setJsonParserName(String jsonParserName) {
        this.jsonParserName = jsonParserName;
        return this;
    }
}
