package allen.api.rush.distributedLock.config;

import allen.api.rush.distributedLock.RedisLockClient;
import allen.api.rush.distributedLock.aspect.DistributedLockableAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author AllenWong
 * @date 2019/9/14 7:45 PM
 */
@Configuration
public class RedisConfig implements ImportAware {
    /**
     *
     * @author AllenWong
     * @date 2019-09-14 19:47:48 
     * @param 
     * @return allen.api.rush.distributedLock.RedisLockClient
     */ 
    @Bean
    public RedisLockClient getRedisLockClient(){
        return new RedisLockClient();
    }
    /**
     *
     * @author AllenWong
     * @date 2019-09-14 20:42:55
     * @param 
     * @return allen.api.rush.distributedLock.aspect.DistributedLockableAspect
     */ 
    @Bean
    public DistributedLockableAspect distributedLockableAspect() {
        return new DistributedLockableAspect();
    }



    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {

    }
}
