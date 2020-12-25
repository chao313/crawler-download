#使用

http://127.0.0.1:8083/download/swagger-ui.html#!/asp45controller/downloadDetailUsingGET


## 功能需求
* 下载发送到kafka
* 处理压缩包
  * 解压rar
  * 判断项目类型
  * 转码成utf-8
  * 生成docker镜像
   * 生成请求的地址
  * 上传本地文件管理服务
   * 生成文件的地址
* 辅助功能
  *直接从容器中获取code,压缩下载
  
## 第二批需求
* 生成完整的生成镜像的包
* 生成下载链接/app下,注意包含sql(直接从数据库中导出)
* 容器可以登陆进去,最好可以可视化编辑
* zip包需要可以下载
* 容器的修改后的也需要可以下载(包含sql语句)
* 需要可以标识成功的操作
  





## 需要修改的
[x] 字段 增加首页访问地址/后台访问地址/账号密码/注意事项/更新时间
[x] 字段 增加状态(是否正常运行)
[x] 字段 增加状态(可用/废弃/暂定)
[x] 字段 增加容器id,用于 portainer url
[x] 字段 增加默认的img(便于预览)
[*] 前端 增加状态判断(停止 -> 展示启动 ; 启动 -> 展示停止) / 变成小图标
[*] 前端 portainer -> 下拉框/弹出链接地址 (按钮太多,影响美观) / 变成portainer一样的小图标
[*] 前端 源url修改为超链
* 前端 增加图片预览
* 前端 增加批量操作
* 前端 增加状态(可用/废弃/暂定)下拉框
[*] 前端 状态放到展示的前端
[*] 增加 生成介绍
[*] 增加 下载zip包
* 增加 镜像push
* 增加 可以使用的镜像打包成可使用镜像-无需重新安装的镜像
  * 目前考虑,修改好容器的数据之后,复制app下的文件,导出php_test里的sql -> 生成一个新的zip包 -> 名字就是xx_pro.zip
    -> 生成pro的image考虑 -> 数据库外迁,创建用户，密码，设置权限（账号,密码,数据库都和id保持一致）
    -> 增加容器再生成(pro->pro)
    * 数据库导出: mysqldump  -u admin -p123456 php_test  > <id>.sql
    * 文件导出 docker cp 11_softview_62253_:/app ./<id>app
    * 文件/sql替换(账号密码去非常复杂的字符串 -> 方便替换) admin1111111 123456111111 (sql和后台管理的账号密码等同)
    * 创建账号密码,设置访问权限,导入数据库(ip地址如何处理？ip6-localhost??ENV HOSTNAME localhost111111??)
  * 相关需求：
    * 添加字段(详见下临时1)

* 增加 根据访问的ip来显示不同的地址，内外网
* 增加 安装步骤截图?
* 前端 查看介绍可以换行+图片(脱离asp300)
* 前端 编辑以弹出框的形式
* 前端 合并内网外网(根据服务)




## 提给自动生成的
* 无法!=
* 无法like


docker tag 11_softview_32090  docker.io/chao313/11_softview_32090:latest
docker push docker.io/chao313/11_softview_32090:latest
docker search 11_softview_32090
https://hub.docker.com/v2/repositories/chao313/?page_size=25&page=2&ordering=last_updated

## ？
* mysql应该要独立出来?
* 镜像是否需要限制使用时间 - 或者运行时需要验证? - 防止其他人使用
* 具体的需求 -> 步骤拆分
 * 镜像名称统一使用ASP_开头 + (PHP|JSP) + 端口号 + 唯一标识(e.g. ASP_PHP_7500_xxx)
 * 容器名称是镜像名称+下划线(e.g. ASP_PHP_7500_xxx_)
 * desc内部需要存放原生的代码,描述,asp300url,图片,docker命令
 * 需要打通ASP300的url -> zip包(直接下载+百度云);就是给一个url,可以直接生成docker
 * 需要一个镜像管理系统来管理这些

