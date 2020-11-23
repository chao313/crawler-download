package demo.spring.boot.demospringboot.service.download.impl;


import demo.spring.boot.demospringboot.service.download.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 规范下载类
 */
@Slf4j
@Component
public class DefaultDownloadService extends DownloadService {

    @Override
    public String downloadPageSource(String url, String encoding) throws IOException {
        return IOUtils.toString(new URL(url), encoding);
    }
}
