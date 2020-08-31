package demo.spring.boot.demospringboot.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 这里是通用util ， 用来处理日常
 * 为减轻依赖，只允许使用java包下的class
 */


public class CommonUtil {


    /**
     * @param collection 需要转换的集合
     * @param type       需要转换的类型
     * @param <T>        需要转换的类型
     * @return e.g. CommonUtil.collectiontoArray(queryFieldNames, String.class)
     */
    public static <T> T[] collectiontoArray(Collection<T> collection, Class<T> type) {
        T[] ts = (T[]) Array.newInstance(type, collection.size());
        collection.toArray(ts);
        return ts;


    }

    /**
     * 用于快速创建map
     *
     * @return
     */
    public MapClass quickCreateHashMap() {
        return new MapClass();
    }

    /**
     * 内部类 -> 用于辅助map
     */
    public class MapClass {
        private Map map = new HashMap();

        public <K, V> MapClass add(K k, V v) {
            map.put(k, v);
            return this;
        }

        public <K, V> Map build() {
            return map;
        }

    }

    /**
     * XML特殊字符转义
     *
     * @param source
     * @return
     */
    public static String escapeXml11(String source) {
        return StringEscapeUtils.escapeXml11(source);
    }


    /**
     * 按行在读取 -> 每一行放入list
     *
     * @param reader
     * @return
     * @throws IOException
     */
    public static Map<Integer, List<String>> read(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);//换成你的文件名
        bufferedReader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
        String line = null;
        Map<Integer, List<String>> result = new HashMap<>();
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            i++;
            String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            List<String> resultStr = Arrays.asList(item);
            result.put(i, resultStr);
        }
        return result;
    }

    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<Integer, List<String>> read(File file) throws IOException {
        List<String> list = FileUtils.readLines(file, "UTF-8");
        Map<Integer, List<String>> result = new HashMap<>();
        int i = 0;
        for (String line : list) {
            i++;
            String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            List<String> resultStr = Arrays.asList(item);
            result.put(i, resultStr);
        }
        return result;
    }

}
