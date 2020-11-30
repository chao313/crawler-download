package demo.spring.boot.demospringboot.framework;

import lombok.Data;

import java.util.List;

/**
 * 正对于 1对1而存在的
 *
 * @param <T>
 * @param <V>
 */
@Data
public class OneToMore<T, V> {
    private T master;
    private List<V> slaves;

    public OneToMore(T master, List<V> slaves) {
        this.master = master;
        this.slaves = slaves;
    }

    public OneToMore() {
    }

    public static <T, V> OneToMore build(T master, List<V> slaves) {
        return new OneToMore(master, slaves);
    }
}
