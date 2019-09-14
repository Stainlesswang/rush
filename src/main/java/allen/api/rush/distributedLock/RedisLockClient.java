package allen.api.rush.distributedLock;

import allen.api.rush.distributedLock.annotation.DistributedLockable;
import allen.api.rush.distributedLock.handler.LockHandler;
import allen.api.rush.distributedLock.lock.DistributedLock;
import allen.api.rush.distributedLock.lock.RedisDistributedLock;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author AllenWong
 * @date 2019/9/14 2:10 PM
 */
@Component
public class RedisLockClient {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public <T> T tryLock(String key, LockHandler<T> handler, long timeout, boolean autoUnlock, int retries, long waitingTime,
                         Class<? extends RuntimeException> onFailure) throws Throwable {
        try (DistributedLock lock = this.acquire(key, timeout, retries, waitingTime)) {
            if (lock != null) {
                LOGGER.info("get lock success, key: {}", key);
                return handler.handle();
            }
            LOGGER.info("get lock fail, key: {}", key);
            if (null != onFailure) {
                throw onFailure.newInstance();
            }
            return null;
        }
    }

    /**
     *
     * 获取redis分布式锁
     * @author AllenWong
     * @date 2019-09-14 14:10:21
     * @param key
     * @param timeout 超时时间  毫秒
     * @param retries 重试次数,到达该重试次数退出获取
     * @param waitingTime 每次重试的时候休眠等待时间
     * @return allen.api.rush.distributedLock.lock.DistributedLock 返回一个key,value以及操作的redisTemplate封装的对象
     */
    public DistributedLock acquire(String key, long timeout, int retries, long waitingTime) throws InterruptedException {
        //首先根据 随机数+时间戳 获取当前的value, 尽量保证不重复即可, 用来做删除时的凭证
        String value=RandomStringUtils.randomAlphanumeric(4)+System.currentTimeMillis();
        do {
            Boolean result
                    = stringRedisTemplate.opsForValue().setIfAbsent(key,value,timeout,TimeUnit.MILLISECONDS);
            if (null!=result&&result) {
                return new RedisDistributedLock(stringRedisTemplate, key, value);
            }
            if (retries > NumberUtils.INTEGER_ZERO) {
                TimeUnit.MILLISECONDS.sleep(waitingTime);
            }
            if(Thread.currentThread().isInterrupted()){
                break;
            }
        } while (retries-- > NumberUtils.INTEGER_ZERO);

        return null;
    }
}
