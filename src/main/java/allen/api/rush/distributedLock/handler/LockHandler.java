package allen.api.rush.distributedLock.handler;

/**
 * lock Handler
 * @author AllenWong
 * @date 2019/9/14 2:57 PM
 */
@FunctionalInterface
public interface LockHandler<T> {

    /**
     *
     * 具体函数重写方法
     * @author AllenWong
     * @date 2019-09-14 14:59:34
     * @param
     * @return T
     */
    T handle() throws Throwable;

}
