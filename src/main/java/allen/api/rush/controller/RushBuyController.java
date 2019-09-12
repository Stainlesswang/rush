package allen.api.rush.controller;

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

    private final
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RushBuyController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(HttpServletRequest request){
        redisTemplate.opsForValue().set("allen","1000");
        Boolean flag=redisTemplate.opsForValue().setIfAbsent("allen","1000");
        logger.info("setIfAbsent:{}",flag);

        ExecutorService executorService=Executors.newCachedThreadPool();
        for (int i = 0; i <1000 ; i++) {
            executorService.execute(()-> redisTemplate.opsForValue().decrement("allen",1));
        }
        logger.info("减1000次后的值为:{}",redisTemplate.opsForValue().get("allen"));

        logger.info("request In:"+request.getRequestURI());
        redisTemplate.opsForValue().setIfAbsent("allen","fuck",10,TimeUnit.SECONDS);
        return "hello world"+redisTemplate.opsForValue().get("allen");
    }
}
