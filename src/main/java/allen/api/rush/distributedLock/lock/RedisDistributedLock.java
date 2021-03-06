package allen.api.rush.distributedLock.lock;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import java.util.Collections;
import java.util.List;

/**
 * 继承实现了自动关闭的分布式锁
 * @author AllenWong
 * @date 2019/9/14 1:41 PM
 */
public class RedisDistributedLock extends DistributedLock {

    private RedisOperations<String, String> operations;
    private String key;
    private String value;
    //比较并删除对应的redis key,使用Lua脚本语言执行,保证两个操作(比较值是否相同 删除key)的原子性
    private static final String COMPARE_AND_DELETE =
            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call('del',KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    public RedisDistributedLock(RedisOperations<String, String> operations, String key, String value) {
        this.operations = operations;
        this.key = key;
        this.value = value;
    }

    /**
     * 实现关闭方法,确保分布式锁创建后自动释放锁资源
     */
    @Override
    public void release() {
        List<String> keys = Collections.singletonList(key);
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(COMPARE_AND_DELETE);
        //设置Boolean类型的返回值
        redisScript.setResultType(Boolean.class);
        operations.execute(redisScript, keys,value);
    }

    @Override
    public String toString() {
        return "RedisDistributedLock [key=" + key + ", value=" + value + "]";
    }
}
