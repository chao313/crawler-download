package demo.spring.boot.demospringboot.service.zip;

import demo.spring.boot.demospringboot.vo.LanguageType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * zip过滤
 * 模板方法 -> 指定步骤
 */
@Slf4j
@Component
public abstract class UnzipFilter {

    /**
     * 获取
     *
     * @param fileInDirAbsolutePath 压缩包所文件夹绝对路径
     * @param fileName              压缩包的
     * @param checkLanguageType     操作指定的文件类型
     * @param languageType          压缩包语言类型
     * @return 返回压缩包的路径 - 绝对路径
     */
    protected abstract String getZipType(String fileInDirAbsolutePath,
                                         String fileName,
                                         LanguageType checkLanguageType,
                                         AtomicReference<LanguageType> languageType);


    /**
     * @param fileInDirAbsolutePath 待解压的文件所在文件夹的绝对地址
     * @param fileName              待解压的文件名称
     * @param checkLanguageType     操作指定的文件类型
     * @param languageType
     * @param filterDirAbsolutePath 过滤的工作文件夹
     * @return
     * @throws IOException
     */
    public boolean doWork(String fileInDirAbsolutePath,
                          String fileName,
                          LanguageType checkLanguageType,
                          AtomicReference<LanguageType> languageType,
                          String filterDirAbsolutePath) throws IOException {

        /**
         * 获取压缩包的路径
         */
        String zipPath = this.getZipType(fileInDirAbsolutePath, fileName, checkLanguageType, languageType);
        log.info("压缩包的路径:{}", zipPath);
        if (null == languageType.get() || (null != languageType.get() && !languageType.get().equals(checkLanguageType))) {
            log.info("当前项目不是指定项目:{}", languageType.get());
            return false;
        } else {
            FileUtils.copyFileToDirectory(new File(zipPath), new File(filterDirAbsolutePath));
            return true;
        }
    }

}
