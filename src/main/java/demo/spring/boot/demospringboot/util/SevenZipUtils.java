package demo.spring.boot.demospringboot.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

import java.io.*;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Slf4j
public class SevenZipUtils {

    public static boolean unzip(String zipFilePath, String zipFileName, String targetFileDir, ArchiveFormat archiveFormat) {
        boolean flag = false;
        //1.判断压缩文件是否存在，以及里面的内容是否为空
        File file = null;//压缩文件(带路径)
        ZipFile zipFile = null;
        file = new File(zipFilePath + "/" + zipFileName);
        System.out.println(">>>>>>解压文件【" + zipFilePath + "/" + zipFileName + "】到【" + targetFileDir + "】目录下<<<<<<");
        if (false == file.exists()) {
            System.out.println(">>>>>>压缩文件【" + zipFilePath + "/" + zipFileName + "】不存在<<<<<<");
            return false;
        } else if (0 == file.length()) {
            System.out.println(">>>>>>压缩文件【" + zipFilePath + "/" + zipFileName + "】大小为0不需要解压<<<<<<");
            return false;
        } else {
            //2.开始解压ZIP压缩文件的处理
            byte[] buf = new byte[1024];
            int readSize = -1;
            ZipInputStream zis = null;
            FileOutputStream fos = null;

            //解压7zip文件
            RandomAccessFile randomAccessFile = null;
            IInArchive inArchive = null;
            try {
                // 判断目标目录是否存在，不存在则创建
                File newdir = new File(targetFileDir);
                if (false == newdir.exists()) {
                    newdir.mkdirs();
                    newdir = null;
                }
                randomAccessFile = new RandomAccessFile(zipFilePath + "/" + zipFileName, "r");
                RandomAccessFileInStream t = new RandomAccessFileInStream(randomAccessFile);
                inArchive = SevenZip.openInArchive(archiveFormat, t);
                ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
                System.out.println("-----Hash-----+------Size------+-----FileName----");
                for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                    final int[] hash = new int[]{0};
                    System.out.println("item.isFolder()==" + item.isFolder());
                    if (!item.isFolder()) {
                        ExtractOperationResult result;
                        final long[] sizeArray = new long[1];
                        result = item.extractSlow(new ISequentialOutStream() {
                            public int write(byte[] data) throws SevenZipException {
                                //写入指定文件
                                FileOutputStream fos;
                                try {
                                    if (item.getPath().indexOf(File.separator) > 0) {
                                        String path = targetFileDir + File.separator + item.getPath().substring(0, item.getPath().lastIndexOf(File.separator));
                                        File folderExisting = new File(path);
                                        if (!folderExisting.exists())
                                            new File(path).mkdirs();
                                    }

                                    fos = new FileOutputStream(targetFileDir + File.separator + item.getPath(), true);
                                    System.out.println(">>>>>>保存文件至：" + targetFileDir + File.separator + item.getPath());
                                    fos.write(data);
                                    fos.close();
                                } catch (FileNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                hash[0] ^= Arrays.hashCode(data); // Consume data
                                sizeArray[0] += data.length;
                                return data.length; // Return amount of consumed data
                            }
                        });

                        if (result == ExtractOperationResult.OK) {
                            System.out.println(String.format("%9X | %10s | %s",
                                    hash[0], sizeArray[0], item.getPath()));
                        } else {
                            System.err.println("Error extracting item: " + result);
                        }
                    }
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("Error occurs: " + e);
                e.printStackTrace();
                System.exit(1);
            } finally {
                if (inArchive != null) {
                    try {
                        inArchive.close();
                    } catch (SevenZipException e) {
                        System.err.println("Error closing archive: " + e);
                    }
                }
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e) {
                        System.err.println("Error closing file: " + e);
                    }
                }
            }


        }

