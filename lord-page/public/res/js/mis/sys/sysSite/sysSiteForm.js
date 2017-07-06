/**
	Desc: 	站点的表单公共方法
	Author: xiaocheng
	Date: 	2017年07月01日 15:12:19
*/
var SysSiteFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//站点名称
        siteImg: '',		//站点图片
        intro: '',		//描述
        letter: '',		//拼音
        domainName: '',		//域名
        ipAddress: '',		//IP地址
        orderValue: 0,		//排序
        createTime: '',		//创建时间
        updateTime: '',		//更新时间
        removed: false,	//是否删除
    },
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/sys/sysSite/isExist.do',"name",rule, value, callback);
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
        sysSiteStatus: []
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取下拉框选项
        $.ajax({
            url: '/api/mis/getEnumType.do',
            data: {cls: "MisUserStatus"},
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.sysSiteStatus = res.data.MisUserStatus;
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
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//更新时间
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/sys/sysSite/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/sysSite";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/sysSite";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(sysSiteView) !== 'undefined' && sysSiteView.dialogFormVisible) {			
			sysSiteView.dialogFormClose();
		}
    }
};