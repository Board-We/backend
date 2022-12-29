FROM mysql:8.0.22

ADD ./conf.d /etc/mysql/conf.d
ADD ./initdb.d /docker-entrypoint-initdb.d

EXPOSE 3306

CMD ["mysqld"]