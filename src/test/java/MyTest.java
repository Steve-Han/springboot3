import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@SpringBootTest(classes = {MyConfiguration.class, HelloService.class})
public class MyTest {

/*    @Autowired
    private MyConfiguration myConfiguration;
    @Autowired
    private HelloService helloService;*/

    @Test
    public void test01() throws Exception {
        List<Double> collect = Stream.generate(Math::random).limit(10).toList();

        System.out.println(collect);
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
