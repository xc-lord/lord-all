(function() {
    'use strict';
    var fs = require('fs');
    var path = require('path');
    var logger = require('./logger')(path.basename(__filename));
    var dataUtils = require('./dataUtils');

    var appInit = {
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
        },
        loadInterceptor:function(app) {
            //登录拦截器，需要拦截静态页面，需要放在express.static上面
            app.use(function (req, res, next) {
                var url = req.originalUrl;
                var regx = "^/mis/\w*";
                //console.info("登录拦截器" + url + " = " + url.match(regx));
                if (url.match(regx) && url != "/mis/login.html") {
                    return dataUtils.doMisAdminAuth(req,res,next);
                }
                next();
            });
        }
    };

    module.exports = appInit;
})();