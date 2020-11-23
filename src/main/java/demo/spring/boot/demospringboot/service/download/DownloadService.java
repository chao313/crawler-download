package demo.spring.boot.demospringboot.service.download;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * 规范下载类
 */
@Slf4j
@Component
public abstract class DownloadService {
    /**
     * 下载源码
     *
     * @param encoding 指定编码
     */
    public abstract String downloadPageSource(String url, String encoding) throws IOException;


    /**
     * 根据网络URL下载,下载的是bytes
     */
    public static byte[] downloadToBytesByUrl(String url) throws IOException {
        return IOUtils.toByteArray(new URL(url));
    }
}
