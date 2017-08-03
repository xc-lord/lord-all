/**
	Desc: 	页面的表单公共方法
	Author: xiaocheng
	Date: 	2017年08月03日 09:13:24
*/
var AdsPageFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//页面名称
        pageCode: '',		//页面代码
        pageImg: '',		//封面图片
        appType: '',		//平台类型(PC、WX、APP)
        pageType: '',		//页面类型：常规、专题
        pageState: '',		//页面状态
        seoDesc: '',		//SEO描述
        seoKeyword: '',		//SEO关键字
        seoTitle: '',		//SEO标题
        actIntro: '',		//活动介绍
        preheatTime: '',		//预热开始时间
        startTime: '',		//活动开始时间
        endTime: '',		//活动结束时间
        dynUrl: '',		//动态地址
        liveUrl: '',		//线上地址
        uiImg: '',		//UI图片
        remark: '',		//备注
        createTime: '',		//创建时间
        updateTime: '',		//最后修改时间
        orderValue: 0,		//排序值
        pageConfig: '',		//页面XML配置
    },
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/ads/adsPage/isExist.do',"name",rule, value, callback);
                    },
                    trigger: 'blur'
                }
            ],
            pageCode: [
                {required: true, message: '页面编码不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/ads/adsPage/isExist.do',"pageCode",rule, value, callback);
                    },
                    trigger: 'blur'
                }
            ],
            phone: [
                {required: true, message: '手机号不能为空', trigger: 'blur'}
            ],
        },
        //编辑界面数据
        editForm: {},
        //状态
        ads_AdsAppType: [],
        ads_AdsPageState: [],
        ads_AdsPageType: [],
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取下拉框选项
        $.ajax({
            url: '/api/mis/getEnumType.do',
            data: {cls: "ads_AdsAppType,ads_AdsPageState,ads_AdsPageType"},
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.ads_AdsAppType = res.data.ads_AdsAppType;
                _self.ads_AdsPageState = res.data.ads_AdsPageState;
                _self.ads_AdsPageType = res.data.ads_AdsPageType;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
		_self.editForm.preheatTime = commonUtils.toTimestamp(_self.editForm.preheatTime);//预热开始时间
		_self.editForm.startTime = commonUtils.toTimestamp(_self.editForm.startTime);//活动开始时间
		_self.editForm.endTime = commonUtils.toTimestamp(_self.editForm.endTime);//活动结束时间
		_self.editForm.createTime = commonUtils.toTimestamp(_self.editForm.createTime);//创建时间
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//最后修改时间
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/ads/adsPage/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/adsPage";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/adsPage";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(adsPageView) !== 'undefined' && adsPageView.dialogFormVisible) {			
			adsPageView.dialogFormClose();
		}
    }
};