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
  
#?
* mysql应该要独立出来?  




## 需要修改的
1.字段 增加首页访问地址/后台访问地址/账号密码/注意事项/更新时间
2.字段 增加状态(是否正常运行)
3.字段 增加状态(可用/废弃/暂定)
4.字段 增加容器id,用于 portainer url
5.字段 增加默认的img(便于预览)
6.前端 增加状态判断(停止 -> 展示启动 ; 启动 -> 展示停止)
7.前端 portainer -> 下拉框/弹出链接地址 (按钮太多,影响美观)
8.前端 源url修改为超链
9.前端 增加图片预览
10.增加 生成介绍
11.增加 镜像push


## 提给自动生成的
* 无法!=
* 无法like


docker tag 11_softview_32090  docker.io/chao313/11_softview_32090:latest
docker push docker.io/chao313/11_softview_32090:latest
