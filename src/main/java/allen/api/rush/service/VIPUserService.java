package allen.api.rush.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AllenWong
 * @date 2019/9/27 2:20 PM
 */
@Service(value = "vip")
@Slf4j
public class VIPUserService implements UserService {
    @Override
    public String test() {
        log.info("VIP User Service");
        HashMap<String, String> hashMap=new HashMap<String, String>(8);
        hashMap.put("1","allen");
        hashMap.put("2","jack");
        hashMap.put("3","tom");
        return JSON.toJSONString(hashMap);
    }
}
