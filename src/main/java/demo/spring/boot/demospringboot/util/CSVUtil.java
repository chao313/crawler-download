package demo.spring.boot.demospringboot.util;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtil {

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
 
