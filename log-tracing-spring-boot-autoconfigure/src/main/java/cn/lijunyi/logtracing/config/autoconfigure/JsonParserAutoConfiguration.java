package cn.lijunyi.logtracing.config.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0.0
 * @className: JsonParserAutoConfiguration
 * @description: JSON解析器自动注入
 * @author: LiJunYi
 * @create: 2023/3/30 16:25
 */
@Configuration
@ConditionalOnProperty(value = "log.tracing.json-parser-name")
@EnableConfigurationProperties(JsonParserProperties.class)
public class JsonParserAutoConfiguration {
}
