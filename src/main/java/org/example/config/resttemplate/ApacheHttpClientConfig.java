package org.example.config.resttemplate;


import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HeaderElement;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpHost;
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
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
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