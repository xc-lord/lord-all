/**
	Desc: 	扩展属性的表单公共方法
	Author: xiaocheng
	Date: 	2018年05月09日 18:08:35
*/
var SysExtendAttrFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        templateId: 0,		//模板ID
        entityCode: '',		//实体编码
        name: '',		//属性名
        dataKey: '',		//属性key
        dataType: '',		//数据类型
        inputType: '',		//属性类型
        valJsonStr: '',		//属性值的Json
        nullable: false,	//是否为空
        orderValue: 0,		//排序
    },
    data: function(){
        return {
           editLoading:false,
           //表单验证规则
           editFormRules: {
               name: [
                   {required: true, message: '名称不能为空', trigger: 'blur'},
                   {
                       validator: function(rule, value, callback){
                           return commonUtils.formRowIsExist('/api/admin/sys/sysExtendAttr/isExist.do',"name",rule, value, callback);
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
           sysExtendAttrStatus: []
        };
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
                _self.sysExtendAttrStatus = res.data.mis_MisUserStatus;
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
            url: '/api/admin/sys/sysExtendAttr/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/sysExtendAttr";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function (_self) {
        window.location.href = "#/sysExtendAttr";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(sysExtendAttrView) !== 'undefined' && sysExtendAttrView.dialogFormVisible) {			
			sysExtendAttrView.dialogFormClose();
		}
    }
};