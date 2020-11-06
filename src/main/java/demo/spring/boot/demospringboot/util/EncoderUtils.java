package demo.spring.boot.demospringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.InputStream;

@Slf4j
public class EncoderUtils {

    public static String getCharset(InputStream is) {

        UniversalDetector detector = new UniversalDetector(null);
        try {
            byte[] bytes = new byte[1024];
            int nread;
            if ((nread = is.read(bytes)) > 0 && !detector.isDone()) {
                detector.handleData(bytes, 0, nread);
            }
        } catch (Exception localException) {
            log.info("detected code:", localException);
        }
        detector.dataEnd();
        String encode = detector.getDetectedCharset();
        /** default UTF-8 */
        if (StringUtils.isEmpty(encode)) {
            encode = "UTF-8";
        }
        detector.reset();
        return encode;
    }

    /**
     * 获取
     * @param bytes
     * @return
     */
    public static String getCharset(byte[] bytes) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encode = detector.getDetectedCharset();
        /** default UTF-8 */
//        if (StringUtils.isEmpty(encode)) {
//            encode = "UTF-8";
//        }
        detector.reset();
        return encode;
    }
}
