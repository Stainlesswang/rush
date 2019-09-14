package allen.api.rush.distributedLock.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author AllenWong
 * @date 2019/9/11 3:46 PM
 */
public abstract class DistributedLock implements AutoCloseable{

    private final Logger LOG=LoggerFactory.getLogger(DistributedLock.class);

    /**
     * 虚拟方法,释放锁的方法
     */
    abstract public void release();

    @Override
    public void close() {
        LOG.debug("distributed lock close , {}", this.toString());
        this.release();
    }
}