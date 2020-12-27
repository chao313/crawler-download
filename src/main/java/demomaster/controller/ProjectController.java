package demomaster.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.RequestUpdate;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.util.CmdDockerUtils;
import demomaster.service.ProjectService;
import demomaster.vo.ProjectMultiTermVo;
import demomaster.vo.ProjectVo;
import demomaster.vo.plugin.ProjectNoPriVo;
import demomaster.vo.plugin.ProjectPriVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ProjectController")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService service;

    /**
     * 插入一条记录: 请求体是json
     *
     * @param vo
     * @return 成功和失败都返回Response，具体的结果在response的
     * app   :状态码
     * content:具体返回值
     */
    @PostMapping(value = "/insert")
    public Response insert(@RequestBody ProjectVo vo) {
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
    public Response insert(@RequestBody List<ProjectVo> vos) {
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
    public Response queryBase(@RequestBody ProjectVo query) {
        Response response = new Response();
        try {
            List<ProjectVo> result = service.queryBase(query);
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
    public Response queryBasePageHelper(@RequestBody ProjectVo query,
                                        @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                        @RequestParam(value = "order", required = false) String order) {
        Response response = new Response();
        try {
            PageHelper.startPage(pageNum, pageSize);
            if (StringUtils.isNotBlank(order)) {
                PageHelper.orderBy(order);
            }
            List<ProjectVo> result = service.queryBase(query);
            //补全docker状态
            List<String> containerRunningNames = CmdDockerUtils.getContainerRunningNames();
            result.forEach(vo -> {
                if (containerRunningNames.contains(vo.getDockerContainerName())) {
                    vo.setDockerStatus(CmdDockerUtils.ContainerStatus.RUNNING.getStatus());
                } else {
                    vo.setDockerStatus(CmdDockerUtils.ContainerStatus.EXISTED.getStatus());
                }
            });
            PageInfo pageInfo = new PageInfo(result);
            response.setCode(Code.System.OK);
            response.setContent(pageInfo);
            log.info("success pageInfo -> {} ");
        } catch (Exception e) {
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
    public Response queryMultiTermPageHelper(@RequestBody ProjectMultiTermVo query,
                                             @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                             @RequestParam(value = "order", required = false) String order) {
        Response response = new Response();
        try {
            PageHelper.startPage(pageNum, pageSize);
            if (StringUtils.isNotBlank(order)) {
                PageHelper.orderBy(order);
            }
            List<ProjectVo> result = service.queryMultiTerm(query);
            PageInfo pageInfo = new PageInfo(result);
            response.setCode(Code.System.OK);
            response.setContent(pageInfo);
//            log.info("success pageInfo -> {} ", pageInfo);
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
    public Response updateBase(@RequestBody RequestUpdate<ProjectVo, ProjectVo> update) {
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
    public Response updateBaseIncludeNull(@RequestBody RequestUpdate<ProjectVo, ProjectVo> update) {
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
    public Response deleteBase(@RequestBody ProjectVo vo) {
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
            ProjectVo result = service.queryByPrimaryKey(id);
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
    public Response updateByPrimaryKey(@RequestBody RequestUpdate<ProjectNoPriVo, ProjectPriVo> update) {
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
