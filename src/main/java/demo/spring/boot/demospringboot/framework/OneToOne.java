package demo.spring.boot.demospringboot.framework;

import lombok.Data;

/**
 * 正对于 1对1而存在的
 *
 * @param <T>
 * @param <V>
 */
@Data
public class OneToOne<T, V> {
    private T master;
    private V slave;

    public OneToOne(T master, V slave) {
        this.master = master;
        this.slave = slave;
    }

    public OneToOne() {
    }

    public static <T, V> OneToOne build(T master, V slave) {
        return new OneToOne(master, slave);
    }
}
