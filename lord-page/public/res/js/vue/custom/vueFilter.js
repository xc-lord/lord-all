//字符串反转
Vue.filter('reverse', function (value) {
    return value.split('').reverse().join('')
});
//时间格式化
Vue.filter('dateFormat', function (date, pattern) {
    if (date == undefined) {
        return "";
    }
    if (pattern == undefined) {
        pattern = "YYYY-MM-DD HH:mm:ss";//默认的时间格式
    }
    console.info("格式化时间");
    return moment(date).format(pattern);
});