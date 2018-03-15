(function() {
    'use strict';

    var path = require('path');
    var logger = require('./logger')(path.basename(__filename));
    var request = require('request');
    var utils = require('./utils');
    var async = require('async');

    var dataFactory = {
        normalApi: function(api, req) {
            /**
             * 规范化API数据结构
             */
            var key, origin;

            if (utils.notObject(api)) return;
            if (utils.isObject(req)) {
                origin = req.protocol + '://' + req.hostname;
            }

            for (key in api) {
                if (api[key].hasOwnProperty('url')) {
                    if (api[key].url.indexOf('http') < 0) {
                        api[key].url = origin + api[key].url;
                    }

                    if (api[key].hasOwnProperty('type') && utils.isString(api[key].type)) {
                        api[key].type = api[key].type.toUpperCase();
                    } else {
                        api[key].type = 'GET';
                    }
                }
            }
        },
        data: function(req, api, callback) {
            var self = this;

            if (utils.notObject(api)) return;

            self.normalApi(api, req);

            var key, tmp, api_array = [];

            for (key in api) {
                tmp = {};

                tmp.title = key;
                tmp.body  = api[key];

                api_array.push(tmp);
            }

            var s = new Date().getTime();
            async.map(api_array, function(item, collector) {
                if ('GET' === item.body.type) {
                    self.get(item.body.url, item.body.urlParams, item.body.headers, function(err, resp, msg) {
                        tmp = {};

                        if (!err) {
                            tmp.title = item.title;
                            tmp.msg   = msg;
                            try {
                                tmp.body = JSON.parse(resp);
                            } catch(error) {
                                tmp.body = resp;
                            }
                        } else {
                            logger.error(resp);
                        }
                        
                        collector(err, tmp);
                    });
                }

                if ('POST' === item.body.type) {
                    self.post(item.body.url, item.body.urlParams, item.body.headers, function(err, resp, msg) {
                        tmp = {};

                        if (!err) {
                            tmp.title = item.title;
                            tmp.msg   = msg;
                            try {
                                tmp.body = JSON.parse(resp);
                            } catch(error) {
                                tmp.body = resp;
                            }
                        } else {
                            logger.error(resp);
                        }

                        collector(err, tmp);
                    });
                }

            }, function(err, results) {
                var e = new Date().getTime();

                logger.info('接口获取数据总共花费时间：'+(e-s)+'ms');

                if (utils.isFunction(callback)) callback(err, results);
            });
        },
        formatData: function(title, data, req, resource) {
            data = data || {};

            data.__title__ = title;
            data.__requestMessage__ = [];

            resource.forEach(function(item) {
                data[item.title] = item.body;
                data.__requestMessage__.push(item.msg);
            });
        },
        css: function(data, arr) {
            data = data || {};
            data.__css__ = arr;
        },
        js: function(data, arr) {
            data = data || {};
            data.__js__ = arr;
        },
        get: function(url, params, headers, callback) {
            var s = new Date().getTime();

            request.get({
                url: url,
                qs: params,
                headers: headers || null
            }, function(error, response, body) {
                var e = new Date().getTime();

                var message = '访问接口：'+response.request.method+' '+
                                        response.statusCode+' '+
                                        response.request.uri.href+' '+(e-s)+'ms';

                if (!error && response.statusCode == 200) {
                    logger.info(message);
                    if (utils.isFunction(callback)) callback(null, body, message);

                } else {
                    logger.error(message);
                    if (utils.isFunction(callback)) callback(error, body, message);
                }
            });
        },
        post: function(url, params, headers, callback) {
            var s = new Date().getTime();

            request.post({
                url: url,
                qs: params,
                headers: headers || null
            }, function(error, response, body) {
                var e = new Date().getTime();

                var message = '访问接口：'+response.request.method+' '+
                                        response.statusCode+' '+
                                        response.request.uri.href+' '+(e-s)+'ms';

                if (!error && response.statusCode == 200) {
                    logger.info(message);
                    if (utils.isFunction(callback)) callback(null, body, message);

                } else {
                    logger.error(message);
                    if (utils.isFunction(callback)) callback(error, body, message);
                }
            });
        }
    };

    module.exports = dataFactory;
})();