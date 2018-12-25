package com.aoptest.aoplog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static Map<String,String> mapping = new HashMap<>();
    static{
        mapping.put("getOwe","ASP001");
        mapping.put("getAcct","ASP002");
    }
    /**
     * 切入点表达式格式：execution([可见性]返回类型[声明类型].方法名(参数)[异常])，其中[]内的是可选的
     * *：匹配所有字符
     * ..：一般用于匹配多个包，多个参数
     * +：表示类及其子类
     * 运算符有：&&，||，！
     * execution:用于匹配子表达式
     * within:用于匹配连接点所在的Java类或者包
     * this:用于向通知方法中传入代理对象的引用  @Before("before()&&this(proxy)") public void beforeAdvice(JoinPoint point,Object proxy)
     * target:用于向通知方法中传入目标对象的引用
     * args：用于将参数传入到通知方法中
     * @within:用于匹配在类一级使用了参数确定的注解的类，其所有方法都将被匹配
     * @target：和@within的功能类似，但必须要指定注解接口的保留策略为RUNTIME
     * @args；传入连接点的对象对应的Java类必须被@args指定的Annotation注解标注
     * @annotation：匹配连接点被它参数指定的Annotation注解的方法。也就是说，所有被指定注解标注的方法都将匹配。
     * bean:通过受管Bean的名字来限定连接点所在的Bean
     */
    @Pointcut("execution(public * com.aoptest.service..*.*(..))")
    public void aspect(){}

    /**
     * 前置通知:在某个连接点之前执行的通知，除非抛出一个异常，否则这个通知不能阻止连接点之前的执行流程
     * @param joinPoint
     * @throws Throwable
     *//*
    @Before("aspect()")
    public void doBeforeAdvice(JoinPoint joinPoint) throws Throwable{
        //获取目标方法的参数信息
        Object[] objs = joinPoint.getArgs();
        //AOP代理类的信息
        joinPoint.getThis();
        //代理的目标对象
        joinPoint.getTarget();
        //通知签名
        Signature signature = joinPoint.getSignature();
        System.out.println("代理的是哪一个方法" + signature.getName());
        System.out.println("代理方法的映射" + mapping.get(signature.getName()));
        System.out.println("AOP代理类的名字" + signature.getDeclaringTypeName());
        System.out.println("AOP代理类的类信息" + signature.getDeclaringType());
        System.out.println("------------------" + ((String)objs[0]).toString() + 1);
    }

    *//**
     * 后置通知:在某个连接点之后执行的通知，通常在一个匹配的方法返回的时候执行（可以在后置通知中绑定返回值）
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值信息
     * 如果第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * @param joinPoint
     * @param resp
     *//*
    @AfterReturning(value="execution(public * com.aoptest.service..*.*(..))",returning="resp")
    public void doAfterAdvice(JoinPoint joinPoint,Object resp){
        Object[] objs = joinPoint.getArgs();
        System.out.println("后置通知中：" + ((String)objs[0]).toString());
        System.out.println("+++++++" + (String)resp.toString());
    }

    *//**
     * 后置异常通知：在方法抛出异常退出时执行的通知
     * throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行
     * 对于throwing对应的通知方法参数为Throwable类型将匹配任何异常
     *//*
    @AfterThrowing(value="execution(public * com.aoptest.service..*.*(..))",throwing = "exception")
    public void doAfterThrowingAdivce(JoinPoint joinPoint,Throwable exception){
        System.out.println(joinPoint.getSignature().getName());
        System.out.println("--------------发生异常");
    }
    @After(value="execution(public * com.aoptest.service..*.*(..))")
    public void doAfterAdvice(JoinPoint joinPoint){
        System.out.println("-------------After------------");
    }*/

    /**
     * 环绕通知：可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否要替换返回值
     * 环绕通知第一个参数必须是ProceedingJoinPoint类型
     * @param proceedingJoinPoint
     * @return
     */
    //@Around(value="aspect()")
    //public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
    //@Around(value="aspect()&&args(stre)")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint,String stre){
        System.out.println("环绕通知------------START");
        try {
            /*Signature signature = proceedingJoinPoint.getSignature();
            String serviceName = signature.getName();
            Method[] methods = signature.getDeclaringType().getDeclaredMethods();
            Class c = null;
            for(Method m : methods){
                if(m.getName().equals(serviceName)){
                    System.out.println("++++++++++++++++++++++++++");
                    c = m.getParameterTypes()[0];
                    System.out.println("-------------参数类型" + c);
                }
            }
            //Class[] reqClass = signature.getDeclaringType().getMethod(serviceName).getParameterTypes();

            Object[] args = proceedingJoinPoint.getArgs();
            System.out.println("-------------" + c.cast(args[0]).toString() + "==============");*/
            System.out.println("+++++++++++++" + stre);
            Object obj = proceedingJoinPoint.proceed();
            System.out.println("环绕通知------------END");
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Around(value="aspect()&&args(stre)")
    public Object doAroundAdvice1(ProceedingJoinPoint proceedingJoinPoint,String stre) throws Throwable{
        System.out.println("环绕通知111111------------START");
        try {
            System.out.println("+++++++++++++" + stre);
            Object obj = proceedingJoinPoint.proceed();
            System.out.println("环绕通知111111------------END");
            int i = 1/0;
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("0000000000000000000000000000000000000000000");
            throw new Exception("错误++++++++", e);
        }
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        Long l = new Date().getTime();
        System.out.println(uuid.toString() + l);
        /*Boolean a = true;
        System.out.println(a = true);
        System.out.println(a.equals(true));*/
        /*String [] keys = new String[]{};
        String[] rst = new String[]{"", "", ""};
        if (keys != null) {
            for(int i = 0; i < Math.min(keys.length, 3); ++i) {
                if (!StringUtils.isEmpty(keys[i])) {
                    rst[i] = keys[i];
                }
            }
        }*/
        /*Integer i = 1;
        System.out.println(i.equals(1));
        System.out.println(i == 1);*/
    }


































































}
