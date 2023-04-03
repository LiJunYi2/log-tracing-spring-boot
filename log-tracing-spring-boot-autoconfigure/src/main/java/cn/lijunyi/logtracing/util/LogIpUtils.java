package cn.lijunyi.logtracing.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0.0
 * @className: LogIpUtils
 * @description: IP工具类
 * @author: LiJunYi
 * @create: 2023/3/9 16:01
 */
public class LogIpUtils {

    private static final String COMMA = ",";
    private static final String IP = "127.0.0.1";
    private static final String LOCAL_IP = "0:0:0:0:0:0:0:1";

    /**
     * x-forwarded-for是识别通过HTTP代理或负载均衡方式连接到Web服务器的客户端
     * 最原始的IP地址的HTTP请求头字段
     */
    private static final String HEADER = "x-forwarded-for";

    private static final String UNKNOWN = "unknown";

    /**
     * 经过apache http服务器的请求才会有
     * 用apache http做代理时一般会加上Proxy-Client-IP请求头
     * 而WL-Proxy-Client-IP是它的weblogic插件加上的头
     */
    private static final String WL_IP = "WL-Proxy-Client-IP";

    /**
     * 获取客户端IP
     *
     * @return IP地址
     */
    public static String getIpAddr()
    {
        return getIpAddr(LogServletUtils.getRequest());
    }

    /**
     * 获取客户端IP
     *
     * @param request 请求对象
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null)
        {
            return UNKNOWN;
        }
        String ip = request.getHeader(HEADER);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader(WL_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return LOCAL_IP.equals(ip) ? IP : getMultistageReverseProxyIp(ip);
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip)
    {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(COMMA) >= 1)
        {
            final String[] ips = ip.trim().split(COMMA);
            for (String subIp : ips)
            {
                if (!isUnknown(subIp))
                {
                    ip = subIp;
                    break;
                }
            }
        }
        return LogStringUtils.substring(ip, 0, 255);
    }

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString)
    {
        return LogStringUtils.isBlank(checkString) || UNKNOWN.equalsIgnoreCase(checkString);
    }
}
