package cn.lijunyi.logtracing.aspect.enums;

import java.util.function.Function;

/**
 * @version 1.0.0
 * @className: SensitiveStrategy
 * @description: 脱敏策略枚举类
 * @author: LiJunYi
 * @create: 2023/3/13 9:47
 */
public enum SensitiveStrategy {

    /**
     * 姓名
     */
    USERNAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),

    /**
     * 密码
     */
    PASSWORD(s -> s.replaceAll("\\d|\\w", "*")),

    /**
     * 手机号
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),

    /**
     * 邮箱
     * 例：脱敏后1****@163.com
     */
    EMAIL(s -> s.replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2")),

    /**
     * 身份证
     */
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2")),

    /**
     * 银行卡,只显示前六位和后四位
     */
    BANK_CARD(s -> s.replaceAll("(\\d{6})\\d{9}(\\d{4})", "$1****$2")),

    /**
     * 地址
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"));


    private final Function<String, String> desensitizer;

    SensitiveStrategy(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    public Function<String, String> desensitizer() {
        return desensitizer;
    }
}
