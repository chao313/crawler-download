package demo.spring.boot.demospringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.List;
import java.util.Map;

@Slf4j
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

    public static String getCookieByLogin(String uri, String name, String pwd) throws IOException {
        String result = "";
        URL url = new URL(uri);
        URLConnection conn = url.openConnection();
        if (conn instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            httpURLConnection.setRequestMethod("POST");
            String data = "username=" + name + "&password=" + pwd + "&loginsubmit=" + "+++%B5%C7+%C2%BC+++"; //请求体的内容
            //设置头信息
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", data.length() + "");
            //四 ☆☆☆☆☆☆☆ 把我们组拼好的数据提交给服务器  以流的形式提交
            conn.setDoOutput(true);// 设置一个标记 允许输出
            conn.getOutputStream().write(data.getBytes());
            //(5)获取服务器返回的状态码
            int code = httpURLConnection.getResponseCode(); //200  代表获取服务器资源全部成功  206请求部分资源
            if (code == 200) {
                //(6)获取服务器返回的数据  以流的形式返回
                InputStream inputStream = conn.getInputStream();
                //(6.1)把inputstream 转换成 string
                String content = IOUtils.toString(inputStream, "GB2312");
                result = httpURLConnection.getHeaderField("Set-Cookie");
            }
        }
        return result;
    }


}
