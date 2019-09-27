package allen.api.rush.service;

/**
 * @author AllenWong
 * @date 2019/9/27 2:19 PM
 * 该类主要是看当一个接口有多个资源实现的时候,Spring在注入的时候有两种方式实现
 * 总结：
 * 1、@Autowired 是通过 byType 的方式去注入的， 使用该注解，要求接口只能有一个实现类。
 * 2、@Resource 可以通过 byName 和 byType的方式注入， 默认先按 byName的方式进行匹配，如果匹配不到，再按 byType的方式进行匹配。
 * 3、@Qualifier 注解可以按名称注入， 但是注意是 类名。
 */
public interface UserService {
    public void test();
}
