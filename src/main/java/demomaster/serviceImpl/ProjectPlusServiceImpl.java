package demomaster.serviceImpl;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import demomaster.vo.ProjectPlusVo;
import demomaster.dao.ProjectPlusDAO;
import demomaster.service.ProjectPlusService;
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
@Service
public class ProjectPlusServiceImpl implements ProjectPlusService {

    @Autowired
    private ProjectPlusDAO dao;

    /**
     * insert
     */
    @Override
    public boolean insert(ProjectPlusVo vo) {

        return dao.insert(vo) > 0 ? true : false;

    }

    /**
     * insert vos 批量插入
     */
    @Override
    public boolean insert(List<ProjectPlusVo> vos) {

        return dao.inserts(vos) > 0 ? true : false;

    }

    /**
     * 查询base
     */
    @Override
    public List<ProjectPlusVo> queryBase(ProjectPlusVo query) {

        return dao.queryBase(query);

    }

    /**
     * 查询base 多维条件
     */
    @Override
    public List<ProjectPlusVo> queryMultiTerm(ProjectPlusMultiTermVo query) {

        return dao.queryMultiTerm(query);

    }

    /**
     * update base (exclude value is null or "")
     */
    @Override
    public boolean updateBase(ProjectPlusVo source, ProjectPlusVo target) {

        return dao.updateBase(source, target) > 0 ? true : false;

    }

    /**
     * update base (include value is null or "")
     */
    @Override
    public boolean updateBaseIncludeNull(ProjectPlusVo source, ProjectPlusVo target) {

        return dao.updateBaseIncludeNull(source, target) > 0 ? true : false;

    }

    /**
     * 删除base
     */
    @Override
    public boolean deleteBase(ProjectPlusVo vo) {

        return dao.deleteBase(vo) > 0 ? true : false;

    }


    /**
     * 根据PrimaryKey查询
     * <p>
     * id  
     */
    @Override
    public ProjectPlusVo queryByPrimaryKey(Integer id) {

        return dao.queryByPrimaryKey(id);

    }

    /**
     * 根据PrimaryKey删除
     * <p>
     * id : 
     * @param id
     * 
     */
    @Override
    public boolean deleteByPrimaryKey(Integer id) {

        return dao.deleteByPrimaryKey(id) > 0 ? true : false;

    }

    /**
     * 根据PrimaryKey更新，会根据主键去更新其他的值(空值不覆盖有值)
     * <p>
     * id : 
     * @param id
     * 
     */
    @Override
    public boolean updateByPrimaryKey(ProjectPlusNoPriVo source,ProjectPlusPriVo target){

        return dao.updateByPrimaryKey(source,target) > 0 ? true : false;

    }


}
