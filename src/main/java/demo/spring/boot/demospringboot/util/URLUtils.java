package demo.spring.boot.demospringboot.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
        conn.setRequestProperty("Cookie", cookieValue);
        InputStream inputStream = conn.getInputStream();
        return IOUtils.toString(inputStream, encoding);
    }

}
