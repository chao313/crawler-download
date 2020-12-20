package demo.spring.boot.demospringboot.enums;

/**
 * 状态 project状态,刚创建,可以使用,暂不可使用,完全不能使用
 */
public enum ProjectStatus {
    CREATED("created"), CAN_USE("can_use"), TEMPORARY_CAN_NOT_USE("temporary_can_not_use"), COMPLETE_CAN_NOT_USE("complete_can_not_use");
    private String status;

    ProjectStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
