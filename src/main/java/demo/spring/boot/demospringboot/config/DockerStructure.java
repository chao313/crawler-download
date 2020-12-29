package demo.spring.boot.demospringboot.config;

/**
 * Docker文件结构
 */
public class DockerStructure {
    public static final String DOCKER_MODEL_Dir_Path = DockerStructure.class.getResource("/docker/model").getPath();
    public static final String CODE = "/app";
    public static final String DSC = "/dsc";
    public static final String DSC_TXT_NAME = "desc.txt";//描述的文件名称
    public static final String INSTALL_SQL = "/init.sh";
}
