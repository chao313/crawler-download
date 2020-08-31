/**
 * Copyright 2020 bejson.com
 */
package demo.spring.boot.demospringboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private String url;
    private String bisName;
    private String downLoadTime;
    private String criteriaId;
    private String hostName;
    private Collection<String> otherDownloadUrls = new ArrayList<>();//其他下载的url
}