package demo.spring.boot.demospringboot.service.asp;

import demo.spring.boot.demospringboot.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 集群API
 */
@FeignClient(name = "Asp300FeignService", url = Asp300FeignService.HOST, configuration = FeignServiceConfig.class)
public interface Asp300FeignService {

    String HOST = "http://www.asp300.net";

    /**
     * 功能:获取跨集群复制统计信息
     */
    @RequestMapping(value = "/2012user/login.asp?action=chk&url=http://www.asp300.net/", method = RequestMethod.GET)
    String login(@RequestParam(value = "username") String username,
                 @RequestParam(value = "password") String password,
                 @RequestParam(value = "loginsubmit") String loginsubmit);


}
