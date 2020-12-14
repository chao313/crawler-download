package demomaster.dao;


import java.util.List;

import demomaster.vo.ProjectPlusVo;
import demomaster.vo.plugin.ProjectPlusPriVo;
import demomaster.vo.plugin.ProjectPlusNoPriVo;
import demomaster.vo.ProjectPlusMultiTermVo;


import org.apache.ibatis.annotations.Param;

/**
 * 表名称      :project_plus
 * 表类型      :BASE TABLE
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建      :2020-12-14
 * 字符集      :utf8_bin
 * 表注释      :
 */
public interface ProjectPlusDAO {

    /**
     * insert
     */
    int insert(ProjectPlusVo vo);

    /**
     * insert vos 批量插入
     */
    int inserts(@Param(value = "vos") List<ProjectPlusVo> vos);

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
    int updateBase(@Param(value = "source") ProjectPlusVo source, @Param(value = "target") ProjectPlusVo target);


    /**
     * update base (include value is null or "")
     */
    int updateBaseIncludeNull(@Param(value = "source") ProjectPlusVo source, @Param(value = "target") ProjectPlusVo target);

    /**
     * 删除base
     */
    int deleteBase(ProjectPlusVo vo);


    /**
     * 根据PrimaryKey查询
     * <p>
     * id : 
     * @param id
     * 
     */
    ProjectPlusVo queryByPrimaryKey(@Param(value = "id") Integer id);

    /**
     * 根据PrimaryKey删除
     * <p>
     * id : 
     * @param id
     * 
     */
    int deleteByPrimaryKey(@Param(value = "id") Integer id);

    /**
     * 根据PrimaryKey更新，会根据主键去更新其他的值(空值不覆盖有值)
     * <p>
     * id : 
     * @param id
     * 
     */
    int updateByPrimaryKey(@Param(value = "source") ProjectPlusNoPriVo source, @Param(value = "target") ProjectPlusPriVo target);

}
