package demo.spring.boot.demospringboot.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Slf4j
public class SevenZipUtilsTest {

    @Test
    public void unzip() {
        String zipFilePath = "D:/20201102";
        String zipFileName = "470.rar";
        String targetFileDir = "D:/20201102";
        SevenZipUtils.unzip(zipFilePath, zipFileName, targetFileDir, ArchiveFormat.RAR);

    }

}
