package demo.spring.boot.demospringboot.controller.wind;

import com.alibaba.fastjson.annotation.JSONField;

public class KafkaMsg {
	@JSONField(name="MsgHead")
	String msgHead = null;
	@JSONField(name="MsgID")
	String msgID = "";
	@JSONField(name="Data")
	String data = "";
	
	public String getMsgHead() {
		return msgHead;
	}

	public void setMsgHead(String msgHead) {
		this.msgHead = msgHead;
	}

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}