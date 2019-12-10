package allen.api.rush;

/**
 * @author AllenWong
 * @date 2019/12/10 8:40 PM
 */
public class AutoCloseTest implements AutoCloseable {
//    private AutoCloseTest(){}

    public static String connection;

    static {
        connection="this is my connection.";
        System.out.println("this is static method");
    }
    public String test(){
        return "test";
    }
    @Override
    public void close() throws Exception {
        System.out.println("oh I am closing  my dear!");
    }


    public static void main(String[] args) throws Exception {
        try (AutoCloseTest app = new AutoCloseTest()) {
            System.out.println("--执行main方法--");
        } catch (Exception e) {
            System.out.println("--exception--");
        } finally {
            System.out.println("--finally--");
        }
    }
}
