package demo.spring.boot.demospringboot.framework;

import lombok.Data;

import java.util.List;

/**
 * 这个是多查询的泛型，加入下划线是为了 避免和数据库的关键字段冲突
 *
 * @param <T>
 */
@Data
public class MultiTerm<T> {
    List<T> _in;
    List<T> _notIn;
    List<String> _like;
    List<String> _notLike;
    List<String> _regex;
    List<String> _notRegex;
    T _lt; //小于
    T _le;
    T _ge;
    T _gt;
}
