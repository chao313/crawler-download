package demo.spring.boot.demospringboot.controller.wind;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Date;

@Slf4j
public class ProducerUpload {

    private static FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");


    public static KafkaMsgRequest generateKafkaRequestMsg(
            String policyID,
            String topic,
            String fileName,
            byte[] fileBytes
    ) {
        KafkaMsgRequest msgRequest = new KafkaMsgRequest();
        msgRequest.msgID = fileName;
        msgRequest.msgHead.setEncType("ORI_FILE");
        msgRequest.msgHead.setTopic(topic);
        msgRequest.msgHead.setUpdateTime(fastDateFormat.format(new Date()));
        msgRequest.msgData.setFileName(fileName);
        msgRequest.msgData.setOrgUrl("");
        msgRequest.msgData.setPolicyId(policyID);
        String data = Base64.encodeBase64String(fileBytes);
        msgRequest.msgData.setData(data);
        log.info("生成结束:");
        return msgRequest;
    }

}
