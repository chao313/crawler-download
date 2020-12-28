package demomaster.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.RequestUpdate;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.docker.DockerRestApiService;
import demomaster.service.ProjectPlusService;
import demomaster.vo.ProjectPlusMultiTermVo;
import demomaster.vo.ProjectPlusVo;
import demomaster.vo.plugin.ProjectPlusNoPriVo;
import demomaster.vo.plugin.ProjectPlusPriVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/ProjectPlusController")
@Slf4j
public class ProjectPlusController {

    @Autowired
    private ProjectPlusService service;

    @Autowired
    private DockerRestApiService dockerRestApiService;

    /**
     * 插入一条记录: 请求体是json
     *
     * @param vo
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/insert")
    public Response insert(@RequestBody ProjectPlusVo vo) {
        Response response = new Response();
        try {
            Boolean result = service.insert(vo);
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }

    /**
     * 插入多条记录: 请求体是json
     *
     * @param vos
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/inserts")
    public Response insert(@RequestBody List<ProjectPlusVo> vos) {
        Response response = new Response();
        try {
            Boolean result = service.insert(vos);
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }


    /**
     * 多条件查询语句,每个字段只要不为null就是查询条件
     *
     * @param query
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/queryBase")
    public Response queryBase(@RequestBody ProjectPlusVo query) {
        Response response = new Response();
        try {
            List<ProjectPlusVo> result = service.queryBase(query);
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }

    /**
     * 多条件查询语句,每个字段只要不为null就是查询条件
     * 这里添加了分页插件，能够返回的数据包含页码，下一页... , 自动查询count
     *
     * @param query
     * @param pageNum  页码 默认值为1
     * @param pageSize 每页的size 默认值为10
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/queryBasePageHelper")
    public Response queryBasePageHelper(@RequestBody ProjectPlusVo query,
                                        @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                        @RequestParam(value = "order", required = false) String order) throws IOException {
        Response response = new Response();
        try {
            PageHelper.startPage(pageNum, pageSize);
            if (StringUtils.isNotBlank(order)) {
                PageHelper.orderBy(order);
            }
            Collection<String> allImages = dockerRestApiService.getAllImages();
            Collection<String> allContainers = dockerRestApiService.getAllContainers();
            Map<String, String> allContainersMap = dockerRestApiService.getAllContainersMap();
            Collection<String> runningContainers = dockerRestApiService.getRunningContainers();
            List<ProjectPlusVo> result = service.queryBase(query);
            for (ProjectPlusVo vo : result) {
                vo.setDevDockerStatusImagesIsExist("false");
                vo.setDevDockerStatusContainerIsExist("false");
                vo.setDevDockerStatusContainerIsRunning("false");
                vo.setProDockerStatusImagesIsExist("false");
                vo.setProDockerStatusContainerIsExist("false");
                vo.setProDockerStatusContainerIsRunning("false");
                if (StringUtils.isNotBlank(vo.getDevDockerImageName())) {
                    allImages.forEach(image -> {
                        if (image.contains("/" + vo.getDevDockerImageName() + ":")) {
                            //检索到代表镜像存在 -> 指定为镜像存在
                            vo.setDevDockerStatusImagesIsExist("true");
                        }
                    });
                }
                if (StringUtils.isNotBlank(vo.getProDockerImageName())) {
                    allImages.forEach(image -> {
                        if (image.contains("/" + vo.getProDockerImageName() + ":")) {
                            //检索到代表镜像存在 -> 指定为镜像存在
                            vo.setProDockerStatusImagesIsExist("true");
                        }
                    });
                }
                if (StringUtils.isNotBlank(vo.getDevDockerContainerName())) {
                    allContainers.forEach(container -> {
                        if (container.contains("/" + vo.getDevDockerContainerName())) {
                            //检索到代表容器存在
                            vo.setDevDockerStatusContainerIsExist("true");
                        }
                    });
                }
                if (StringUtils.isNotBlank(vo.getProDockerContainerName())) {
                    allContainers.forEach(container -> {
                        if (container.contains("/" + vo.getProDockerContainerName())) {
                            //检索到代表容器存在
                            vo.setProDockerStatusContainerIsExist("true");
                        }
                    });
                }
                if (StringUtils.isNotBlank(vo.getDevDockerContainerName())) {
                    runningContainers.forEach(container -> {
                        if (container.contains("/" + vo.getDevDockerContainerName())) {
                            //检索到代表容器存在 -> 指定为镜像存在
                            vo.setDevDockerStatusContainerIsRunning("true");
                        }
                    });
                }
                if (StringUtils.isNotBlank(vo.getProDockerContainerName())) {
                    runningContainers.forEach(container -> {
                        if (container.contains("/" + vo.getProDockerContainerName())) {
                            //检索到代表容器存在 -> 指定为镜像存在
                            vo.setProDockerStatusContainerIsRunning("true");
                        }
                    });
                }
                String devDockerContainerId = allContainersMap.get("/" + vo.getDevDockerContainerName());
                vo.setDevDockerContainerId(devDockerContainerId);
                String proDockerContainerId = allContainersMap.get("/" + vo.getProDockerContainerName());
                vo.setProDockerContainerId(proDockerContainerId);
            }

            PageInfo pageInfo = new PageInfo(result);
            response.setCode(Code.System.OK);
            response.setContent(pageInfo);
            log.info("success pageInfo -> {} ", pageInfo.getSize());
        } catch (
                Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }

    /**
     * 多条件查询语句,每个字段只要不为null就是查询条件(这里是多维条件,包含like,not like)
     * 这里添加了分页插件，能够返回的数据包含页码，下一页... , 自动查询count
     *
     * @param query
     * @param pageNum  页码 默认值为1
     * @param pageSize 每页的size 默认值为10
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/queryMultiTermPageHelper")
    public Response queryMultiTermPageHelper(@RequestBody ProjectPlusMultiTermVo query,
                                             @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                             @RequestParam(value = "order", required = false) String order) {
        Response response = new Response();
        try {
            PageHelper.startPage(pageNum, pageSize);
            if (StringUtils.isNotBlank(order)) {
                PageHelper.orderBy(order);
            }
            List<ProjectPlusVo> result = service.queryMultiTerm(query);
            PageInfo pageInfo = new PageInfo(result);
            response.setCode(Code.System.OK);
            response.setContent(pageInfo);
            log.info("success pageInfo -> {} ", pageInfo);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }


    /**
     * 多条件更新语句,
     * source每个字段只要不为null就是更新数据 -> 慎用
     * target每个字段只要不为null就是查询条件 -> 慎用
     *
     * @param source
     * @param target
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/updateBase")
    public Response updateBase(@RequestBody RequestUpdate<ProjectPlusVo, ProjectPlusVo> update) {
        Response response = new Response();
        try {
            Boolean result = service.updateBase(update.getSource(), update.getTarget());
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }

    /**
     * 多条件更新语句,(包含null)
     * source每个字段都是更新数据 -> 慎用
     * target每个字段只要不为null就是查询条件 -> 慎用
     *
     * @param source
     * @param target
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/updateBaseIncludeNull")
    public Response updateBaseIncludeNull(@RequestBody RequestUpdate<ProjectPlusVo, ProjectPlusVo> update) {
        Response response = new Response();
        try {
            Boolean result = service.updateBaseIncludeNull(update.getSource(), update.getTarget());
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }

    /**
     * 多条件查询语句,
     * 每个字段只要不为null就是查询条件
     *
     * @param vo
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/deleteBase")
    public Response deleteBase(@RequestBody ProjectPlusVo vo) {
        Response response = new Response();
        try {
            Boolean result = service.deleteBase(vo);
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }


    /**
     * 主键查询语句,
     * 因为是主键 -> 查询返回的是一条记录
     *
     * @param id
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @GetMapping(value = "/queryByPrimaryKey")
    public Response queryByPrimaryKey(
            @RequestParam Integer id
    ) {
        Response response = new Response();
        try {
            ProjectPlusVo result = service.queryByPrimaryKey(id);
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }


    /**
     * 主键删除语句,
     * 因为是主键 -> 删除的是一条记录
     *
     * @param id
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @GetMapping(value = "/deleteByPrimaryKey")
    public Response deleteByPrimaryKey(
            @RequestParam Integer id
    ) {
        Response response = new Response();
        try {
            Boolean result = service.deleteByPrimaryKey(id);
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }

    /**
     * 根据PrimaryKey更新，会根据主键去更新其他的值(空值不覆盖有值)
     *
     * @param update.source 只包含非主键的字段
     * @param update.target 只包含主键的字段
     * @param id
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/updateByPrimaryKey")
    public Response updateByPrimaryKey(@RequestBody RequestUpdate<ProjectPlusNoPriVo, ProjectPlusPriVo> update) {
        Response response = new Response();
        try {
            Boolean result = service.updateByPrimaryKey(update.getSource(), update.getTarget());
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("success result -> {} ", result);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 -> {} ", e.getMessage(), e);
        }
        return response;
    }
}
