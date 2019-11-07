package allen.api.rush.controller;

import allen.api.rush.cache.CacheHandler;
import allen.api.rush.distributedLock.RedisLockClient;
import allen.api.rush.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wangjianqiang
 */
@RestController
@Slf4j
public class CacheController {
    @Resource(name = "vip")
    UserService userService;
    @Resource
    private RedisLockClient redisLockClient;

    @RequestMapping(value = "/cache",method = RequestMethod.GET)
    public String cache(){
        CacheHandler cacheHandler=new CacheHandler(userService,redisLockClient);
        UserService proxyUserService= (UserService) cacheHandler.getproxy();
        return proxyUserService.test();
    }
}
