#!/bin/sh
ps -fe|grep node |grep -v grep
if [ $? -ne 0 ]
then
echo "start node lord-page process....."
echo "Update from github.com!"
cd /data/app/src/lord-all
git pull origin master
echo "copy file to server path!"
rm -fr /data/app/server/node/lord-page/
cp -r /data/app/src/lord-all/lord-page/ /data/app/server/node
echo "node lord-page install project!"
cd /data/app/server/node/lord-page
cnpm install
nohup node bin/www >/data/app/server/node/log-lord-page.log 2>&1 &
echo "node lord-page server starting!"
else
echo "node lord-page is runing....."
echo "Update from github.com!"
cd /data/app/src/lord-all
git pull origin master
echo "shutdown node lord-page!"
ps -ef | grep node | grep -v grep | awk '{print $2}' | xargs kill -9
echo "copy file to server path!"
rm -fr /data/app/server/node/lord-page/
cp -r /data/app/src/lord-all/lord-page/ /data/app/server/node
echo "node install project!"
cd /data/app/server/node/lord-page
cnpm install
nohup node bin/www >/data/app/server/node/log-lord-page.log 2>&1 &
echo "node lord-page server restarting!"
fi
