import java.util.function.BiConsumer;

public class BiFunctionTest {

    private Integer i =2;

    /**
     * 特定类的任意对象的方法引用
     * @param integer
     * @return
     */
    public Boolean getBoolean(Integer integer) {
        return i == integer;
    }

    public static void main(String[] args){
        BiFunctionTest.invokeMethodOfAnyObject(BiFunctionTest::test);
    }

    static void invokeMethodOfAnyObject(BiConsumer<BiFunctionTest, String> consumer){
        consumer.accept(new BiFunctionTest(), "ABC123");
    }


    public void test(String str){
        System.out.println(str);
    }

}
