import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.google.common.math.LongMath;
import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static java.math.RoundingMode.FLOOR;

//@SpringBootTest(classes = {MyConfiguration.class, HelloService.class})
public class MyTest {

/*    @Autowired
    private MyConfiguration myConfiguration;
    @Autowired
    private HelloService helloService;*/

    @Test
    public void test01() throws Exception {
        ArrayList<String> stringList = Lists.newArrayList();
        ArrayList<Integer> intList = Lists.newArrayList();
        // 认为stringList和intList的类型是一样的。这就是所谓的泛型类型擦除, 泛型String和Integer被檫除了。
        //System.out.println(stringList.getClass().isAssignableFrom(intList.getClass()));

        // 定义了一个空的匿名类
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {
        };
        // TypeToken解析出了泛型参数的具体类型
        TypeToken<?> genericTypeToken = typeToken.resolveType(ArrayList.class.getTypeParameters()[0]);
        System.out.println(genericTypeToken.getType());

    }

    public List<String> getDatesBetween(String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);
        return startDate.datesUntil(endDate.plusDays(1))
                .map(LocalDate::toString)
                .collect(Collectors.toList());
    }


}