## 临时1
    private Integer id;
    private String criteriaid;  // 唯一id
    private String projectName;  // 项目-名称
    private String projectUpdateTime;  // 项目-更新时间
    private String projectType;  // 项目-类型(VIP/COMMON)
    private String projectPrice;  // 项目-价格
    private String projectPackageType;  // 项目-下载包类型(流/text)
    private String projectPanAddress;  // 项目-网盘地址
    private String projectRealFileName;  // 项目-真实文件名称                              pro_projectRealFileName
    private String projectHtmlBody;  // 项目-网页Body
    private String projectSourceUrl;  // 项目-源ASPURL
    private String projectLanguage;  // 项目-开发语言
    private String projectSize2;  // 项目-大小
    private String projectSizeNum;  // 项目-大小数字
    private String projectSizeType;  // 项目-大小type(k,M,G)
    private String projectImgs;  // 项目-介绍(图片)                                      projectImgs 考虑新增功能? -> 直接上传就行(增加字段值+保存图片)
    private String projectImgsDefault;  // 项目-默认的图片
    private String projectRuntime;  // 项目-运行环境
    private String projectOfficialWebsite;  // 项目-官网地址
    private String projectShowWebsite;  // 项目-展示网址
    private String projectDownloadUrls;  // 项目-下载URL
    private String projectDownloadSum;  // 项目-下载次数
    private String projectIntroduction;  // 项目-介绍(文字)
    private String projectStatus;  // 项目-状态,可用/废弃/暂定
    private String projectMark;  // 项目-备注
	                                                                                       private String project_sql;  // 项目-sql
    private String projectCanRunning;  // 项目-是否正常运行
    private String dockerImageName;  // docker-镜像-名称                                   pro_dockerImageName
    private String dockerImageShellRemove;  // docker-镜像-移除命令                        pro_dockerImageShellRemove
    private String dockerStatus;  // docker-状态,创建.运行中,停止,容器移除,镜像移除        pro_dockerStatus
    private String dockerContainerId;  // docker-容器-完整id                               pro_
    private String dockerContainerName;  // docker-容器-名称                               pro_
    private String dockerContainerPort;  // docker-端口号
    private String dockerContainerShellCreate;  // docker-容器-创建命令                    pro_(命令考虑移除？)
    private String dockerContainerShellRun;  // docker-容器-启动命令                       pro_
    private String dockerContainerShellStop;  // docker-容器-停止命令                      pro_
    private String dockerContainerShellRemove;  // docker-容器-移除命令                    pro_
    private String addressContainerOuter;  // 地址-容器外网
    private String addressContainerInner;  // 地址-容器内网
    private String addressProjectFront;  // 地址-项目前端
    private String addressProjectBackground;  // 地址-项目后台
    private String addressProjectBackgroundAccountPasswd;  // 地址-项目后台-账号密码
    private String updateTime;  // 更新时间
	                                                                                      考虑更简单的状态(镜像是否存在/容器是否存在/容器是否运行)
	                                                                                      docker_images_is_exist  docker_container_is_exist docker_container_is_running
	                                                                                      pro_docker_images_is_exist  pro_docker_container_is_exist pro_docker_container_is_running

CREATE TABLE `project_plus` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `criteriaId` longtext COLLATE utf8_bin COMMENT '唯一id',
  `project_name` text COLLATE utf8_bin COMMENT '项目-名称',
  `project_update_time` text COLLATE utf8_bin COMMENT '项目-更新时间',
  `project_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-类型(VIP/COMMON)',
  `project_price` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-价格',
  `project_package_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-下载包类型(流/text)',
  `project_pan_address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-网盘地址',
  `project_html_body` longtext COLLATE utf8_bin COMMENT '项目-网页Body',
  `project_source_url` text COLLATE utf8_bin COMMENT '项目-源ASPURL',
  `project_language` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-开发语言',
  `project_size2` text COLLATE utf8_bin COMMENT '项目-大小',
  `project_size_num` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-大小数字',
  `project_size_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-大小type(k,M,G)',
  `project_imgs` text COLLATE utf8_bin COMMENT '项目-介绍(图片)',
  `project_imgs_default` text COLLATE utf8_bin COMMENT '项目-默认的图片',
  `project_runtime` text COLLATE utf8_bin COMMENT '项目-运行环境',
  `project_official_website` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-官网地址',
  `project_show_website` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-展示网址',
  `project_download_urls` text COLLATE utf8_bin COMMENT '项目-下载URL',
  `project_download_sum` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-下载次数',
  `project_introduction` longtext COLLATE utf8_bin COMMENT '项目-介绍(文字)',
  `project_status` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-状态,可用/废弃/暂定',
  `project_mark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-备注',
  `project_can_running` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-是否正常运行',
  `project_sql` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-sql',
  `project_port` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-端口号',
  `address_container_outer` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址-容器外网',
  `address_container_inner` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址-容器内网',
  `address_project_front` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址-项目前端',
  `address_project_background` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址-项目后台',
  `address_project_background_account_passwd` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址-项目后台-账号密码',
  
  `dev_project_real_file_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '项目-真实文件名称',
  `dev_docker_image_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-镜像-名称',
  `dev_docker_image_shell_remove` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-镜像-移除命令',
  `dev_docker_container_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-容器-完整id',
  `dev_docker_container_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-容器-名称',
  `dev_docker_container_shell_create` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-容器-创建命令',
  `dev_docker_container_shell_run` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-容器-启动命令',
  `dev_docker_container_shell_stop` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-容器-停止命令',
  `dev_docker_container_shell_remove` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-容器-移除命令',
  `dev_docker_status_images_is_exist` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-状态-镜像是否存在',
  `dev_docker_status_container_is_exist` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-状态-容器是否存在',
  `dev_docker_status_container_is_running` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'docker-状态-容器是否运行',
  
  `pro_project_real_file_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-项目-真实文件名称',
  `pro_docker_image_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-镜像-名称',
  `pro_docker_image_shell_remove` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-镜像-移除命令',
  `pro_docker_container_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-容器-完整id',
  `pro_docker_container_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-容器-名称',
  `pro_docker_container_shell_create` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-容器-创建命令',
  `pro_docker_container_shell_run` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-容器-启动命令',
  `pro_docker_container_shell_stop` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-容器-停止命令',
  `pro_docker_container_shell_remove` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-容器-移除命令',
  `pro_docker_status_images_is_exist` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-状态-镜像是否存在',
  `pro_docker_status_container_is_exist` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-状态-容器是否存在',
  `pro_docker_status_container_is_running` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'pro-docker-状态-容器是否运行',


  `update_time` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index111` (`criteriaId`(100)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11322 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;