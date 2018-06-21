/**
	Desc: 	广告位的元素的表单公共方法
	Author: xiaocheng
	Date: 	2017年08月03日 16:18:22
*/
var AdsElementFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//名称
        title: '',		//标题
        subTitle: '',		//子标题
        adsImg: '',		//图片地址
        adsUrl: '',		//跳转的链接
        tags: '',		//标签
        extension: '',		//扩展属性
        startTime: new Date(),		//开始时间
        endTime: new Date(moment().add(3, 'years')),		//结束时间
        orderValue: 0,		//排序值
        keyword: '',		//关键字
        targetId: '',		//元素id
        targetType: '',		//元素类型
        pageId: 0,		//页面id
        spaceId: 0,		//广告位Id
        tex: '',		//大文本
        fouce: false,	//是否高亮
        target: true,	//是否新窗口打开
        createTime: '',		//创建时间
        updateTime: '',		//最后修改时间
    },
    data: function() {
        var _self = this;
        function validateEndTime(rule, value, callback) {
            var startTime = _self.editForm.startTime;
            if (!startTime) {
                return callback(new Error("开始时间为空"));
            }
            if(startTime.getTime() >= value.getTime()) {
                return callback(new Error("结束时间不能小于开始时间"));
            }
            return callback();
        };
        return {
            editLoading:false,
            //表单验证规则
            editFormRules: {
                name: [
                    {required: true, message: '名称不能为空', trigger: 'blur'},
                ],
                startTime:[{type: 'date', required: true, message: '开始时间不能为空', trigger: 'blur'}],
                endTime: [
                    {type: 'date', required: true, message: '结束时间不能为空', trigger: 'blur'},
                    {
                        validator: validateEndTime,
                        trigger: 'blur'
                    }
                ],
            },
            //编辑界面数据
            editForm: {},
            //状态
            ads_AdsElementType: [],
            adsPage:{},
            adsSpace:{},
        };
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取下拉框选项
        $.ajax({
            url: '/api/mis/getEnumType.do',
            data: {cls: "ads_AdsElementType"},
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.ads_AdsElementType = res.data.ads_AdsElementType;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
		_self.editForm.startTime = commonUtils.toTimestamp(_self.editForm.startTime);//开始时间
		_self.editForm.endTime = commonUtils.toTimestamp(_self.editForm.endTime);//结束时间
		_self.editForm.createTime = commonUtils.toTimestamp(_self.editForm.createTime);//创建时间
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//最后修改时间
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/ads/adsElement/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/adsElement/list/" + _self.adsSpace.id;//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function (spaceId) {
        window.location.href = "#/adsElement/list/" + spaceId;//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(adsElementView) !== 'undefined' && adsElementView.dialogFormVisible) {			
			adsElementView.dialogFormClose();
		}
    }
};