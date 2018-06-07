(function() {
    'use strict';
    var template = require('art-template');//腾讯的模板引擎
    var moment = require('moment');//时间格式化
    var numeral = require('numeral');//数字格式

    var ViewTemplate = {
        init: function (app) {
            template.config('base', '');
            template.config('extname', '.html');
            template.config('cache', false);
            template.config("escape", false);
            //template.config('compress',true);//压缩输出的html
            this.init_template_fun(template);//添加辅助方法
            app.engine('.html', template.__express);
            app.set('view engine', 'html');
        },
        init_template_fun: function (template) {
            var self = this;
            /*
             * 格式化时间，用法{{createTime | dateFormat:'YYYY/MM/DD HH:mm:ss'}}
             */
            template.helper("dateFormat", function (date, pattern) {
                if (date == undefined) {
                    return "";
                }
                if (pattern == undefined) {
                    pattern = "YYYY-MM-DD HH:mm:ss";//默认的时间格式
                }
                return moment(date).format(pattern);
            });
            /*
             * 字符串替换，用法{{123.456 | numFormat:'0.00'}}
             */
            template.helper("numFormat", function (num, pattern) {
                if (num == undefined) {
                    return "";
                }
                if (pattern == undefined) {
                    pattern = "0.00";//默认的时间格式
                }
                var number = numeral(num);
                return number.format(pattern);
            });
            /*
             * 字符串替换，用法{{error.stack | strReplace:/\n/g,"<br/>"}}
             */
            template.helper("strReplace", function (str, oldStr, newStr) {
                if (oldStr == undefined || newStr == undefined) {
                    return "";
                }
                return str.replace(oldStr,newStr);
            });
        }
    };

    module.exports = ViewTemplate;
})();