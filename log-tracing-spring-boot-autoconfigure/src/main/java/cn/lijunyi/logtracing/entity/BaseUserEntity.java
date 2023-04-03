package cn.lijunyi.logtracing.entity;

/**
 * @version 1.0.0
 * @className: BaseUserEntity
 * @description: 基本用户信息
 * @author: LiJunYi
 * @create: 2023/3/9 16:11
 */
public class BaseUserEntity {

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

    public Integer getOperatorType() {
        return operatorType;
    }

    public BaseUserEntity setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
        return this;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public BaseUserEntity setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
        return this;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public BaseUserEntity setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
        return this;
    }

    public String getOperateDeptName() {
        return operateDeptName;
    }

    public BaseUserEntity setOperateDeptName(String operateDeptName) {
        this.operateDeptName = operateDeptName;
        return this;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public BaseUserEntity setOperateIp(String operateIp) {
        this.operateIp = operateIp;
        return this;
    }

    @Override
    public String toString() {
        return "BaseUserEntity{" +
                "operatorType=" + operatorType +
                ", operateUserId=" + operateUserId +
                ", operateUserName='" + operateUserName + '\'' +
                ", operateDeptName='" + operateDeptName + '\'' +
                ", operateIp='" + operateIp + '\'' +
                '}';
    }

    public BaseUserEntity(Integer operatorType, Long operateUserId, String operateUserName, String operateDeptName, String operateIp) {
        this.operatorType = operatorType;
        this.operateUserId = operateUserId;
        this.operateUserName = operateUserName;
        this.operateDeptName = operateDeptName;
        this.operateIp = operateIp;
    }

    public BaseUserEntity() {
    }
}
