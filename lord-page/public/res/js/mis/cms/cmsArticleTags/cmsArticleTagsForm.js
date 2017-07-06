/**
	Desc: 	文章标签关联表的表单公共方法
	Author: xiaocheng
	Date: 	2017年07月01日 18:03:02
*/
var CmsArticleTagsFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        articleId: 0,		//文章Id
        tagsId: 0,		//标签Id
    },
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/cms/cmsArticleTags/isExist.do',"name",rule, value, callback);
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
        cmsArticleTagsStatus: []
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
                _self.cmsArticleTagsStatus = res.data.MisUserStatus;
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
            url: '/api/admin/cms/cmsArticleTags/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/cmsArticleTags";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/cmsArticleTags";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(cmsArticleTagsView) !== 'undefined' && cmsArticleTagsView.dialogFormVisible) {			
			cmsArticleTagsView.dialogFormClose();
		}
    }
};