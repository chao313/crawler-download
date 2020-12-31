#!/bin/sh
echo '172.17.0.1 host.docker.internal' >> /etc/hosts
sh  /app/init/init.sh
/run.sh
#常驻服务
nohup tail -f /bin/sh &