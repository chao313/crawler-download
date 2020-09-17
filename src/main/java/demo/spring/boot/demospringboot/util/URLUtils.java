package demo.spring.boot.demospringboot.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class URLUtils {

    public static byte[] toByteArray(String uri, String cookieValue) throws IOException {
        URL url = new URL(uri);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Cookie", cookieValue);
        InputStream inputStream = conn.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }

    public static String toString(String uri, String cookieValue, String encoding) throws IOException {
        URL url = new URL(uri);
        URLConnection conn = url.openConnection();
//        conn.setReadTimeout(3000);
        conn.setRequestProperty("Cookie", cookieValue);
        InputStream inputStream = conn.getInputStream();
        return IOUtils.toString(inputStream, encoding);
    }

    /**
     * 获取响应类型 application/octet-stream
     */
    public static String getContentType(String uri, String cookieValue) throws IOException {
        URL url = new URL(uri);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Cookie", cookieValue);
        String contentType = conn.getContentType();
        return contentType;
    }

    /**
     * 获取响应头
     */
    public static Map<String, List<String>> getHeaderFields(String uri, String cookieValue) throws IOException {
        URL url = new URL(uri);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Cookie", cookieValue);
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        return headerFields;
    }

    /**
     * 获取响应码
     */
    public static int getResponseCode(String uri, String cookieValue) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie", cookieValue);
        int responseCode = conn.getResponseCode();
        return responseCode;
    }


}
