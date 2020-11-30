package demomaster.service;


import java.util.List;

import demomaster.vo.ProjectVo;
import demomaster.vo.plugin.ProjectPriVo;
import demomaster.vo.plugin.ProjectNoPriVo;
import demomaster.vo.ProjectMultiTermVo;


/**
 * 表名称      :project
 * 表类型      :BASE TABLE
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建      :2020-11-29
 * 字符集      :utf8_bin
 * 表注释      :
 */
public interface ProjectService {

    /**
     * insert
     */
    boolean insert(ProjectVo vo);


    /**
     * insert vos 批量插入
     */
    boolean insert(List<ProjectVo> vos);


    /**
     * 查询base
     */
    List<ProjectVo> queryBase(ProjectVo query);

    /**
     * 查询base 多维条件
     */
    List<ProjectVo> queryMultiTerm(ProjectMultiTermVo query);

    /**
     * update base (exclude value is null or "")
     */
    boolean updateBase(ProjectVo source, ProjectVo target);

    /**
     * update base (include value is null or "")
     */
    boolean updateBaseIncludeNull(ProjectVo source, ProjectVo target);

    /**
     * 删除base
     */
    boolean deleteBase(ProjectVo vo);


    /**
     * 根据PrimaryKey查询
     * <p>
     * id : 
     */
     ProjectVo queryByPrimaryKey(Integer id);

    /**
     * 根据PrimaryKey删除
     * <p>
     * id : 
     * @param id
     * 
     */
    boolean deleteByPrimaryKey(Integer id);

    /**
     * 根据PrimaryKey更新，会根据主键去更新其他的值(空值不覆盖有值) -> 主键不更新
     * <p>
     * id : 
     * @param id
     * 
     */
    boolean updateByPrimaryKey(ProjectNoPriVo source, ProjectPriVo target);



}
