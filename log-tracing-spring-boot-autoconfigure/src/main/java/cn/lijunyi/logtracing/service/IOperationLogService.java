package cn.lijunyi.logtracing.service;


import cn.lijunyi.logtracing.entity.BaseLogEntity;
import cn.lijunyi.logtracing.entity.BaseUserEntity;

/**
 * @version 1.0.0
 * @className: IOperationLogService
 * @description: 日志预留接口
 * @author: LiJunYi
 * @create: 2023/3/9 15:00
 */
@FunctionalInterface
public interface IOperationLogService {

    /**
     * 获取到操作日志进行入库
     * @param baseLogEntity 日志对象
     */
    void recordOperateLog(BaseLogEntity baseLogEntity);

    /**
     * 获取系统用户信息
     *
     * @return {@link BaseUserEntity}
     */
    default BaseUserEntity getSysUserInfo() {
        return new BaseUserEntity(1,1L,"","","");
    }
}
