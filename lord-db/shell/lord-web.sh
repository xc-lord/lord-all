#!/bin/sh
echo "Update from github.com!"
cd /data/app/src/lord-all
git pull origin master

echo "mvn install"
cd /data/app/src/lord-all
mvn clean install
if [ $? -eq 0 ];then echo "mvn install lord-all success!";else echo "mvn install lord-all fail!";exit $?;fi

ps -fe|grep lord-web-1.0-SNAPSHOT.jar |grep -v grep
if [ $? -ne 0 ]
then
echo "start lord-web process....."
else
echo "lord-web is runing....."
ps -ef | grep lord-web-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9
echo "shutdown lord-web!"
fi

echo "starting lord-web!"
rm -f /data/app/spring/lord-web-1.0-SNAPSHOT.jar
cp /data/app/src/lord-all/lord-web/target/lord-web-1.0-SNAPSHOT.jar /data/app/spring
cd /data/app/spring

nohup java -jar lord-web-1.0-SNAPSHOT.jar --spring.profiles.active=live >/data/app/logs/log-lord-web.log 2>&1 &
echo "spring boot lord-web server starting!"

tail -100f /data/app/logs/log-lord-web.log
