package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.Resource;
import org.example.dto.ShowingInfo;
import org.example.dto.TenantVisitData;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    public String restTest() {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.parseMediaType("application/json;charset=utf-8"));
        headers.setContentType(MediaType.parseMediaType("text/html;charset=utf-8"));
        //header("Content-type: text/html; charset=utf-8");

        //使用map装载参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "admin");

        //设置请求体
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RequestEntity<Void> requestEntity = RequestEntity.get("https://www.baidu.com")
                .accept(MediaType.valueOf("text/html;charset=utf-8"))
                .headers(headers).build();
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        //ResponseEntity<String> response = restTemplate.exchange("https://www.baidu.com", HttpMethod.GET, entity, String.class);
        String body = response.getBody();
        // System.out.println(entity.getBody());

        return body;
    }

    @Resource(name = "oneRabbitTemplate")
    private RabbitTemplate oneRabbitTemplate;

    public void sendToOneMessage() {
        String exchange = "ex.crm.takelook";
        String routingKey = "k.scm.house_showing_shanghai.ams";

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        TenantVisitData data = getData();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message info = new Message(gson.toJson(data).getBytes(), messageProperties);
        info.getMessageProperties().setMessageId(data.getTransmissionId());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("123");
        oneRabbitTemplate.convertAndSend(exchange, routingKey, info, correlationData);
    }

    public void sendToOneMessageCD() {
        String exchange = "ex.crm.takelook";
        String routingKey = "k.scm.house_showing_shanghai.ams";

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        TenantVisitData data = getData();

        data.setCityCode("CD");
        data.setSubBizId("2");
        ArrayList<ShowingInfo> list = new ArrayList<>();

        ShowingInfo showingInfo2 = new ShowingInfo();
        showingInfo2.setAmsHouseCode("FY1000001440"); //todo
        list.add(showingInfo2);

        data.setShowingsList(list);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message info = new Message(gson.toJson(data).getBytes(), messageProperties);
        info.getMessageProperties().setMessageId(data.getTransmissionId());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("123");
        oneRabbitTemplate.convertAndSend(exchange, routingKey, info, correlationData);
    }

    public TenantVisitData getData() {
        TenantVisitData data = new TenantVisitData();
        data.setCityCode("SH");
        data.setSubBizId("1");
        data.setInterfaceCode("CD01");
        data.setCreateTime(new Date(1662432991349L));
        data.setSex(1L);
        data.setLeadusersId(8231660L); //todo
        data.setLeadusersName("涂文友"); //todo
        data.setShowingsId(672253711L);
        data.setCustomerMobile("19802255780");
        data.setTransmissionId("CD011662432991560");
        data.setCustomerName("马郑州");
        data.setBeginTime(new Date(1662432119000L));
        data.setEndTime(new Date(1662432129009L));
        data.setClientId("620865949");
        data.setTransmissionTime(new Date(1662432991560L));
        data.setCustomerDemandId(10621126567L);
        data.setDemandType(1L);
        data.setLeadStatus(3L);
        data.setDemandTypeName("上海的需求");
        data.setTenantSourceCode("4");
        data.setOperation(0L);
        data.setBizType("CRM");

        ShowingInfo showingInfo = new ShowingInfo();
        showingInfo.setAmsRoomId(24243741l); //todo
        showingInfo.setAmsHouseCode("FY1000077548"); //todo
        ArrayList<ShowingInfo> list = new ArrayList<>();
        list.add(showingInfo);

        ShowingInfo showingInfo2 = new ShowingInfo();
        showingInfo2.setAmsRoomId(24243739l); //todo
        showingInfo2.setAmsHouseCode("FY1000077548"); //todo
        list.add(showingInfo2);

        data.setShowingsList(list);

        return data;
    }

    public String testRemoteCRM(String userId) throws Exception {
        String urlStr = "https://b-crm-rest.meethome.com.cn/api/appoint/queryList";

        /*CRMWorkTakeOrderListRequest request = new CRMWorkTakeOrderListRequest("CD", "1", "CD071703166239062",
               "2023-12-21 21:43", "CD07", userId, "", "", "1", "10");
        // header根据实际情况设置，没有就空着
        HttpHeaders headers = new HttpHeaders();
        *//*headers.add("AccessKey", "自定义的API访问key");
        headers.add("Content-Type", "application/json");*//*
        HttpEntity<?> requestEntity = new HttpEntity<>(request, headers);
        String body = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(body, Map.class);
        map.forEach((o, o2) -> {
            System.out.print(o);
            System.out.print(":");
            System.out.println(o2);
        });

        return body ;*/

        URL url = new URL("https://www.baidu.com");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.connect();
        for (Certificate certificate : connection.getServerCertificates()) {
            //第一个就是服务器本身证书，后续的是证书链上的其他证书
            X509Certificate x509Certificate = (X509Certificate) certificate;
            System.out.println(x509Certificate.getSubjectX500Principal());//颁发给
            System.out.println(x509Certificate.getIssuerX500Principal());//颁发者
            System.out.println(x509Certificate.getSigAlgName());//算法
            System.out.println(x509Certificate.getNotBefore());//有效期开始时间
            System.out.println(x509Certificate.getNotAfter());//有效期结束时间
        }
        connection.disconnect();

        return "";
    }

    public Map<String, Object> Obj2Map(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    public Object map2Obj(Map<String, Object> map, Class<?> clz) throws Exception {
        Object obj = clz.newInstance();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

}
