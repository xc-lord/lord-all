/**
	Desc: 	系统菜单的表单公共方法
	Author: xiaocheng
	Date: 	2017年05月10日 15:51:31
*/
var MisMenuFromCommon = {
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/mis/misMenu/isExist',"name",rule, value, callback);
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
        misMenuStatus: [],
        icons: [
            {
                value: 'el-icon-menu',
                label: '菜单'
            },
            {
                value: 'el-icon-setting',
                label: '设置'
            },
            {
                value: 'el-icon-document',
                label: '页面'
            },
            {
                value: 'el-icon-edit',
                label: '编辑'
            },
            {
                value: 'el-icon-share',
                label: '分享'
            },
            {
                value: 'el-icon-delete',
                label: '删除'
            },
            {
                value: 'el-icon-date',
                label: '时间'
            },
            {
                value: 'el-icon-upload',
                label: '上传'
            },
            {
                value: 'el-icon-message',
                label: '消息'
            },
            {
                value: 'el-icon-picture',
                label: '图片'
            }
        ],
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
                _self.misMenuStatus = res.data.MisUserStatus;
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
            url: '/api/admin/mis/misMenu/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/misMenu";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/misMenu";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(misMenuView) !== 'undefined' && misMenuView.dialogFormVisible) {			
			misMenuView.dialogFormClose();
		}
    }
};