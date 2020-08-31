package demo.spring.boot.demospringboot.resource.vo;

public class FileDetail {

    private String fileName;//文件名称

    private String preRelationViewUrl;//预览的相对url

    private String preAbsolutionViewUrl;//预览的绝对url

    private String downloadUrl;//预览的绝对url

    private String createTime;//创建的时间 YYYY-MM-dd HH:MM:SS

    private String type;//文件类型 jpg,png,pdf

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPreRelationViewUrl() {
        return preRelationViewUrl;
    }

    public void setPreRelationViewUrl(String preRelationViewUrl) {
        this.preRelationViewUrl = preRelationViewUrl;
    }

    public String getPreAbsolutionViewUrl() {
        return preAbsolutionViewUrl;
    }

    public void setPreAbsolutionViewUrl(String preAbsolutionViewUrl) {
        this.preAbsolutionViewUrl = preAbsolutionViewUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "FileDetail{" +
                "fileName='" + fileName + '\'' +
                ", preRelationViewUrl='" + preRelationViewUrl + '\'' +
                ", preAbsolutionViewUrl='" + preAbsolutionViewUrl + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