        return flag;
    }

    /**
     * @param fileInDirAbsolutePath 压缩包所文件夹绝对路径
     * @param zipFileName           压缩包名称
     * @param targetFileDir
     * @param encodeFunction
     * @return
     */
    public static boolean unzip(String fileInDirAbsolutePath,
                                String zipFileName,
                                String targetFileDir,
                                BiFunction<byte[], String, byte[]> encodeFunction) {
        boolean flag = false;
        //1.判断压缩文件是否存在，以及里面的内容是否为空
        File file = null;//压缩文件(带路径)
        ZipFile zipFile = null;
        file = new File(fileInDirAbsolutePath + "/" + zipFileName);
        log.info(">>>>>>解压文件【{}/{}】到【{}】目录下<<<<<<", fileInDirAbsolutePath, zipFileName, targetFileDir);
        if (false == file.exists()) {
            log.info(">>>>>>解压文件【{}/{}】不存在<<<<<<", fileInDirAbsolutePath, zipFileName);
            return false;
        } else if (0 == file.length()) {
            log.info(">>>>>>解压文件【{}/{}】大小为0不需要解压<<<<<<", fileInDirAbsolutePath, zipFileName);
            return false;
        } else {
            //2.开始解压ZIP压缩文件的处理
            byte[] buf = new byte[1024];
            int readSize = -1;
            ZipInputStream zis = null;
            FileOutputStream fos = null;

            //解压7zip文件
            RandomAccessFile randomAccessFile = null;
            IInArchive inArchive = null;
            try {
                // 判断目标目录是否存在，不存在则创建
                File newdir = new File(targetFileDir);
                if (false == newdir.exists()) {
                    newdir.mkdirs();
                    newdir = null;
                }
                randomAccessFile = new RandomAccessFile(fileInDirAbsolutePath + "/" + zipFileName, "r");
                RandomAccessFileInStream t = new RandomAccessFileInStream(randomAccessFile);
//                inArchive = SevenZip.openInArchive(archiveFormat, t);
                inArchive = SevenZip.openInArchive(null, t);//修改为null 就可以
                ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
                System.out.println("-----Hash-----+------Size------+-----FileName----");
                for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                    final int[] hash = new int[]{0};
                    if (!item.isFolder()) {
                        ExtractOperationResult result;
                        final long[] sizeArray = new long[1];
                        result = item.extractSlow(new ISequentialOutStream() {
                            public int write(byte[] data) {
                                //写入指定文件
                                FileOutputStream fos;
                                try {
                                    if (item.getPath().indexOf(File.separator) > 0) {
                                        String path = targetFileDir + File.separator + item.getPath().substring(0, item.getPath().lastIndexOf(File.separator));
                                        File folderExisting = new File(path);
                                        if (!folderExisting.exists())
                                            new File(path).mkdirs();
                                    }

                                    fos = new FileOutputStream(targetFileDir + File.separator + item.getPath(), true);
                                    log.info(">>>>>>保存文件至：{}", targetFileDir + File.separator + item.getPath());
                                    fos.write(encodeFunction.apply(data, item.getPath()));
                                    fos.close();
                                } catch (Exception e) {
                                    log.error("e:{}", e.toString(), e);
                                }
                                hash[0] ^= Arrays.hashCode(data); // Consume data
                                sizeArray[0] += data.length;
                                return data.length; // Return amount of consumed data
                            }
                        });
                        if (result == ExtractOperationResult.OK) {
                            log.error("Success extracting item: :{}", item.getPath());
                        } else {
                            log.error("Error extracting item: :{}", result);
                        }
                    }
                }
                flag = true;
            } catch (Exception e) {
                log.error("Error occurs:{}", e.toString(), e);
            } finally {
                if (inArchive != null) {
                    try {
                        inArchive.close();
                    } catch (SevenZipException e) {
                        log.error("Error closing archive:{}", e.toString(), e);
                    }
                }
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e) {
                        log.error("Error closing file:{}", e.toString(), e);
                    }
                }
            }


        }

        return flag;
    }


}
