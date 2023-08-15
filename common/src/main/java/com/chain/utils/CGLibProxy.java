package com.chain.utils;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib动态代理
 * UserService UserService = (UserService) new CGLibProxy().createProxyObject(new UserServiceImpl());
 */
public class CGLibProxy implements MethodInterceptor {
    /**
     * 创建 CGLib 动态代理类实例
     */
    public Object createProxyObject(Object targetObject) {
        // 创建Enhancer对象，用于创建代理对象
        Enhancer enhancer = new Enhancer();
        // 设置目标类的类加载器
        enhancer.setClassLoader(targetObject.getClass().getClassLoader());
        // 设置代理目标类
        enhancer.setSuperclass(targetObject.getClass());
        // 设置方法拦截器
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }


    /**
     * 动态代理方法拦截器。每当代理对象的方法被调用时，都会进入该方法
     *
     * @param proxy       代理目标对象的代理对象，它是真实的代理对象。
     * @param method      被拦截的目标类的方法
     * @param args        执行目标类的方法的参数
     * @param methodProxy CGLib提供的用于调用目标方法的代理对象。通过这个MethodProxy对象，可以调用目标方法而不必使用Java的反射机制。
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //进行逻辑处理的函数
        if ("addUser".equals(method.getName())) {
            //检查权限
            checkPopedom();
        }
        //调用目标对象上对应的方法，并传入相应的参数。
        Object obj = methodProxy.invokeSuper(proxy, args);
        return obj;
    }

    private void checkPopedom() {
        System.out.println("检查权限：checkPopedom()!");
    }
}
