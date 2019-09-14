package allen.api.rush.controller;

import allen.api.rush.distributedLock.RedisLockClient;
import allen.api.rush.distributedLock.annotation.DistributedLockable;
import allen.api.rush.model.AnyObject;
import allen.api.rush.service.DistributedLockableService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 实现一个抢购的实例
 */
@RestController
public class RushBuyController {

    private Logger logger = LoggerFactory.getLogger(RushBuyController.class);
    private static final ExecutorService POOL = Executors.newFixedThreadPool(32);

    @Autowired
    DistributedLockableService distributedLockableService;


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(HttpServletRequest request) throws Throwable {
        AnyObject anyObject =
                new AnyObject(ThreadLocalRandom.current().nextLong(),RandomStringUtils.random(3));

        List<Future<Long>> list = new ArrayList<>();

        for (int index = 0; index < 100; index++) {
            list.add(POOL.submit(() ->
                    distributedLockableService.distributedLockable(anyObject, "str", "str", 20L)
            ));
        }

        Set<Long> results = new HashSet<>();
        for (Future<Long> future : list) {
            if (future.get() != null) {
                results.add(future.get());
            }
        }
        return results.toString();

    }
}
