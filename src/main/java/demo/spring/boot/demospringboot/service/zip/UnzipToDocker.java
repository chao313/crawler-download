package demo.spring.boot.demospringboot.service.zip;

import demo.spring.boot.demospringboot.util.DockerCmdUtils;
import demo.spring.boot.demospringboot.vo.LanguageType;
import demomaster.vo.ProjectVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 模板方法 -> 指定步骤
 */
@Slf4j
@Component
public abstract class UnzipToDocker {

    /**
     * 解压并且获取解压缩的根目录
     * 1.全部转换成UTF-8
     * 2.获取内部的SQL语句
     * 3.修改数据量连接地址
     *
     * @param fileInDirAbsolutePath 压缩包所文件夹绝对路径
     * @param fileName              压缩包的
     * @param sql                   压缩包内SQL
     * @param checkLanguageType     期望压缩包语言类型(为空不做判断)
     * @param languageType          压缩包语言类型
     * @return 返回解压缩的根目录 - 绝对路径
     */
    protected abstract String unzipAndGetRoot(String fileInDirAbsolutePath,
                                              String fileName,
                                              StringBuilder sql,
                                              LanguageType checkLanguageType,
                                              AtomicReference<LanguageType> languageType);

    /**
     * 找到真正的数据路径
     *
     * @param rootPath 解压缩的根目录
     * @return 找到真正的数据路径
     */
    protected abstract String getDataPath(String rootPath);

    /**
     * 组合docker文件和真正的是数据文件
     *
     * @param dataPath        找到真正的数据路径
     * @param dockerModelPath 用于复制的docker的model的路径
     * @return 复制的DockerFile所在的文件路径
     */
    protected abstract String copyCombineCodeAndDocker(String dataPath,
                                                       String dockerModelPath,
                                                       String workDirAbsolutePath) throws IOException;

    /**
     * 补全sql文件
     *
     * @param dockerRealPath 解ocker组合后的路径
     * @return 数据路径
     */
    protected abstract void makeUpSqlPath(String dockerRealPath,
                                          StringBuilder sql) throws IOException;

    /**
     * 补全描述文件
     *
     * @param dockerRealPath docker组合后的路径
     * @param descMap        描述的路径
     * @return 数据路径
     */
    protected abstract void makeUpDecPath(String dockerRealPath, Map<String, byte[]> descMap) throws IOException;


    /**
     * 构建docker镜像
     *
     * @param dockerRealPath docker真实的路径
     * @param fileName       待解压的文件名称
     * @return imageName     镜像名称
     */
    protected abstract String buildDockerImage(String dockerRealPath,
                                               String fileName);

    /**
     * 推送docker镜像
     *
     * @param imageName 镜像名称
     * @return
     */
    protected abstract Boolean pushDockerImage(String imageName);


    /**
     * 构建运行docker容器 容器名称默认为_+容器名称
     *
     * @param imageName 镜像名称
     * @return
     */
    protected abstract String buildRunDockerContainer(String imageName, Integer port);


    /**
     * @param fileInDirAbsolutePath 待解压的文件所在文件夹的绝对地址
     * @param workDirAbsolutePath   工作文件夹
     * @param fileName              待解压的文件名称
     * @param dockerModelDirPath    docker的model的地址
     * @param port                  docker的端口号
     * @param descMap               描述的map
     */
    public boolean doWork(String fileInDirAbsolutePath,
                          String workDirAbsolutePath,
                          String fileName,
                          String dockerModelDirPath,
                          Integer port,
                          Map<String, byte[]> descMap,
                          ProjectVo projectVo) throws IOException {

        StringBuilder sql = new StringBuilder();//存放sql的地址

        AtomicReference<LanguageType> languageType = new AtomicReference<>();

        /**
         * 解压并且获取解压缩的根目录
         */
        String rootPath = this.unzipAndGetRoot(fileInDirAbsolutePath, fileName, sql, null, languageType);
        log.info("解压的路径:{}", rootPath);
//        if (null == languageType.get() || (null != languageType.get() && !languageType.get().equals(LanguageType.PHP))) {
//            log.info("当前项目不是PHP项目:{}", languageType.get());
//            return false;
//        }
        /**
         * 找到真正的数据路径
         */
        String dataPath = this.getDataPath(rootPath);
        log.info("解压的data路径:{}", dataPath);

        /**
         * 组合docker文件和真正的是数据文件
         */
        String dockerRealPath = this.copyCombineCodeAndDocker(dataPath, dockerModelDirPath, workDirAbsolutePath);
        log.info("组合docker的path:{}", dockerRealPath);

        /**
         * 补全sql文件
         */
        this.makeUpSqlPath(dockerRealPath, sql);
        log.info("补全sql数据:{}", sql.toString().length());
        /**
         * 补全描述文件
         */
        this.makeUpDecPath(dockerRealPath, descMap);

        /**
         * 构建docker镜像
         */
        String imageName = this.buildDockerImage(dockerRealPath, fileName);
        log.info("镜像名称:{}", imageName);


        /**
         * 推送docker镜像
         */
//        Boolean pushFlag = this.pushDockerImage(imageName);
//        log.info("镜像推送:{}", pushFlag);

        /**
         * 构建运行docker容器 容器名称默认为_+容器名称
         */
        String containerName = this.buildRunDockerContainer(imageName, port);
        log.info("镜像容器运行:{}", containerName);

        if (null != projectVo) {
            if (null != languageType.get()) {
                projectVo.setLanguage(languageType.get().getType());
            }
            projectVo.setDockerImageName(imageName);
            projectVo.setDockerContainerName(containerName);
            projectVo.setDockerPort(port.toString());
            projectVo.setHttpInnerAddress(ProjectVo.INNER_HOST + ":" + port);
            projectVo.setHttpOutAddress(ProjectVo.OUT_HOST + ":" + port);
            projectVo.setDockerShellCreate(DockerCmdUtils.create(containerName, port, 80, imageName));
            projectVo.setDockerShellRun(DockerCmdUtils.run(containerName));
            projectVo.setDockerShellStop(DockerCmdUtils.stopContainer(containerName));
            projectVo.setDockerShellContainerRemove(DockerCmdUtils.removeContainer(containerName));
            projectVo.setDockerShellImageRemove(DockerCmdUtils.removeImage(imageName));
            projectVo.setProjectStatus(ProjectVo.Status.CREATED.getStatus());
            projectVo.setDockerStatus(DockerCmdUtils.Status.CREATED.getStatus());
        }
        return true;
    }

}
