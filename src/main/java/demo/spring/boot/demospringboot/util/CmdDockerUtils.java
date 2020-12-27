package demo.spring.boot.demospringboot.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//docker状态,创建,运行中,停止,容器移除,镜像移除
public class CmdDockerUtils {

    /**
     * 状态
     */
    public enum ContainerStatus {
        CREATED("created"), RUNNING("running"), EXISTED("exited");
        private String status;

        ContainerStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public static String build(String imageName, String dockerRealPath) {
        return MessageFormat.format("docker build  -t  chao313/{0}  {1} ", imageName, dockerRealPath);
    }

    public static String create(String containerName, Integer outPort, Integer innerPort, String imageName) {
        return MessageFormat.format("docker create  --name {0} -p {1}:{2} chao313/{3}", containerName, String.valueOf(outPort), String.valueOf(innerPort), imageName);
    }

    public static String run(String containerName) {
        return MessageFormat.format("docker start {0}", containerName);
    }

    public static String stopContainer(String containerName) {
        return MessageFormat.format("docker stop {0}", containerName);
    }

    public static String removeContainer(String containerName) {
        return MessageFormat.format("docker rm {0}", containerName);
    }

    public static String removeImage(String imageName) {
        return MessageFormat.format("docker rmi chao313/{0}", imageName);
    }

    /**
     * 按镜像名称检索
     */
    public static String imageSearchByImageName(String imageName) {
        return MessageFormat.format("docker images chao313/{0}", imageName);
    }

    /**
     * 按容器名称检索
     */
    public static String containerSearchByContainerName(String containerName) {
        return MessageFormat.format("docker ps -a --filter name={0}", containerName);
    }

    /**
     * 按容器名称检索
     */
    public static String containerRunningSearchByContainerName(String containerName) {
        return MessageFormat.format("docker ps --filter name={0}", containerName);
    }

    /**
     * 获取容器完整的id
     */
    public static String getFullId(String containerName) {
        return MessageFormat.format("docker  inspect {0} --format='{{.Id}}'", containerName);
    }

    /**
     * 获取tag的命令
     */
    public static String getTagCmd(String imageName) {
        return MessageFormat.format("docker tag {0}  docker.io/chao313/{0}:latest", imageName, imageName);
    }

    /**
     * 获取tag的命令
     */
    public static String getRemoveTagCmd(String imageName) {
        return MessageFormat.format("docker rmi  docker.io/chao313/{0}:latest", imageName, imageName);
    }

    /**
     * 获取push的命令
     */
    public static String getPushCmd(String imageName) {
        return MessageFormat.format("docker push docker.io/chao313/{0}:latest", imageName);
    }

    public static List<String> getContainerRunningNames() {
        String cmd = "docker ps -a -f status=running --format {{.Names}}";
        return ShellUtil.getResult(cmd.split(" "));
    }

    /**
     * 获取导出sql的命令
     */
    @Deprecated
    public static String[] getCopySqlCmd(String containerName, String pathDir, String fileName) {
        String format = MessageFormat.format("docker exec {0} mysqldump  -u admin111111 -p123456111111 php_test111111 ", containerName);
        String format_end = MessageFormat.format(" > {1}/{2}.sql", pathDir, fileName);
        List<String> tmp = new ArrayList<>(Arrays.asList(format.split(" ")));
        tmp.add(0,"-c");
        tmp.add(0,"/bin/bash");
        tmp.add(format_end);
        String[] result = {};
        return tmp.toArray(result);
    }

    /**
     * 获取导出sql的命令
     */
    public static String getCopySqlCmd(String containerName) {
        String format = MessageFormat.format("docker exec {0} mysqldump  -u admin111111 -p123456111111 php_test111111 ", containerName);
        return format;
    }

    /**
     * 获取导出sql的命令
     * docker cp 11_softview_62253_:/app ./<id>/app
     */
    public static String getCopyAppCmd(String containerName, String pathDir) {
        return MessageFormat.format("docker cp {0}:/app {1}", containerName, pathDir);
    }


}
