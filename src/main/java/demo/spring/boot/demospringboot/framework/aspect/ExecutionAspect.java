package demo.spring.boot.demospringboot.framework.aspect;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.framework.Response;
import demomaster.vo.ProjectVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 2018/8/9    Created by   chao
 */
@Slf4j
@Aspect
@Component
public class ExecutionAspect {

    @Autowired
    private StartConfig startConfig;


    /**
     * 定义切面执行的方法
     */
    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void pointCut() {
    }

    /**
     * ProceedingJoinPoint 继承的 JoinPoint 比JoinPoint ， 只多了执行的proceed Around </>一旦执行了这个方法，如果不调用proceed
     * ， 就会导致调用终止 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 执行完AfterAdvice，再转到ThrowingAdvice
     */
    @Around(value = "pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed(); //继续下一个方法的调用 ：就是调用拦截的函数，得到拦截函数执行的结果
            if (result instanceof Response) {
                Object content = ((Response) result).getContent();
                if (content instanceof PageInfo) {
                    List list = ((PageInfo) content).getList();
                    list.forEach(vo -> {
                        deal(vo);
                    });
                } else if (content instanceof ArrayList) {
                    ((ArrayList) content).forEach(vo -> {
                        deal(vo);
                    });
                } else if (content instanceof ProjectVo) {
                    deal(content);
                }
            }
            log.info("执行结果:{}", JSONObject.toJSON(result));
            return result;
        } catch (Exception e) {
            log.error("[]FAIL path:{}", e.getMessage(), e);
        }
        return result;
    }

    private void deal(Object vo) {
        if (vo instanceof ProjectVo) {
            ProjectVo tmp = ((ProjectVo) vo);
            String httpInnerAddress = tmp.getHttpInnerAddress();
            String httpOutAddress = tmp.getHttpOutAddress();
            if (StringUtils.isNotBlank(httpInnerAddress)) {
                String replaceHttpInnerAddress = httpInnerAddress.replace(StartConfig.INNER_HOST, startConfig.getLocalHostInner());
                tmp.setHttpInnerAddress(replaceHttpInnerAddress);
            }
            if (StringUtils.isNotBlank(httpOutAddress)) {
                String replaceHttpOutAddress = httpOutAddress.replace(StartConfig.OUT_HOST, startConfig.getLocalHostOuter());
                tmp.setHttpOutAddress(replaceHttpOutAddress);
            }
        }
    }


}
