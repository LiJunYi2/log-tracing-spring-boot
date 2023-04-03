package cn.lijunyi.logtracing.config.sensitive;

import cn.lijunyi.logtracing.aspect.annotation.Sensitive;
import cn.lijunyi.logtracing.aspect.enums.SensitiveStrategy;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * @version 1.0.0
 * @className: SensitiveJsonSerializer
 * @description: 序列化注解自定义实现：
 * JsonSerializer<String>：指定String 类型，serialize()方法用于将修改后的数据载入
 * @author: LiJunYi
 * @create: 2023/3/13 10:01
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏策略枚举类
     */
    private SensitiveStrategy strategy;

    /**
     * 序列化
     *
     * @param value              值
     * @param generator          Json生成器
     * @param serializerProvider 序列化器
     * @throws IOException ioexception
     */
    @Override
    public void serialize(String value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeString(strategy.desensitizer().apply(value));
    }

    /**
     * 创建上下文
     *
     * @param serializerProvider 序列化器
     * @param beanProperty       bean属性
     * @return {@link JsonSerializer}<{@link ?}>
     * @throws JsonMappingException json映射异常
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Sensitive annotation = beanProperty.getAnnotation(Sensitive.class);
        if (Objects.nonNull(annotation)&& Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            this.strategy = annotation.strategy();
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
