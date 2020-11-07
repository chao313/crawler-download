package demo.spring.boot.demospringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

@Slf4j
public class ApacheZipUtils {

    /**
     * 解压
     * 从输入流到输出流
     *
     * @param inputStream
     * @throws Exception
     */
    public static void unZip(InputStream inputStream, OutputStream outputStream) throws Exception {
        ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(inputStream);
        ZipArchiveEntry entry = null;
        while ((entry = zipArchiveInputStream.getNextZipEntry()) != null) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    public static void unZip(InputStream stream) throws Exception {
        ZipArchiveInputStream inputStream = new ZipArchiveInputStream(stream);
        ZipArchiveEntry entry = null;
        while ((entry = inputStream.getNextZipEntry()) != null) {
            System.out.println(entry.getName());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //读取内容
            IOUtils.copy(inputStream, outputStream);
            System.out.println(outputStream.toString());
        }
        inputStream.close();
        stream.close();
    }
}
