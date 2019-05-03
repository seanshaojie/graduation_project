package com.example.advice;


import com.example.annotation.LogAnnotation;
import com.example.model.SysLogs;
import com.example.service.SysLogService;
import com.example.utils.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 统一日志处理
 *
 */
@Aspect//标注增强处理类（切面类）
@Component
public class LogAdvice {

    @Autowired
    private SysLogService logService;

    @Around(value = "@annotation(com.example.annotation.LogAnnotation)")
    public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
        SysLogs sysLogs = new SysLogs();
        sysLogs.setUser(UserUtil.getLoginUser()); // 设置当前登录用户
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String module = null;
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        module = logAnnotation.module();
        if (StringUtils.isEmpty(module)) {
            ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                module = apiOperation.value();
            }
        }

        if (StringUtils.isEmpty(module)) {
            throw new RuntimeException("没有指定日志module");
        }
        sysLogs.setModule(module);

        try {
            Object object = joinPoint.proceed();
            sysLogs.setFlag(true);

            return object;
        } catch (Exception e) {
            sysLogs.setFlag(false);
            sysLogs.setRemark(e.getMessage());
            throw e;
        } finally {
            if (sysLogs.getUser() != null) {
                logService.save(sysLogs);
            }
        }

    }
}
