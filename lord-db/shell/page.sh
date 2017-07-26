#!/bin/sh
ps -fe|grep node |grep -v grep
if [ $? -ne 0 ]
then
echo "start node process....."
cd /data/app/server/node/lord-page/
nohup node bin/www >/data/app/server/node/log-lord-page.log 2>&1 &
echo "lord-page nodejs server starting !"
else
echo "node is runing....."
ps -ef | grep node | grep -v grep | awk '{print $2}' | xargs kill -9
cd /data/app/server/node/lord-page/
nohup node bin/www >/data/app/server/node/log-lord-page.log 2>&1 &
echo "lord-page nodejs server restarting !"
fi
