package allen.api.rush.controller;

import allen.api.rush.distributedLock.RedisLockClient;
import allen.api.rush.distributedLock.annotation.DistributedLockable;
import allen.api.rush.model.AnyObject;
import allen.api.rush.service.DistributedLockableService;
import allen.api.rush.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 实现一个抢购的实例
 * @author wangjianqiang
 */
@RestController
public class RushBuyController {

    private Logger logger = LoggerFactory.getLogger(RushBuyController.class);
    private static final ExecutorService POOL = Executors.newFixedThreadPool(16);

    private final
    DistributedLockableService distributedLockableService;
    @Resource(name = "vip")
    UserService userService;

    public RushBuyController(DistributedLockableService distributedLockableService) {
        this.distributedLockableService = distributedLockableService;
    }

    @RequestMapping(value = "/lockAndRun",method = RequestMethod.GET)
    public String reidsLockTest(HttpServletRequest request){
        AnyObject anyObject = new AnyObject(10L,"Allen");
        //只有获取锁的方法才可以执行
        Long future = distributedLockableService.distributedLockableOnFaiFailure(anyObject, "str", "str", 1L);
        return future.toString();
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(HttpServletRequest request) throws Throwable {
        AnyObject anyObject = new AnyObject(ThreadLocalRandom.current().nextLong(),RandomStringUtils.random(3));

        List<Future<Long>> list = new ArrayList<>();

        for (int index = 0; index < 10; index++) {
            list.add(POOL.submit(() ->
                    distributedLockableService.distributedLockableOnFaiFailure(anyObject, "str", "str", 50L)
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


    @RequestMapping(value = "/test2",method = RequestMethod.GET)
    public String fuckme(){
        userService.test();
        return "test Success!";
    }
}
