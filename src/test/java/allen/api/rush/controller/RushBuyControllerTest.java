package allen.api.rush.controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
@SpringBootTest
public class RushBuyControllerTest {
    private Logger logger = LoggerFactory.getLogger(RushBuyController.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Test
    public void contextLoads() {

    }
}