package demo.spring.boot.demospringboot.enums;

/**
 * 状态 - 镜像和容器合并
 * 镜像不存在 -> 镜像存在 -> 容器不存在 -> 容器存在 -> 容器运行 -> 容器停止
 */
public enum DockerStatus {
    IMAGE_NO_EXIST("image_no_exist"), IMAGE_EXIST("image_exist"), CONTAINER_NO_EXIST("container_no_exist"), CONTAINER_RUNNING("container_running"), CONTAINER_STOPPED("container_stopped");
    private String status;

    DockerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
