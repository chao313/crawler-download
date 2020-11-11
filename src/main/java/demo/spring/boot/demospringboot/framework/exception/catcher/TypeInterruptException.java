package demo.spring.boot.demospringboot.framework.exception.catcher;

/**
 * 类型中断异常
 */
public class TypeInterruptException extends RuntimeException {
    public TypeInterruptException() {
    }

    public TypeInterruptException(String message) {
        super(message);
    }
}
