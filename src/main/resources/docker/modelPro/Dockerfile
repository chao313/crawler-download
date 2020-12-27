FROM mattrayner/lamp:latest-1404-php5
WORKDIR /app
##选择add 自带解压
COPY create_mysql_users.sh /
COPY import.sh /root/
COPY init/init.sql /root/init/
COPY sql/db.sql /root/sql/
COPY start.sh /root/
RUN  chmod 755 /root/*.sh && chmod 755 /*.sh
ADD app .
ADD dsc .
EXPOSE 80
CMD ["/root/start.sh"]