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
    /**
     * 上传图片前的验证事件
     * @param file      图片
     * @param _self     vue对象
     * @returns 是否可以上传
     */
    uploadImageCheck:function(file, _self) {
        const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/gif' || file.type === 'image/x-icon';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
            _self.$message.error('上传的图片只能是 JPG、PNG、GIF、ICON格式!');
        }
        if (!isLt2M) {
            _self.$message.error('上传的图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
    },
    /**
     * 上传图片成功的回调事件
     * @param res   服务器响应
     * @param file  图片
     * @param _self vue对象
     * @returns 上传后的图片地址
     */
    uploadImageSuccess:function(res, file, _self) {
        if(res.success) {
            return res.data.filePath;
        } else {
            _self.$message.error(res.msg);
        }
        return '';
    },
};

//页面的参数存储
var pageParam = {};
