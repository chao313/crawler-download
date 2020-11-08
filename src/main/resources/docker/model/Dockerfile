FROM mattrayner/lamp:latest-1404-php5
WORKDIR /root/www/html/
##选择add 自带解压
ADD biquge.tar.gz .
COPY import.sh /root/
COPY start.sh /root/
COPY install.sql /root/
COPY create_mysql_users.sh /
RUN  chmod 755 /root/*.sh && chmod 755 /*.sh
EXPOSE 80
CMD ["/root/start.sh"]