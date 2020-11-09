package demo.spring.boot.demospringboot.service;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
public class ShellUtil {

    /**
     * 使用了模板泛型方法，+ function 处理input留作后期实现
     *
     * @param sh
     * @param function
     * @param <T>
     * @return
     */
    public static <T> T executeLinuxShell(String sh, Function<InputStream, T> function) {
        String shell = sh;
        log.info("sh is :{}", shell);
        Runtime run = Runtime.getRuntime();
        InputStream in = null;
        T result = null;
        try {
            Process process = run.exec(shell);
            while (process.isAlive()) {
                in = process.getInputStream();
                result = function.apply(in);
                in = process.getErrorStream();
                result = function.apply(in);
                in.close();
            }
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 调用了模板方法，调用时实现逻辑
     *
     * @param sh
     * @return
     */
    public static List<String> getResult(String sh) {
        return ShellUtil.executeLinuxShell(sh, inputStream -> {
            Reader reader = new InputStreamReader(inputStream);
            LineNumberReader lineNumberReader
                    = new LineNumberReader(reader);
            List<String> list;
            list = lineNumberReader.lines().collect(Collectors.toList());
            try {
                lineNumberReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        });
    }

    @org.junit.Test
    public void test() {
        List<String> list = this.getResult("ls");
        log.info("result:{}", list);
    }

    /**
     * https://blog.csdn.net/shadow_zed/article/details/93545843
     *
     * @throws IOException
     */
    public static void run(String shell) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(shell);

        //设置环境变量，初始值是当前进程环境的一个副本System.getenv()
        Map<String, String> env = pb.environment();
        File log2 = new File("log");
        log2.createNewFile();
        //redirectErrorStream 属性默认值为false，意思是子进程的标准输出和错误输出被发送给两个独立的流，这些流可以通过 Process.getInputStream() 和 Process.getErrorStream() 方法来访问。 
        //如果将值设置为 true，标准错误将与标准输出合并。这使得关联错误消息和相应的输出变得更容易。在此情况下，合并的数据可从 Process.getInputStream() 返回的流读取，而从 Process.getErrorStream() 返回的流读取将直接到达文件尾。
//        pb.redirectErrorStream(true);
//        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log2));
        //启动进程
        Process p = pb.start();

        while (p.isAlive()) {
            Reader reader = new InputStreamReader(p.getInputStream());
            LineNumberReader lineNumberReader
                    = new LineNumberReader(reader);
            List<String> list;
            list = lineNumberReader.lines().collect(Collectors.toList());
            log.info("list:{}", list);
        }
    }
}



