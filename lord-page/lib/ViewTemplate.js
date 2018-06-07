(function() {
    'use strict';
    var template = require('art-template');//腾讯的模板引擎
    var moment = require('moment');//时间格式化
    var numeral = require('numeral');//数字格式

    var ViewTemplate = {
        init: function (app) {
            this.init_template_fun(template);//添加辅助方法
            //app.engine('.html', template.__express);
            app.engine('html', require('express-art-template'));
            app.set('view engine', 'html');
            app.set('view options', {
                debug: process.env.NODE_ENV !== 'Production',
                escape: false,  // 是否开启对模板输出语句自动编码功能。为 false 则关闭编码输出功能
                extname: '.html',  // 默认后缀名。如果没有后缀名，则会自动添加 extname
                bail: false,  // bail 如果为 true，编译错误与运行时错误都会抛出异常
                onerror: function(error, options) {
                    // 错误事件。仅在 bail 为 false 时生效
                    logger.error(error);
                }
            });
        },
        init_template_fun: function (template) {
            var self = this;
            var helper = template.defaults.imports;
            helper.timestamp = function(value) {
                return value;
            };
            helper.toJSON = function(obj) {
                return JSON.stringify(obj);
            };
            /*
             * 格式化时间，用法{{createTime | dateFormat:'YYYY/MM/DD HH:mm:ss'}}
             */
            helper.dateFormat = function (date, pattern) {
                if (date == undefined) {
                    return "";
                }
                if (pattern == undefined) {
                    pattern = "YYYY-MM-DD HH:mm:ss";//默认的时间格式
                }
                return moment(date).format(pattern);
            };
            /*
             * 字符串替换，用法{{123.456 | numFormat:'0.00'}}
             */
            helper.numFormat = function (num, pattern) {
                if (num == undefined) {
                    return "";
                }
                if (pattern == undefined) {
                    pattern = "0.00";//默认的时间格式
                }
                var number = numeral(num);
                return number.format(pattern);
            };
            /*
             * 字符串替换，用法{{error.stack | strReplace:/\n/g,"<br/>"}}
             */
            helper.strReplace = function (str, oldStr, newStr) {
                if (oldStr == undefined || newStr == undefined) {
                    return "";
                }
                return str.replace(oldStr,newStr);
            };
        }
    };

    module.exports = ViewTemplate;
})();