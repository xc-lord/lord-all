var logger = require('log4js');
var moment = require('moment');//时间格式化
var infoLogger = logger.getLogger("INFO");
var errorLogger = logger.getLogger("ERROR");
var crypto = require('crypto');
var typeMap = {
    "[object Undefined]":"undefined",
    "[object Null]":"null",
    "[object Number]":"number",
    "[object Object]":"object",
    "[object Array]":"array",
    "[object Boolean]":"boolean",
    "[object Function]":"function",
    "[object String]":"string"
};

var $ = CommonUtils = {
    //判断字符串是否为空
    isEmpty:function(str) {
        if (str == null || str == undefined || str == '' || str == false) {
            return true;
        }
        return false;
    },
    //判断字符串是否不为空
    isNotEmpty:function(str) {
        return !this.isEmpty(str);
    },
    //转换为时间戳
    toTimestamp:function(time) {
        if(typeof time == 'number') {
            return time;
        }
        if(typeof time == 'object') {
            return Date.parse(time);
        }
    },
    default: function (source,target) {
        var self = this;
        for(var key in source){
            if(self.type(target[key]) == "undefined"){
                target[key] = source[key];
            }
        }
        return self;
    },
    try:function(callback,error){
        var self = this;
        return function(){
            try{
                callback && callback.apply(null,arguments);
            }catch(e){
                self.logger.error(e);
                error && error();
            }
        };
    },
    trycatch:function(fn){
        var self = this;
        try{
            if(fn){
                var args = Array.prototype.slice(arguments,1);
                fn.apply(null,args);
            }
        }catch(e){
            self.logger.error(e);
        }
    },
    deepClone : function(source){
        if(!source){
            return null;
        }
        //深度复制
        var self = this;
        var tmp = null;
        if(Object.prototype.toString.call(source) == "[object Array]"){
            //数组
            tmp = [];
            for(var i=0;i<source.length;i++){
                var value = source[i];
                if(typeof value == "object"){
                    tmp.push(self.deepClone(value));
                }else{
                    tmp.push(value);
                }
            }
        }else if(Object.prototype.toString.call(source) == "[object Object]"){
            tmp = {};
            for(var key in source){
                var value = source[key];
                if(typeof value == "object"){
                    tmp[key] = self.deepClone(value);
                }else{
                    tmp[key] = value;
                }
            }
        }
        return tmp;
    },
    type: function (obj) {
        return typeMap[Object.prototype.toString.call(obj)];
    },
    logger:{
        debug:function(){
            infoLogger.debug.apply(infoLogger,arguments);
        },
        info:function(){
            infoLogger.info.apply(infoLogger,arguments);
        },
        error:function(){
            errorLogger.error.apply(errorLogger,arguments);
        }
    },
    date:{
    },
    mapKey:function(map){
        var result = [];
        if(!map){
            return result;
        }
        for(var key in map){
            result.push(key);
        }
        return result;
    },
    mapValue:function(map){
        var result = [];
        for(var key in map){
            result.push(map[key]);
        }
        return result;
    },
    arrValue:function(arr,key){
        var self = this;
        var result = [];
        this.each(arr,function(){
            result.push(this[key]);
        });
        return result;
    },
    md5:function(str){
        return crypto.createHash('md5').update(str).digest('hex');
    },
    timestamp:function(){
        return new Date().getTime()/1000;
    },
    dateFormat:function(date, pattern) {
        if (date == undefined) {
            return "";
        }
        if (pattern == undefined) {
            pattern = "YYYY-MM-DD HH:mm:ss";//默认的时间格式
        }
        return moment(date).format(pattern);
    },
    each:function(arr,callback){
        if(!arr || arr.length ==0){
            return;
        }
        var self = this;
        for(var i=0;i<arr.length;i++){
            if(callback){
                var result = callback.call(arr[i],i,arr[i]);
                if(self.type(result) == "boolean"){
                    if(result){
                        continue;
                    }else{
                        break;
                    }
                }
            }
        }
    },
    def:function(value,def){
        return (this.isUndefined(value)||value=="")?def:value;
    }
};

CommonUtils.date.__defineGetter__("year",function(){
    return new Date().getFullYear();
});

CommonUtils.date.__defineGetter__("timestamp",function(){
    return new Date().getTime()/1000;
});

CommonUtils.date.__defineGetter__("month",function(){
    var month = new Date().getMonth()+1;
    return month<9?"0"+month:month;
});

CommonUtils.date.__defineGetter__("day",function(){
    var date = new Date().getDate();
    return date<9?"0"+date:date;
});

for(var key in typeMap){
    eval('$["is'+typeMap[key].charAt(0).toUpperCase()+typeMap[key].substring(1)+'"] = function(obj){\
        return $.type(obj) === "'+typeMap[key]+'";\
    }');

}

module.exports = CommonUtils;