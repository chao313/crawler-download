package demo.spring.boot.demospringboot.controller.wind;

import com.alibaba.fastjson.annotation.JSONField;

public class MsgData {	
	
	@JSONField(name="FileName")
	private String fileName = "";
	@JSONField(name="TaskType")
	private String taskType = "";
	@JSONField(name="Data")
	private String data = "";
	@JSONField(name="PolicyId")
	private String policyId = "";
	@JSONField(name="OrgUrl")
    private String orgUrl = "";
	
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getTaskType() {
        return taskType;
    }
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getPolicyId() {
        return policyId;
    }
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }
    public String getOrgUrl() {
        return orgUrl;
    }
    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }
	
	
}
