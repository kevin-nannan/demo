package com.example.jpa.transaction.annocationSelf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 手写事务注解:
 * 3,扫包--定义一个事务扫包AOP
 * 4,拦截方法的时候,使用反射判断该方法是否加了事务注解,有就开启事务
 */
@Aspect
@Component
public class TransactionAspect {
    @Resource
    private TransactionUtils transactionUtils;
    private TransactionStatus transactionStatus;

    //异常通知
    @AfterThrowing("execution(* com.example.jpa.transaction.*.*(..))")
    public void afterThrowing() {
        if (transactionStatus != null) {
            System.out.println("---回滚事务---");
            transactionUtils.rollback();
        }
    }

    //环绕通知 在方法之前和之后处理事情
    @Around("execution(* com.example.jpa.transaction.*.*(..))")
    public void around(ProceedingJoinPoint pjp) throws Throwable {

        transactionStatus = begin(pjp);
        //调用目标代理对象方法
        pjp.proceed();

        commit(transactionStatus);

    }

    /**
     * 判断事务状态,提交事务
     *
     * @param transactionStatus
     */
    private void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null) {
            System.out.println("---提交事务---");
            transactionUtils.commit(transactionStatus);
        }
    }

    /**
     * 判断是否有@CkTransactional事务注解,有的话开启事务
     *
     * @param pjp
     * @return null或事务状态transactionStatus
     * @throws NoSuchMethodException
     */
    private TransactionStatus begin(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        //1,获取代理对象的方法
        CkTransactional transactional = getCkTransactional(pjp);
        transactionStatus = null;
        if (transactional != null) {
            //3,加了的话开始事务
            System.out.println("---开启事务---");
            transactionStatus = transactionUtils.begin();
        }
        return transactionStatus;
    }

    /**
     * 获取代理对象的方法,判断是否有@LpTransactional事务注解
     *
     * @param pjp
     * @return null或者LpTransactional对象
     * @throws NoSuchMethodException
     */
    private CkTransactional getCkTransactional(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        // 获取方法名称
        String methodName = pjp.getSignature().getName();
        // 获取目标对象
        Class<?> classTarget = pjp.getTarget().getClass();
        // 获取目标对象类型
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        // 获取目标对象方法
        Method objMethod = classTarget.getMethod(methodName, par);
        CkTransactional transactional = objMethod.getDeclaredAnnotation(CkTransactional.class);
        return transactional;
    }
}
