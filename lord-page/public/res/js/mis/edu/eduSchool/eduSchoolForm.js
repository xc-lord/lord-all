/**
	Desc: 	学校的表单公共方法
	Author: xiaocheng
	Date: 	2018年06月06日 10:15:50
*/
var EduSchoolFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//名称
        enName: '',		//英文名
        logoImg: '',		//学校Logo
        intro: '',		//介绍
        provinceId: 0,		//省ID
        cityId: 0,		//市ID
        countyId: 0,		//县ID
        townId: 0,		//镇ID
        address: '',		//详细地址
        orderValue: 0,		//排序
        creator: 0,		//创建人
        createTime: '',		//创建时间
        modifier: 0,		//更新人
        updateTime: '',		//更新时间
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
                           return commonUtils.formRowIsExist('/api/admin/edu/eduSchool/isExist.do',"name",rule, value, callback);
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
           eduSchoolStatus: []
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
                _self.eduSchoolStatus = res.data.mis_MisUserStatus;
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
            url: '/api/admin/edu/eduSchool/saveOrUpdate.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                var entityId = res.data.id;
                var saveAttr = _self.$refs.eduSchoolExtendAttr.saveAction(entityId);
                var saveRecruit = _self.$refs.eduSchoolRecruit.saveAction(entityId);
                var saveTestTime = _self.$refs.eduSchoolTestTime.saveAction(entityId);
                var saveCondition = _self.$refs.eduSchoolCondition.saveAction(entityId);
                var eduSchoolScoreLine = _self.$refs.eduSchoolScoreLine.saveAction(entityId);
                if(saveAttr && saveRecruit && saveTestTime && saveCondition && eduSchoolScoreLine) {
                    window.location.href = "#/eduSchool";//跳转到列表页面
                    formSelf.closeDialogForm();//关闭弹窗
                }
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function (_self) {
        window.location.href = "#/eduSchool";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(eduSchoolView) !== 'undefined' && eduSchoolView.dialogFormVisible) {			
			eduSchoolView.dialogFormClose();
		}
    }
};