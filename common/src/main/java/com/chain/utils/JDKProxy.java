package com.chain.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理类
 * UserService UserService = (UserService) new JDKProxy().newProxy(new UserServiceImpl());
 */
public class JDKProxy implements InvocationHandler {
    //需要代理的目标对象
    private Object targetObject;

    /**
     * 创建 JDK 动态代理类实例
     */
    public Object newProxy(Object targetObject) {
        // 将目标对象传入进行代理
        this.targetObject = targetObject;
        // JDK提供的用于创建代理对象的静态方法, 返回代理对象
        return Proxy.newProxyInstance(
                targetObject.getClass().getClassLoader(),   // 指定目标类的类加载
                targetObject.getClass().getInterfaces(),    // 代理需要实现的接口
                this    // 调用代理对象逻辑的拦截器，即InvocationHandler 实现类
        );
    }

    /**
     * 动态代理方法拦截器。每当代理对象的方法被调用时，都会进入该方法
     *
     * @param proxy  代理目标对象的代理对象，它是真实的代理对象。
     * @param method 被拦截的目标类的方法
     * @param args   执行目标类的方法的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //进行逻辑处理的函数
        checkPopedom();
        //调用目标对象上对应的方法，并传入相应的参数。
        Object ret = method.invoke(targetObject, args);
        //返回目标方法的执行结果。
        return ret;
    }

    /**
     * 检查权限
     */
    private void checkPopedom() {
        System.out.println("检查权限");
    }
}
