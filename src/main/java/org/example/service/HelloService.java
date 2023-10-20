package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HelloService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Transactional
    public List<Map<String, Object>> test111() throws Exception {

        String sql = """
                select * from DEPT where DEPTNO=1
                """;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        //List<Map<String, Object>> byNamedQuery = (List<Map<String, Object>>) hibernateTemplate.findByNamedQuery(sql);
        //修改数据
        String sql1 = """
                UPDATE DEPT SET LOC='123111' where DEPTNO = 1
                """;
        jdbcTemplate.execute(sql1);

        return maps;
    }

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

    public int throwErr () {
        int i = 1/0;

        return i;
    }

}
