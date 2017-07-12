<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
/**
	Desc: 	${bizName}的表单公共方法
	Author: xiaocheng
	Date: 	${now?string('yyyy年MM月dd日 HH:mm:ss')}
*/
var ${className}FromCommon = {
    //表单默认值
    defaultModel: {
    <#list table.columns as column>
    <#if column.pk>
        //${column.columnNameLower}//不需要此项
    <#elseif column.javaType == 'java.lang.Long' || column.javaType == 'java.lang.Integer'>
        ${column.columnNameLower}: 0,		//${column.remarks}
    <#elseif column.javaType == 'java.lang.Boolean'>
        ${column.columnNameLower}: false,	//${column.remarks}
    <#else>
        ${column.columnNameLower}: '',		//${column.remarks}
    </#if>
    </#list>
    },
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/${bizPackage}/${classNameLower}/isExist.do',"name",rule, value, callback);
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
        ${classNameLower}Status: []
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
                _self.${classNameLower}Status = res.data.mis_MisUserStatus;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
	<#list table.columns as column>
	<#if column.javaType == 'java.util.Date'>
		_self.editForm.${column.columnNameLower} = commonUtils.toTimestamp(_self.editForm.${column.columnNameLower});//${column.remarks}
	</#if>
	</#list>        
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/${bizPackage}/${classNameLower}/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/${classNameLower}";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/${classNameLower}";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(${classNameLower}View) !== 'undefined' && ${classNameLower}View.dialogFormVisible) {			
			${classNameLower}View.dialogFormClose();
		}
    }
};