var globalConfig = {
    apiUrl : ""
};

//公共方法定义
var commonUtils = {
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
    /**
     * 验证表单的字段是否已经存在相同的记录
     * @param apiUrl    验证的APIUrl
     * @param rowName   属性名
     * @param rule      验证规则
     * @param value     属性值
     * @param callback  验证成功的回调方法
     * @returns {*}
     */
    formRowIsExist: function(apiUrl, rowName, rule, value, callback){
        if (!value) {
            return callback(new Error("此项不能为空"));
        }
        $.ajax({
            method: "get",
            url: apiUrl,
            data: {id:pageParam.id, rowValue:value, rowName:rowName},
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                return callback(new Error(res.msg));
            } else {
                callback();
            }
        });
    },
};

//页面的参数存储
var pageParam = {};
