package demo.spring.boot.demospringboot.util;

import java.io.*;

public class IOUtils {

    /**
     * inputStream转outputStream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static OutputStream inputToOutput(InputStream in) throws Exception {
        OutputStream outputStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            outputStream.write(ch);
        }
        return outputStream;
    }

    /**
     * outputStream转inputStream
     *
     * @param out
     * @return
     */
    public static InputStream outputToInput(OutputStream out) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        return inputStream;
    }


}
