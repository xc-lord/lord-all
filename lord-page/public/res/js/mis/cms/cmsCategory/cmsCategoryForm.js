/**
	Desc: 	文章分类的表单公共方法
	Author: xiaocheng
	Date: 	2017年07月01日 16:25:37
*/
var CmsCategoryFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//分类名称
        letter: '',		//拼音
        siteId: 0,		//站点id
        level: 0,		//等级
        intro: '',		//描述
        icon: '',		//分类图标
        pcImg: '',		//PC图片
        mobileImg: '',		//移动端图片
        parentId: 0,		//父分类
        parentName: '',		//父分类名称
        parentIds: '',		//父分类Id列表
        leaf: false,	//是否是叶子节点
        childrenIds: '',		//子分类列表
        style: '',		//样式
        misUserId: 0,		//添加用户Id
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
                        return commonUtils.formRowIsExist('/api/admin/cms/cmsCategory/isExist.do',"name",rule, value, callback);
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
        cmsCategoryStatus: []
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
                _self.cmsCategoryStatus = res.data.mis_MisUserStatus;
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
            url: '/api/admin/cms/cmsCategory/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/cmsCategory";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/cmsCategory";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(cmsCategoryView) !== 'undefined' && cmsCategoryView.dialogFormVisible) {			
			cmsCategoryView.dialogFormClose();
		}
    }
};