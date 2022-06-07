package com.yqy.bootes.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author bahsk
 * @createTime 2022-06-04 23:04
 * @description
 * @program: boot-es
 */
@Component
public class EsClient {

    @Value("${elasticsearch.rest.hosts}")         //读取ES主机+端口配置
    private String hosts;

    @Bean
    public RestHighLevelClient initSimpleClient(){
        //根据配置文件配置HttpHost数组
        HttpHost[] httpHosts = Arrays.stream(hosts.split(",")).map(
                host -> {
                    //分隔ES服务器的IP和端口
                    String[] hostParts = host.split(":");
                    String hostName = hostParts[0];
                    int port = Integer.parseInt(hostParts[1]);
                    return new HttpHost(hostName, port, HttpHost.DEFAULT_SCHEME_NAME);
                }).filter(Objects::nonNull).toArray(HttpHost[]::new);
        //构建客户端
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
