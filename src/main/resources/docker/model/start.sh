#!/bin/sh
rm -rf    /app/*
mv  /root/www/html/www/*   /app
mv  /root/www/html/www/.htaccess   /app
/run.sh