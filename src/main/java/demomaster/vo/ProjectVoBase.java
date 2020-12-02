package demomaster.vo;


import lombok.Data;

/**
 * 这个为了做到
 */
@Data
public class ProjectVoBase {

    private Integer id;
    private String criteriaid;  // ASP唯一id 
    private String projectName;  // 项目名称 
    private String projectUpdateTime;  // 项目更新时间 
    private String projectType;  // 类型(VIP/COMMON) 
    private String projectZipStatus;  // 项目的下载包类型(流/盘) 
    private String projectPanAddress;  // 项目的网盘地址 
    private String fileRealName;  // 真实文件名称 
    private String htmlBody;  // 网页Body
    private String sourceUrl;  // ASPURL 
    private String language;  // 项目语言 
    private String size2;  // 项目大小 
    private String sizeNum;  // 项目大小数字 
    private String sizeType;  // 项目大小type(k,M,G) 
    private String officialWebsite;  // 官网地址 
    private String showWebsite;  // 展示网址 
    private String downloadSum;  // 项目下载次数 
    private String introduction;  // 项目介绍(文字) 
    private String contentImgs;  // 项目介绍(图片) 
    private String runtime;  // 运行环境 
    private String downloadUrls;  // 项目下载URL 


}
