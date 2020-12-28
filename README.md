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
    * 数据库导出: mysqldump  -u admin -p123456 php_test111111  > <id>.sql
    * 文件导出 docker cp 11_softview_62253_:/app ./<id>app
    * 文件/sql替换(账号密码去非常复杂的字符串 -> 方便替换) admin1111111 123456111111 (sql和后台管理的账号密码等同)
    * 创建账号密码,设置访问权限,导入数据库(ip地址如何处理 localhost111111 ,宿主机ip ,数据库名称 php_test111111)
  * 相关需求：
    * 添加字段(详见下临时1)

* 增加 根据访问的ip来显示不同的地址，内外网
* 增加 安装步骤截图?
[x]前端 查看介绍可以换行+图片(脱离asp300)
* 前端 编辑以弹出框的形式
* 前端 合并内网外网(根据服务)
* 增加 devToPro/proToPro
* 增加 pro容器的reset(sql)





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
   docker create --name mysql_pro -p 3306:3306  --privileged=true  -v /data/docker/mysql_pro:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456  -ti docker.io/mysql:5.6
