#!/bin/sh
#rm -rf    /app/*
#echo '172.17.0.1 host.docker.internal' >> /etc/hosts
mv  /root/www/html/www/*   /app
mv  /root/www/html/www/.htaccess   /app
sh /app/init/init.sql
/run.sh