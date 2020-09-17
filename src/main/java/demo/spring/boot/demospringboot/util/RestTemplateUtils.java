package demo.spring.boot.demospringboot.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate 工具类
 */
public class RestTemplateUtils {

    public static String getToString(String uri, String cookieValue, String encoding) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList<>();
        cookies.add(cookieValue);       //在 header 中存入cookies
        headers.put(HttpHeaders.COOKIE, cookies);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> resEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        return resEntity.toString();
    }
}
