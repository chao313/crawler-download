package demo.spring.boot.demospringboot.framework;

/**
 * 这个是用于updateByKey的request使用
 */
public class RequestUpdate<SOURCE, TARGET> {

    /**
     * source -> 需要更新的
     */
    private SOURCE source;

    /**
     * target -> 目标条件
     */
    private TARGET target;


    public SOURCE getSource() {
        return source;
    }

    public void setSOURCE(SOURCE source) {
        this.source = source;
    }

    public TARGET getTarget() {
        return target;
    }

    public void setTARGET(TARGET target) {
        this.target = target;
    }


    @Override
    public String toString() {
        return "TQuestionsRequestUpdatePrimaryKey{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }

}
