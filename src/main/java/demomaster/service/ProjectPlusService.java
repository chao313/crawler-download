package demomaster.service;


import java.util.List;

import demomaster.vo.ProjectPlusVo;
import demomaster.vo.plugin.ProjectPlusPriVo;
import demomaster.vo.plugin.ProjectPlusNoPriVo;
import demomaster.vo.ProjectPlusMultiTermVo;


/**
 * 表名称      :project_plus
 * 表类型      :BASE TABLE
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建      :2020-12-25
 * 字符集      :utf8_bin
 * 表注释      :
 */
public interface ProjectPlusService {

    /**
     * insert
     */
    boolean insert(ProjectPlusVo vo);


    /**
     * insert vos 批量插入
     */
    boolean insert(List<ProjectPlusVo> vos);


    /**
     * 查询base
     */
    List<ProjectPlusVo> queryBase(ProjectPlusVo query);

    /**
     * 查询base 多维条件
     */
    List<ProjectPlusVo> queryMultiTerm(ProjectPlusMultiTermVo query);

    /**
     * update base (exclude value is null or "")
     */
    boolean updateBase(ProjectPlusVo source, ProjectPlusVo target);

    /**
     * update base (include value is null or "")
     */
    boolean updateBaseIncludeNull(ProjectPlusVo source, ProjectPlusVo target);

    /**
     * 删除base
     */
    boolean deleteBase(ProjectPlusVo vo);


    /**
     * 根据PrimaryKey查询
     * <p>
     * id : 
     */
     ProjectPlusVo queryByPrimaryKey(Integer id);

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
    boolean updateByPrimaryKey(ProjectPlusNoPriVo source, ProjectPlusPriVo target);



}
