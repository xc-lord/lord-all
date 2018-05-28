#!/bin/sh
echo "Update from github.com!"
cd /data/app/src/lord-all
git pull origin master

ps -fe|grep node |grep -v grep
if [ $? -ne 0 ]
then
echo "node lord-page is not running....."
echo "start node lord-page process....."
else
echo "node lord-page is running....."
echo "shutdown node lord-page!"
ps -ef | grep node | grep -v grep | awk '{print $2}' | xargs kill -9
fi

echo "copy file to server path!"
rm -fr /data/app/server/node/lord-page/
cp -r /data/app/src/lord-all/lord-page/ /data/app/server/node
echo "node lord-page install project!"
cd /data/app/server/node/lord-page
cnpm install
nohup node bin/www >/data/app/server/node/log-lord-page.log 2>&1 &
echo "node lord-page server starting!"
