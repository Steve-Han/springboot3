import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.BiFunction;

//@SpringBootTest(classes = {MyConfiguration.class, HelloService.class})
public class MyTest {

/*    @Autowired
    private MyConfiguration myConfiguration;
    @Autowired
    private HelloService helloService;*/

    @Test
    public void test01() throws Exception {

    }

    public void funTest(Integer i, BiFunction<BiFunctionTest, Integer, Boolean> function) {
        BiFunctionTest biTest = new BiFunctionTest();
        Boolean apply = function.apply(biTest, i);
        System.out.println(apply);
    }

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
