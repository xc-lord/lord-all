/**
	Desc: 	广告位的表单公共方法
	Author: xiaocheng
	Date: 	2017年08月03日 11:12:10
*/
var AdsSpaceFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//名称
        keyword: '',		//关键字
        subKeyword: '',		//子关键字
        adsType: '',		//图片、文字、商品、文章
        adsNum: 0,		//元素的数量，0不限制数量
        showState: false,	//显示1、隐藏0
        spaceImg: '',		//图片
        spaceUrl: '',		//链接
        level: 0,		//等级
        parentId: 0,		//父节点Id
        pageId: 0,		//页面id
        remark: '',		//备注
        intro: '',		//介绍
        createTime: '',		//创建时间
        updateTime: '',		//最后修改时间
        orderValue: 0,		//排序
    },
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/ads/adsSpace/isExist.do',"name",rule, value, callback);
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
        adsSpaceStatus: []
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取下拉框选项
        $.ajax({
            url: '/api/mis/getEnumType.do',
            data: {cls: "mis_MisUserStatus"},
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.adsSpaceStatus = res.data.mis_MisUserStatus;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
		_self.editForm.createTime = commonUtils.toTimestamp(_self.editForm.createTime);//创建时间
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//最后修改时间
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/ads/adsSpace/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/adsSpace";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/adsSpace";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(adsSpaceView) !== 'undefined' && adsSpaceView.dialogFormVisible) {			
			adsSpaceView.dialogFormClose();
		}
    }
};