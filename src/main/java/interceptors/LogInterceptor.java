package interceptors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogInterceptor {

    private Logger log = LoggerFactory.getLogger("interceptLog");

    @AroundInvoke
    public Object printLog(InvocationContext context) throws Exception {
        log.info("Метод: " + context.getMethod().getName());
        return context.proceed();
    }
}
