package cn.lijunyi.logtracing.service.impl;

import cn.lijunyi.logtracing.service.ILogJsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @className: JacksonParser
 * @description:
 * @author: LiJunYi
 * @create: 2023/3/28 16:16
 */
@Component
@ConditionalOnClass({com.fasterxml.jackson.databind.ObjectMapper.class})
public class JacksonParser implements ILogJsonParser {

    private static final Logger log = LoggerFactory.getLogger(JacksonParser.class);

    private ObjectMapper objectMapper;

    public JacksonParser(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    /**
     * Serialize Java Object to JSON
     *
     * @param o                 o
     * @param excludeParamNames 排除参数名称
     * @return {@link String}
     */
    @Override
    public String toJSONString(Object o, String[] excludeParamNames) {
        try {
            return getObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Serialize Java Object to JSON By jackson, exception is :{}" , e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public JacksonParser(){

    }

    public ObjectMapper getObjectMapper(){
        if (this.objectMapper == null){
            this.objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
