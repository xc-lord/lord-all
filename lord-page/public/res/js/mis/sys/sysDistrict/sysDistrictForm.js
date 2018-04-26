/**
	Desc: 	行政区域的表单公共方法
	Author: xiaocheng
	Date: 	2018年04月24日 15:04:09
*/
var SysDistrictFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        citycode: '',		//城市编码
        adcode: '',		//区域编码
        name: '',		//行政区名称
        longitude: '',		//经度
        latitude: '',		//纬度
        level: '',		//行政区划级别
        zipCode: '',		//邮编
        parentId: 0,		//父区域ID
        updateTime: '',		//更新时间
        createTime: '',		//创建时间
        orderValue: 0,		//排序值
    },
    data: function(){
        return {
           editLoading:false,
           //表单验证规则
           editFormRules: {
               name: [
                   {required: true, message: '名称不能为空', trigger: 'blur'}
               ],
               adcode: [
                   {required: true, message: '城市编码不能为空', trigger: 'blur'}
               ],
           },
           //编辑界面数据
           editForm: {},
           //状态
            sysDistrictLevel: []
        };
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取下拉框选项
        $.ajax({
            url: '/api/mis/getEnumType.do',
            data: {cls: "sys_SysDistrictLevel"},
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.sysDistrictLevel = res.data.sys_SysDistrictLevel;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//更新时间
		_self.editForm.createTime = commonUtils.toTimestamp(_self.editForm.createTime);//创建时间
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/sys/sysDistrict/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/sysDistrict/" + pageParam.parentId;//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function (_self) {
        window.location.href = "#/sysDistrict/" + pageParam.parentId;//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(sysDistrictView) !== 'undefined' && sysDistrictView.dialogFormVisible) {			
			sysDistrictView.dialogFormClose();
		}
    }
};