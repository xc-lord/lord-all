/**
	Desc: 	后台菜单的具体权限的表单公共方法
	Author: xiaocheng
	Date: 	2017年07月28日 18:03:31
*/
var MisMenuRightFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//权限名称
        rightCode: '',		//权限code
        menuId: 0,		//菜单Id
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
                        return commonUtils.formRowIsExist('/api/admin/mis/misMenuRight/isExist.do',"name",rule, value, callback);
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
        misMenuRightStatus: []
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
                _self.misMenuRightStatus = res.data.mis_MisUserStatus;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/mis/misMenuRight/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/misMenuRight";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/misMenuRight";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(misMenuRightView) !== 'undefined' && misMenuRightView.dialogFormVisible) {			
			misMenuRightView.dialogFormClose();
		}
    }
};