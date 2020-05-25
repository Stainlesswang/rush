package allen.api.rush.distributedLock.aspect;

import allen.api.rush.distributedLock.RedisLockClient;
import allen.api.rush.distributedLock.annotation.DistributedLockable;
import allen.api.rush.distributedLock.support.KeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * @author AllenWong
 * @date 2019/9/14 4:01 PM
 */
@Aspect
@Order(10)
public class DistributedLockableAspect implements KeyGenerator {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisLockClient redisLockClient;


    /**
     * {@link DistributedLockable}
     * @author AllenWong
     *
     */
    @Pointcut(value = "execution(* *(..)) && @annotation(allen.api.rush.distributedLock.annotation.DistributedLockable)")
    public void distributedLockable() {}

    /**
     * @author AllenWong
     *
     * @param joinPoint
     * @param lockable
     * @return
     * @throws Throwable
     */
    @Around(value = "distributedLockable() && @annotation(lockable)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLockable lockable) throws Throwable {

        long start = System.currentTimeMillis();
        final String key = this.generate(joinPoint, lockable.prefix(), lockable.argNames(), lockable.argsAssociated()).toString();

        Object result = redisLockClient.tryLock(
                key, joinPoint::proceed,//joinPoint::proceed 把这个方法作为一个方法传进去
                lockable.unit().toMillis(lockable.timeout()), lockable.autoUnlock(),
                lockable.retries(), lockable.unit().toMillis(lockable.waitingTime()),
                lockable.onFailure()
        );

        long end = System.currentTimeMillis();
        LOGGER.info("distributed lockable cost: {} ns", end - start);

        return result;
    }
}
