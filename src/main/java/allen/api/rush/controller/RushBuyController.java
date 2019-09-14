package allen.api.rush.controller;

import allen.api.rush.distributedLock.RedisLockClient;
import allen.api.rush.distributedLock.annotation.DistributedLockable;
import allen.api.rush.model.AnyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;

/**
 * 实现一个抢购的实例
 */
@RestController
public class RushBuyController {

    private Logger logger = LoggerFactory.getLogger(RushBuyController.class);

    @Autowired
    RedisLockClient lockClient;

    private final
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RushBuyController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(HttpServletRequest request) throws Throwable {
        AnyObject object=lockClient.tryLock("allen", () -> {
            System.out.println("running");
            return new AnyObject(11L,"allen");
        }, 400000, true, 2, 1000, RuntimeException.class);
        return object.getName();
    }
}
