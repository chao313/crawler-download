package demo.spring.boot.demospringboot.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ProUtils {

    public static String PATH_ROOT = "PATH_ROOT";//根目录
    public static String PATH_APP = "PATH_APP";
    public static String PATH_SQL = "PATH_SQL";
    public static String PATH_INIT = "PATH_INIT";
    public static String PATH_PRO_ZIP = "PATH_PRO_ZIP";
    public static String NAME_PRO_ZIP = "NAME_PRO_ZIP";
    public static String TO_DELETE_DIR = "TO_DELETE_DIR";

    public static ThreadLocal<Map<String, String>> threadLocalVar = InheritableThreadLocal.withInitial(HashMap::new);

    public static String getProAccount(String criteriaid) {
        return (criteriaid + "_admin").replace("_SoftView", "");
    }

    public static String getProPasswd(String criteriaid) {
        return criteriaid + "_passwd";
    }

    public static String getProDb(String criteriaid) {
        return criteriaid + "_db";
    }

    /**
     * 获取dev到pro的映射
     */
    public static Map<String, String> getDevToProReplace(String criteriaid) {
        Map<String, String> map = MapUtil.$(
                "localhost111111", "host.docker.internal",
                "admin111111", getProAccount(criteriaid),
                "123456111111", getProPasswd(criteriaid),
                "php_test111111", getProDb(criteriaid)
        );
        return map;
    }

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public static String getVByK(String key) {
        return threadLocalVar.get().get(key);
    }

}
