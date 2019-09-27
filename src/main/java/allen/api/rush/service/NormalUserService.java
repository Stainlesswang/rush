package allen.api.rush.service;

import org.springframework.stereotype.Service;

/**
 * @author AllenWong
 * @date 2019/9/27 2:20 PM
 */
@Service(value = "normal")
public class NormalUserService implements UserService {
    @Override
    public void test() {
        System.out.println("Normal User Service");
    }
}
