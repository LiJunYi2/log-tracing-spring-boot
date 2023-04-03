package cn.lijunyi.logtracing.util.constant;

/**
 * @version 1.0.0
 * @className: JsonParserConstant
 * @description: json解析器常量
 * @author: LiJunYi
 * @create: 2023/3/30 14:38
 */
public class JsonParserConstant {

    /**
     * fastjson2
     */
    public static final String fastjson2 = "fastjson2";

    /**
     * jackson
     */
    public static final String jackson = "jackson";

    /**
     * 需要进行脱敏的属性
     */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "passWord", "oldPassword", "newPassword", "confirmPassword" };


}
