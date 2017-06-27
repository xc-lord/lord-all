/**
	Desc: 	后台用户的表单公共方法
	Author: xiaocheng
	Date: 	2017年05月09日 16:43:46
*/
var MisUserFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//真实姓名
        username: '',		//用户名
        password: '',		//密码
        phone: '',		//手机
        status: 'Normal',		//用户状态
        nickName: '',		//昵称
        email: '',		//邮箱
        icon: '',		//头像
        roleName: '',		//用户角色
        roleId: '',		//用户角色Id
        addUserId: 0,		//添加用户Id
        loginTime: '',		//登录时间
        createTime: '',		//创建时间
        updateTime: '',		//更新时间
        removed: false,	//是否删除
        sex: 1,		//性别：1男，2女
        superAdmin: false,	//是否超级管理员
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
                        return commonUtils.formRowIsExist('/api/admin/mis/misUser/isExist.do',"name",rule, value, callback);
                    },
                    trigger: 'blur'
                }
            ],
            username: [
                {required: true, message: '用户名不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/mis/misUser/isExist.do',"username",rule, value, callback);
                    },
                    trigger: 'blur'
                }
            ],
            password: [
                {required: true, message: '密码不能为空', trigger: 'blur'}
            ],
            nickName: [
                {required: true, message: '昵称不能为空', trigger: 'blur'}
            ],
            phone: [
                {required: true, message: '手机号不能为空', trigger: 'blur'},
                { min: 11, max: 11, message: '手机号为11个字符', trigger: 'blur' }
            ],
            status: [
                {required: true, message: '请选择用户状态'}
            ],
            roleId: [
                {required: true, message: '请选择用户角色'}
            ],
        },
        //编辑界面数据
        editForm: {},
        //状态
        misUserStatus: [],
        //用户角色
        userRoles:[],
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
                _self.misUserStatus = res.data.MisUserStatus;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });

        //用户角色
        $.ajax({
            url: '/api/admin/mis/misRole/list.do',
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                var list = res.data.list;
                /*for(var i=0; i<list.length; i++) {
                    var o = list[i];
                    var obj = {};
                    obj.value = o.id;
                    obj.label = o.name;
                    _self.userRoles.push(obj);
                }*/
                _self.userRoles = res.data.list;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
		_self.editForm.loginTime = commonUtils.toTimestamp(_self.editForm.loginTime);//登录时间
		_self.editForm.createTime = commonUtils.toTimestamp(_self.editForm.createTime);//创建时间
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//更新时间
        //保存异步提交
        $.ajax({
            async: true,
            method: "post",
            url: '/api/admin/mis/misUser/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/misUser";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/misUser";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(misUserView) !== 'undefined' && misUserView.dialogFormVisible) {			
			misUserView.dialogFormClose();
		}
    }
};