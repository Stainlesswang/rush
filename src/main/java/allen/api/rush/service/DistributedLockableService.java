package allen.api.rush.service;

import allen.api.rush.distributedLock.annotation.DistributedLockable;
import allen.api.rush.model.AnyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author AllenWong
 * @date 2019/9/14 7:48 PM
 */
@Service
public class DistributedLockableService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * @author piaoruiqing
     *
     * @param anyObject
     * @param param1
     * @param param2
     * @param timeout
     * @return
     */
    @DistributedLockable(
            argNames = {"anyObject.id", "anyObject.name", "param1"},
            timeout = 20, unit = TimeUnit.SECONDS
    )
    public Long distributedLockable(AnyObject anyObject, String param1, Object param2, Long timeout) {

        try {
            TimeUnit.SECONDS.sleep(timeout);
            LOGGER.info("distributed-lockable: " + System.nanoTime());
        } catch (InterruptedException e) {
        }

        return System.nanoTime();
    }

    /**
     * @author piaoruiqing
     *
     * @param anyObject
     * @param param1
     * @param param2
     * @param timeout
     * @return
     */
    @DistributedLockable(
            argNames = {"anyObject.id", "anyObject.name", "param1"},
            timeout = 20, unit = TimeUnit.SECONDS,
            onFailure = RuntimeException.class
    )
    public Long distributedLockableOnFaiFailure(AnyObject anyObject, String param1, Object param2, Long timeout) {

        try {
            TimeUnit.SECONDS.sleep(timeout);
            LOGGER.info("distributed-lockable-on-failure: " + System.nanoTime());
        } catch (InterruptedException e) {
        }

        return System.nanoTime();
    }

}
