package allen.api.rush.cache;

import allen.api.rush.distributedLock.RedisLockClient;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * 模拟58一个加缓存的需求
 * 此类作为使用JDK的代理实现的AOP功能，
 * 为服务里边的获取数据方法加上缓存，任何此服务的获取服务方法都要走这个缓存器
 * @author wangjianqiang
 */
public class CacheHandler implements InvocationHandler {
    private Object target;
    private RedisLockClient redisLockClient;
    private static Logger log=LoggerFactory.getLogger(CacheHandler.class);

    /**
     * 带参数的构造函数
     * @param target
     */
    public CacheHandler(Object target,RedisLockClient redisLockClient){
        super();
        this.target=target;
        this.redisLockClient=redisLockClient;
    }
    CacheHandler(){

    }

    /**
     * 必须实现的代理类真正执行的方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO: 2019/11/6  在这里实现缓存的逻辑   result就是我们需要缓存的对象
//        String REDIS_KEY_BACK="rush_cache_back";

        String redisKey ="rush_cache";
        StringRedisTemplate stringRedisTemplate=redisLockClient.getStringRedisTemplate();
        String jsonStr=stringRedisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isNotEmpty(jsonStr)){
            log.info("命中缓存：{}---- 结果：{}", redisKey,jsonStr);
            return jsonStr;
        }
        log.info("entry the proxy invoke proxy：{}---- method：{}---- args：{}----",proxy,method,args);
        Object result= method.invoke(target,args);
        stringRedisTemplate.opsForValue().set(redisKey, JSON.toJSONString(result),10L, TimeUnit.SECONDS);
        return JSON.toJSONString(result);
    }

    /**
     * 获取该Handler所对应的代理对象
     * @return 返回的是经过代理的对象
     */
    public Object getproxy(){
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),target.getClass().getInterfaces(),this);
    }
}
