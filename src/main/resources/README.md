#注意
swagger地址
http://localhost:8083/download/swagger-ui.html

http://localhost:8083/swagger-ui.html


* 具体的需求 -> 步骤拆分
 * 镜像名称统一使用ASP_开头 + (PHP|JSP) + 端口号 + 唯一标识(e.g. ASP_PHP_7500_xxx)
 * 容器名称是镜像名称+下划线(e.g. ASP_PHP_7500_xxx_)
 * desc内部需要存放原生的代码,描述,asp300url,图片,docker命令
 * 需要打通ASP300的url -> zip包(直接下载+百度云);就是给一个url,可以直接生成docker
 * 需要一个镜像管理系统来管理这些
