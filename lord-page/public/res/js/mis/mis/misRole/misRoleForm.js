/**
	Desc: 	用户角色的表单公共方法
	Author: xiaocheng
	Date: 	2017年05月08日 12:14:33
*/
var MisRoleFromCommon = {
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ],
			roleCode: [
                {required: true, message: '名称不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/mis/misRole/isExist.do',"roleCode",rule, value, callback);
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
        misRoleStatus: []
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取下拉框选项
        $.ajax({
            async: true,
            url: '/api/mis/getEnumType.do',
            data: {cls: "MisUserStatus"},
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.misRoleStatus = res.data.misRoleStatus;
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
            async: true,
            method: "post",
            url: '/api/admin/mis/misRole/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/misRole";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/misRole";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(misRoleView) !== 'undefined' && misRoleView.dialogFormVisible) {			
			misRoleView.dialogFormClose();
		}
    }
};