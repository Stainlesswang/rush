package allen.api.rush.distributedLock.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author AllenWong
 * @date 2019/9/14 3:44 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLockable {
    /** timeout of the lock */
    long timeout() default 5L;

    /** time unit */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

    /** number of retries */
    int retries() default 0;

    /** interval of each retry */
    long waitingTime() default 0L;

    /** key prefix */
    String prefix() default "";

    /** parameters that construct a key */
    String[] argNames() default {};

    /** construct a key with parameters */
    boolean argsAssociated() default true;

    /** whether unlock when completed */
    boolean autoUnlock() default true;

    /** throw an runtime exception while fail to get lock */
    Class<? extends RuntimeException> onFailure() default NoException.class;

    /** no exception */
    public static final class NoException extends RuntimeException {

        private static final long serialVersionUID = -7821936618527445658L;

    }
}
