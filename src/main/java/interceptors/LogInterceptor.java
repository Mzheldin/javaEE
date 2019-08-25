package interceptors;


import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogInterceptor {
    @AroundInvoke
    public Object printLog(InvocationContext context) throws Exception {
        System.out.println("Метод: " + context.getMethod().getName());
        return context.proceed();
    }
}
