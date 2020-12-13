package demo.spring.boot.demospringboot.util;

import java.text.MessageFormat;
import java.util.List;

//docker状态,创建,运行中,停止,容器移除,镜像移除
public class DockerCmdUtils {

    /**
     * 状态
     */
    public enum Status {
        CREATED("created"), RUNNING("running"), STOPPED("stopped"), CONTAINER_REMOVED("container_removed"), IMAGES_REMOVED("images_removed");
        private String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

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
        return MessageFormat.format("docker build --rm -t  {0}  {1}  --name {0} -p {1}:{2} {3}", imageName, dockerRealPath);
    }

    public static String create(String containerName, Integer outPort, Integer innerPort, String imageName) {
        return MessageFormat.format("docker create  --name {0} -p {1}:{2} {3}", containerName, String.valueOf(outPort), String.valueOf(innerPort), imageName);
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
        return MessageFormat.format("docker rmi {0}", imageName);
    }

    /**
     * 获取容器完整的id
     */
    public static String getFullId(String containerName) {
        return MessageFormat.format("docker  inspect {0} --format='{{.Id}}'", containerName);
    }

    public static List<String> getContainerRunningNames() {
        String cmd = "docker ps -a -f status=running --format {{.Names}}";
        return ShellUtil.getResult(cmd.split(" "));
    }


}
