(function() {
    'use strict';
    var path = require('path');
    var logger = require('./logger')(path.basename(__filename));
    var artTemplate = require('art-template');//腾讯的模板引擎
    var moment = require('moment');//时间格式化
    var numeral = require('numeral');//数字格式
    const cheerio = require('cheerio');//Html处理
    const defaultImg = "http://www.wutuojia.com/images/front/grey.png";//默认图片

    //随机的默认图片
    const imageArr = [
        "http://img2.wutuojia.com/pic/sq_middle/inspire/su18/20161130/174813_223126.jpg",
        "http://img2.wutuojia.com/pic/sq_middle/inspire/su17/20160811/155807_754360.jpg",
        "http://img2.wutuojia.com/pic/sq_middle/inspire/su17/20160706/174342_903596.jpg",
        "http://img2.wutuojia.com/pic/sq_middle/inspire/su18/20160630/171620_410457.jpg",
        "http://img2.wutuojia.com/pic/sq_middle/cms/send/20160104/155752_697085.jpg",
        //defaultImg,
        "http://img2.wutuojia.com/pic/sq_middle/cms/send/20151105/143207_468996.jpg"
    ];

    var viewTemplate = {
        /** 腾讯的模板引擎初始化 */
        init: function (app) {
            this.init_template_fun(artTemplate);//添加辅助方法
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
        /** 初始化模板的格式化方法 */
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
             * 显示图片
             */
            helper.showImg = function(obj) {
                if(obj && obj.trim() != '')
                    return global.imageSite + obj;
                var num = Math.floor(Math.random()*(imageArr.length));
                return imageArr[num];
            };
            /*
             * 显示PC内容
             */
            helper.showPC = function(obj) {
                if(obj) {
                    //加载内容为html，不转义为Soap编码
                    var $ = cheerio.load(obj, {decodeEntities: false});
                    $('img').each(function(i, elem) {
                        //图片伪静态处理
                        var src = $(this).attr("src");
                        $(this).addClass("lazy");
                        $(this).attr("data-original", src);
                        $(this).attr("src", defaultImg);
                    });
                    return $("body").html();
                }
                return obj;
            };
            /*
             * 显示MIP内容
             */
            helper.showMip = function(obj) {
                if(obj) {
                    //加载内容为html，不转义为Soap编码
                    var $ = cheerio.load(obj, {decodeEntities: false});
                    $('img').each(function(i, elem) {
                        var src = $(this).attr("src");
                        var width = $(this).attr("d-width");//图片原宽度
                        var height = $(this).attr("d-height");//图片原高度
                        if(!width) width = 412;
                        if(!height) height = 412;

                        //图片处理成MIP的图片格式
                        $(this).replaceWith('<mip-img\
                        layout="responsive"\
                        width="' + width + '"\
                        height="' + height + '"\
                        src="' + src + '"></mip-img>');
                    });
                    return $("body").html();
                }
                return obj;
            };
            /*
             * 获取属性，自动判断是否为空
             */
            helper.xv = function(obj, paramStr) {
                if(!paramStr)
                    return '';
                var paramArr = paramStr.split('.');
                var val = obj;
                var count = 0;
                for(var i=0; i<paramArr.length; i++ ){
                    var param = paramArr[i];
                    var paramObj = val[param];
                    if(paramObj) {
                        val = paramObj;
                        count++;
                    } else {
                        break;
                    }
                }
                if(count == paramArr.length)
                    return val;
                else
                    return '';
            };
            helper.showJson = function(jsonstr, paramStr) {
                if(jsonstr && jsonstr.trim() != '') {
                    var json = JSON.parse(jsonstr);
                    return helper.xv(json, paramStr);
                }
                return "";
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

    module.exports = viewTemplate;
})();