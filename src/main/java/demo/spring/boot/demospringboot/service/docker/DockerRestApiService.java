package demo.spring.boot.demospringboot.service.docker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import demo.spring.boot.demospringboot.config.StartConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class DockerRestApiService {

    @Autowired
    private StartConfig startConfig;

    /**
     * 获取全部的镜像
     */
    public Collection<String> getAllImages() throws IOException {
        Set<String> result = new HashSet<>();
        String retStr = IOUtils.toString(new URL(startConfig.getLocalHostDockerApi() + "/images/json"), "UTF-8");
        JSONArray retJsonArray = JSON.parseArray(retStr);
        retJsonArray.forEach(obj -> {
            if (obj instanceof JSONObject) {
                JSONObject tmp = ((JSONObject) obj);
                if (tmp.containsKey("RepoTags")) {
                    JSONArray namesArray = tmp.getJSONArray("RepoTags");
                    namesArray.forEach(name -> {
                        result.add(name.toString());
                    });
                }
            }
        });
        return result;

    }

    /**
     * 获取全部的容器 注意这里的名称前面加上了/
     */
    public Collection<String> getAllContainers() throws IOException {
        Set<String> result = new HashSet<>();
        String retStr = IOUtils.toString(new URL(startConfig.getLocalHostDockerApi() + "/containers/json?all=true"), "UTF-8");
        JSONArray retJsonArray = JSON.parseArray(retStr);
        retJsonArray.forEach(obj -> {
            if (obj instanceof JSONObject) {
                JSONObject tmp = ((JSONObject) obj);
                if (tmp.containsKey("Names")) {
                    JSONArray namesArray = tmp.getJSONArray("Names");
                    namesArray.forEach(name -> {
                        result.add(name.toString());
                    });
                }
            }
        });
        return result;
    }

    /**
     * 获取运行的容器 注意这里的名称前面加上了/
     */
    public Collection<String> getRunningContainers() throws IOException {
        Set<String> result = new HashSet<>();
        String retStr = IOUtils.toString(new URL(startConfig.getLocalHostDockerApi() + "/containers/json"), "UTF-8");
        JSONArray retJsonArray = JSON.parseArray(retStr);
        retJsonArray.forEach(obj -> {
            if (obj instanceof JSONObject) {
                JSONObject tmp = ((JSONObject) obj);
                if (tmp.containsKey("Names")) {
                    JSONArray namesArray = tmp.getJSONArray("Names");
                    namesArray.forEach(name -> {
                        result.add(name.toString());
                    });
                }
            }
        });
        return result;
    }

    /**
     * 获取容器名称到id的映射
     */
    public Map<String, String> getAllContainersMap() throws IOException {
        Map<String, String> result = new HashMap<>();
        String retStr = IOUtils.toString(new URL(startConfig.getLocalHostDockerApi() + "/containers/json?all=true"), "UTF-8");
        JSONArray retJsonArray = JSON.parseArray(retStr);
        retJsonArray.forEach(obj -> {
            if (obj instanceof JSONObject) {
                JSONObject tmp = ((JSONObject) obj);
                String id = tmp.getString("Id");
                if (tmp.containsKey("Names")) {
                    JSONArray namesArray = tmp.getJSONArray("Names");
                    namesArray.forEach(name -> {
                        result.put(name.toString(), id);
                    });
                }
            }
        });
        return result;
    }


}
