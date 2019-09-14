package allen.api.rush.service;

import allen.api.rush.model.AnyObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * @author AllenWong
 * @date 2019/9/14 9:10 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedLockableServiceTest {

    private static final ExecutorService POOL = Executors.newFixedThreadPool(32);

    @Resource
    private DistributedLockableService distributedLockableService;

    /**
     * @author piaoruiqing
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testDistributedLockable() throws InterruptedException, ExecutionException {


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

        assertEquals(1, results.size());
    }

    /**
     * @author piaoruiqing
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testOnFailure() throws InterruptedException, ExecutionException {

        AnyObject anyObject =
                new AnyObject(ThreadLocalRandom.current().nextLong(),RandomStringUtils.random(3));
        List<Exception> exceptions = new ArrayList<>();
        Future<?> future1 = POOL.submit(() -> {
            try {
                distributedLockableService.distributedLockableOnFaiFailure(anyObject, "str", "str", 20L);
            } catch (Exception e) {
                exceptions.add(e);
            }
        });
        Future<?> future2 = POOL.submit(() -> {
            try {
                distributedLockableService.distributedLockableOnFaiFailure(anyObject, "str", "str", 20L);
            } catch (Exception e) {
                exceptions.add(e);
            }
        });
        future1.get();
        future2.get();
        assertEquals(exceptions.size(), 1);
        assertEquals(exceptions.get(0).getClass(), RuntimeException.class);
    }
}