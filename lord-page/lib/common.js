(function() {
    'use strict';

    var path = require('path');
    var logger = require('./logger')(path.basename(__filename));
    var utils = require('./utils');
    var fs = require('fs');

    var common = {
        interceptor: function(req, res) {
            var self = this;

            logger.info('调用了一次拦截器！');

            //重写res.render()
            var render = res.render.bind(res);
            res.render = function(name, options, callback){
                return render(name, options, function(err, str){
                    if (err) return req.next(err);
                    
                    if (utils.isFunction(callback)) callback(err, str);

                    if ('{Template Error}'===str) {
                        if (process.env.NODE_ENV==='Production') {
                            // 模板渲染错误时，在正式环境下，加载指定的提示错误的页面
                            fs.readFile('./views/partials/error.html', 'utf-8', function(error, data) {
                                if(!error) str = data;

                                try{
                                    res.send(str);
                                }catch(e){
                                    logger.error(e);
                                }
                            });
                        } else {
                            // 模板渲染错误时，在非正式环境下，输出调试的信息
                            var debugStyle = '<style>H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}</style>';
                            var debugMessage = '<h1 style="text-align:center;">模板渲染错误，接口<a href="http://www.bejson.com/jsonviewernew/" target="_blank" style="color:#fff;">返回数据</a>如下</h1>';
                            
                            options.__requestMessage__.forEach(function(item) {
                                if (item.indexOf(' 200 ')>=0){
                                    debugMessage += '<h2>'+item+'</h2>';
                                } else {
                                    debugMessage += '<h2 style="background:red;">'+item+'</h2>';
                                }
                            });

                            debugMessage += JSON.stringify(options);

                            try{
                                res.send(debugStyle+debugMessage);
                            }catch(e){
                                logger.error(e);
                            }
                        }
                    } else {
                        // 模板渲染正常，输出正确的页面信息
                        try{
                            res.send(str);
                        }catch(e){
                            logger.error(e);
                        }
                    }
                });
            };

            //重写res.sendFile()
            var sendFile = res.sendFile.bind(res);
            res.sendFile = function(filepath, options, callback) {
                if (utils.notFunction(callback)) {
                    if (utils.isFunction(options)) {
                        callback = options;
                    }
                }

                if (utils.notObject(options)) {
                    options = {
                        root: path.join(__dirname, '../public'),
                        dotfiles: 'deny',
                        headers: {
                            'x-timestamp': Date.now(),
                            'x-send': true
                        }
                    };
                }

                return sendFile(filepath, options, callback);
            };

            req.next();
        },
        loadRoutes: function(app) {
            /**
             * 加载 routes 目录下的所有路由文件
             */
            var routerPath = path.join(__dirname, '../routes/'),
                files = fs.readdirSync(routerPath),
                router,
                file;

            for(var i=0; i<files.length; i++){
                file = files[i];
                if(file.indexOf('.') === 0) continue;

                router = require(routerPath+file);
                app.use('', router);

                logger.info('加载路由：'+routerPath+file);
            }
        }
    };

    module.exports = common;
})();