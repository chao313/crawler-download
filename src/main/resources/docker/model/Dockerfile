FROM mattrayner/lamp:latest-1404-php5
WORKDIR /root/www/html/www/
##选择add 自带解压
COPY create_mysql_users.sh /
COPY import.sh /root/
COPY start.sh /root/
RUN  chmod 755 /root/*.sh && chmod 755 /*.sh
COPY install.sql /root/
ADD code .
ADD dsc .
EXPOSE 80
CMD ["/root/start.sh"]