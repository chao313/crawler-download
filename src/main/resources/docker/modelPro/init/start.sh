#!/bin/sh
echo '172.17.0.1 host.docker.internal' >> /etc/hosts
sh  /app/init/init.sh
/run.sh