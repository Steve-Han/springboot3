package org.example.config.resttemplate;


import cn.hutool.http.ssl.TrustAnyHostnameVerifier;
import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HeaderElement;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.message.BasicHeaderElementIterator;
import org.apache.hc.core5.util.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static org.example.config.resttemplate.HttpClientConfigConstants.DEFAULT_KEEP_ALIVE_TIME;
import static org.example.config.resttemplate.HttpClientConfigConstants.IDLE_CONNECTION_WAIT_TIME;
import static org.example.config.resttemplate.HttpClientConfigConstants.MAX_LOCALHOST_CONNECTIONS;
import static org.example.config.resttemplate.HttpClientConfigConstants.MAX_ROUTE_CONNECTIONS;
import static org.example.config.resttemplate.HttpClientConfigConstants.MAX_TOTAL_CONNECTIONS;

@Configuration
@EnableScheduling
public class ApacheHttpClientConfig {

    private final Logger LOG = LoggerFactory.getLogger(ApacheHttpClientConfig.class);

    @Bean
    public SSLConnectionSocketFactory createSSLConnSocketFactory(){
        // 创建TrustManager
        X509TrustManager xtm = new X509TrustManager(){
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public X509Certificate[] getAcceptedIssuers() { return null; }
        };
        // TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
        SSLContext ctx;
        try {
            ctx = SSLContext.getInstance("TLS");
            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[]{xtm}, null);
            return new SSLConnectionSocketFactory(ctx, new TrustAnyHostnameVerifier());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LOG.error("create SSLSF error", e);
        }
        return null;
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        /*Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                //.register("http", plainsf)
                //.register("https", sslsf)
                .register("https", createSSLConnSocketFactory())
                .build();*/
        //PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(registry);

        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

        // set total amount of connections across all HTTP routes
        poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);

        // set maximum amount of connections for each http route in pool
        poolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        // increase the amounts of connections if host is localhost
        HttpHost localhost = new HttpHost("http://localhost", 8080);
        poolingConnectionManager.setMaxPerRoute(new HttpRoute(localhost), MAX_LOCALHOST_CONNECTIONS);

        return poolingConnectionManager;
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (httpResponse, httpContext) -> {
            Iterator<Header> headerIterator = httpResponse.headerIterator(HttpHeaders.KEEP_ALIVE);
            BasicHeaderElementIterator elementIterator = new BasicHeaderElementIterator(headerIterator);

            while (elementIterator.hasNext()) {
                HeaderElement element = elementIterator.next();
                String param = element.getName();
                String value = element.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return TimeValue.ofDays(Long.parseLong(value) * 1000); // convert to ms
                }
            }

            return TimeValue.ofDays(DEFAULT_KEEP_ALIVE_TIME);
        };
    }

    @Bean
    public Runnable idleConnectionMonitor(PoolingHttpClientConnectionManager pool) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 20000)
            public void run() {
                // only if connection pool is initialised
                if (pool != null) {
                    pool.closeExpired();
                    pool.closeIdle(TimeValue.of(IDLE_CONNECTION_WAIT_TIME, TimeUnit.MILLISECONDS));

                    LOG.info("Idle connection monitor: Closing expired and idle connections");
                }
            }
        };
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("idleMonitor");
        scheduler.setPoolSize(5);
        return scheduler;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }

}