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
    public static String test(){
        return "test";
    }
    public static void test(String ss){
         ss="world";
    }

    public static void test(StringBuilder stringBuilder){
        stringBuilder.append("world");
    }
    @Override
    public void close() throws Exception {
        System.out.println("oh I am closing  my dear!");
    }


    public static void main(String[] args) throws Exception {
        String s="hello";
        test(s);
        System.out.println(s);

        StringBuilder builder=new StringBuilder("hello");
        test(builder);
        System.out.println(builder.toString());

        try (AutoCloseTest app = new AutoCloseTest()) {
            System.out.println("--执行main方法--");
        } catch (Exception e) {
            System.out.println("--exception--");
        } finally {
            System.out.println("--finally--");
        }
    }
}
