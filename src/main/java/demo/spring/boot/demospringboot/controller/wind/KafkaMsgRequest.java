package demo.spring.boot.demospringboot.controller.wind;


/**
 * kafka数据请求
 */
public class KafkaMsgRequest {

    // msgId
    public String msgID = "";
    // msghead
    public MsgHead msgHead = new MsgHead();
    // msgData
    public MsgData msgData = new MsgData();


    public KafkaMsgRequest() {

    }


    public String encodeData() {
        KafkaMsg kafkaMsg = new KafkaMsg();
        kafkaMsg.setData(JsonUtil.objectToJson(msgData));
        kafkaMsg.setMsgHead(JsonUtil.objectToJson(msgHead));
        kafkaMsg.setMsgID(msgID);
        String jsonCon = JsonUtil.objectToJson(kafkaMsg);
        return jsonCon;
    }

}

