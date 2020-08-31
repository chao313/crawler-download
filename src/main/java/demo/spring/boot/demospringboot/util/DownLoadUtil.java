package demo.spring.boot.demospringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class DownLoadUtil {


    /**
     * 根据网络URL下载文件
     * 使用的是apache 的 IOUtils
     *
     * @param url
     * @return 字节
     * @throws IOException
     */
    public static byte[] downloadFileByUrl(String url) throws IOException {
        return IOUtils.toByteArray(new URL(url));
    }


    /**
     * 根据网络URL下载文件
     *
     * @param url      文件所在地址
     * @param fileName 指定下载后该文件的名字
     * @param savePath 文件保存根路径
     */
    public static void downloadFileByUrl(String url, String fileName, String savePath) {
        URL urlObj = null;
        URLConnection conn = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        BufferedOutputStream bos = null;
        try {
            // 1.建立网络连接
            urlObj = new URL(url);
            // 2.打开网络连接
            conn = urlObj.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 3.得到输入流
            inputStream = conn.getInputStream();
            bis = new BufferedInputStream(inputStream);

            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            // 文件的绝对路径
            String filePath = savePath + File.separator + fileName;
            File file = new File(filePath);
            // 4.
            outputStream = new FileOutputStream(file);
            bos = new BufferedOutputStream(outputStream);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            System.out.println("info:" + url + " download success,fileRename=" + fileName);
        } catch (MalformedURLException e) {
            System.out.println("世界上最遥远的距离就是没有网，检查设置");
            System.out.println("info:" + url + " download failure");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("您的网络连接打开失败，请稍后重试！");
            System.out.println("info:" + url + " download failure");
            e.printStackTrace();
        } finally {// 关闭流
            try {
                if (bis != null) {// 关闭字节缓冲输入流
                    bis.close();
                }

                if (inputStream != null) {// 关闭字节输入流
                    inputStream.close();
                }
                if (bos != null) {// 关闭字节缓冲输出流
                    bos.close();
                }
                if (outputStream != null) {// 关闭字节输出流
                    outputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
