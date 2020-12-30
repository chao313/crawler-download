FROM mattrayner/lamp:latest-1404-php5
WORKDIR /app
##选择add 自带解压
COPY /init/start.sh /root/
COPY /init/run.sh /
RUN  chmod 755 /root/*.sh && chmod 755 /*.sh
ADD app .
EXPOSE 80
CMD ["/root/start.sh"]

#容器路径
##/app
##app/init/init.sh
##app/init/db.sql
