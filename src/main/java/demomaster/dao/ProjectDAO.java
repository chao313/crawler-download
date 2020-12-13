package demomaster.dao;


import demomaster.vo.ProjectMultiTermVo;
import demomaster.vo.ProjectVo;
import demomaster.vo.plugin.ProjectNoPriVo;
import demomaster.vo.plugin.ProjectPriVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表名称      :project
 * 表类型      :BASE TABLE
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建      :2020-11-30
 * 字符集      :utf8_bin
 * 表注释      :
 */
public interface ProjectDAO {

    /**
     * insert
     */
    int insert(ProjectVo vo);

    /**
     * insert vos 批量插入
     */
    int inserts(@Param(value = "vos") List<ProjectVo> vos);

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
    int updateBase(@Param(value = "source") ProjectVo source, @Param(value = "target") ProjectVo target);


    /**
     * update base (include value is null or "")
     */
    int updateBaseIncludeNull(@Param(value = "source") ProjectVo source, @Param(value = "target") ProjectVo target);

    /**
     * 删除base
     */
    int deleteBase(ProjectVo vo);


    /**
     * 根据PrimaryKey查询
     * <p>
     * id :
     *
     * @param id
     */
    ProjectVo queryByPrimaryKey(@Param(value = "id") Integer id);

    /**
     * 根据PrimaryKey删除
     * <p>
     * id :
     *
     * @param id
     */
    int deleteByPrimaryKey(@Param(value = "id") Integer id);

    /**
     * 根据PrimaryKey更新，会根据主键去更新其他的值(空值不覆盖有值)
     * <p>
     * id :
     *
     * @param id
     */
    int updateByPrimaryKey(@Param(value = "source") ProjectNoPriVo source, @Param(value = "target") ProjectPriVo target);

}
