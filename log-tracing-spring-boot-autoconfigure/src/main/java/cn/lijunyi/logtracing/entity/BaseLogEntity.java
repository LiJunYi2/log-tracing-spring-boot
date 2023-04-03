package cn.lijunyi.logtracing.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0.0
 * @className: BaseLogEntity
 * @description: 日志基本对象
 * @author: LiJunYi
 * @create: 2023/3/9 14:27
 */
public class BaseLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 操作模块
     */
    private String operateModule;

    /**
     * 业务类型
     * 根据自己的业务自定义类型，例如:0=其它,1=新增,2=修改,3=删除......
     */
    private Integer businessType;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 操作者类别
     */
    private Integer operatorType;

    /**
     * 操作者id
     */
    private Long operateUserId;

    /**
     * 操作者用户名
     */
    private String operateUserName;

    /**
     * 操作者部门名
     */
    private String operateDeptName;

    /**
     * 操作者ip地址
     */
    private String operateIp;

    /**
     * 操作描述
     */
    private String operateDescription;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 消耗时间
     */
    private Long costTime;

    public Long getId() {
        return id;
    }

    public BaseLogEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOperateModule() {
        return operateModule;
    }

    public BaseLogEntity setOperateModule(String operateModule) {
        this.operateModule = operateModule;
        return this;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public BaseLogEntity setBusinessType(Integer businessType) {
        this.businessType = businessType;
        return this;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public BaseLogEntity setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public BaseLogEntity setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public BaseLogEntity setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public BaseLogEntity setRequestParam(String requestParam) {
        this.requestParam = requestParam;
        return this;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public BaseLogEntity setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
        return this;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public BaseLogEntity setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
        return this;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public BaseLogEntity setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
        return this;
    }

    public String getOperateDeptName() {
        return operateDeptName;
    }

    public BaseLogEntity setOperateDeptName(String operateDeptName) {
        this.operateDeptName = operateDeptName;
        return this;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public BaseLogEntity setOperateIp(String operateIp) {
        this.operateIp = operateIp;
        return this;
    }

    public String getOperateDescription() {
        return operateDescription;
    }

    public BaseLogEntity setOperateDescription(String operateDescription) {
        this.operateDescription = operateDescription;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public BaseLogEntity setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public BaseLogEntity setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
        return this;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public BaseLogEntity setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
        return this;
    }

    public Long getCostTime() {
        return costTime;
    }

    public BaseLogEntity setCostTime(Long costTime) {
        this.costTime = costTime;
        return this;
    }

    @Override
    public String toString() {
        return "BaseLogEntity{" +
                "id=" + id +
                ", operateModule='" + operateModule + '\'' +
                ", businessType=" + businessType +
                ", requestUrl='" + requestUrl + '\'' +
                ", method='" + method + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestParam='" + requestParam + '\'' +
                ", operatorType=" + operatorType +
                ", operateUserId=" + operateUserId +
                ", operateUserName='" + operateUserName + '\'' +
                ", operateDeptName='" + operateDeptName + '\'' +
                ", operateIp='" + operateIp + '\'' +
                ", operateDescription='" + operateDescription + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", jsonResult='" + jsonResult + '\'' +
                ", operateTime=" + operateTime +
                ", costTime=" + costTime +
                '}';
    }
}
