/**
	Desc: 	文件管理的表单公共方法
	Author: xiaocheng
	Date: 	2017年07月01日 11:39:47
*/
var SysFileFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//文件名称
        intro: '',		//文件描述
        filePath: '',		//文件路径
        fileSize: 0,		//文件大小
        fileType: '',		//文件类型
        fileSuffix: '',		//文件后缀名
        alias: '',		//文件别名
        mdCode: '',		//文件md5值
        directoryId: 0,		//目录id
        misUserId: 0,		//上传后台用户id
        userId: 0,		//上传用户id
        createTime: '',		//上传时间
        updateTime: '',		//更新时间
    },
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            mdCode: [
                {required: true, message: 'md5值不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/mis/sysFile/isExist.do',"mdCode",rule, value, callback);
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
        sysFileStatus: []
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
                _self.sysFileStatus = res.data.mis_MisUserStatus;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
		_self.editForm.createTime = commonUtils.toTimestamp(_self.editForm.createTime);//上传时间
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//更新时间
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/mis/sysFile/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/sysFile";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/sysFile";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(sysFileView) !== 'undefined' && sysFileView.dialogFormVisible) {			
			sysFileView.dialogFormClose();
		}
    }
};