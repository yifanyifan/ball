package com.chain.feign;

import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalTransactionalAspect {
    private final static Logger logger = LoggerFactory.getLogger(GlobalTransactionalAspect.class);

    /**
     * 用于处理feign降级后无法触发seata的全局事务的回滚
     * 过程：拦截*FallbackFactory降级方法，只要进了这个方法就手动结束seata全局事务
     */
    @After("execution(* com.chain.feign..*FallbackFactory.*(..))")
    public void before(JoinPoint joinPoint) throws TransactionException {
        if (!StringUtils.isBlank(RootContext.getXID())) {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.info("\n===========>降级后进行全局事务手动回滚,class：" + className + "，method：" + methodName + "，全局事务XID：" + RootContext.getXID());
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }
    }
}