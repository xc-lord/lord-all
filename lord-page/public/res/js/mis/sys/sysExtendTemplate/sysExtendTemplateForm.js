/**
 Desc: 	扩展属性模板的表单公共方法
 Author: xiaocheng
 Date: 	2018年05月09日 16:52:19
 */
var SysExtendTemplateFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        name: '',		//模板名称
        entityTable: '',		//实体表名
        entityCode: '',		//实体编码
        orderValue: 0,		//排序
        creator: 0,		//创建人
        createTime: '',		//创建时间
        modifier: 0,		//更新人
        updateTime: '',		//更新时间
        removed: false,	//是否删除
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
                            return commonUtils.formRowIsExist('/api/admin/sys/sysExtendTemplate/isExist.do',"name",rule, value, callback);
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
            //数据类型
            extendAttrDataType:[],
            //表单输入框类型
            extendAttrInputType:[],
            //是否编辑模式
            editMode:true,
            editModeName:'编辑',
            columnIdIndex:1,
            //状态
            sysExtendTemplateStatus: []
        };
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取列类型
        var cls = "sys_ExtendAttrDataType,sys_ExtendAttrInputType";
        $.ajax({
            url: '/api/mis/getEnumType.do',
            data: {cls: cls},
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.extendAttrDataType = res.data['sys_ExtendAttrDataType'];
                _self.extendAttrInputType = res.data['sys_ExtendAttrInputType'];
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
        var columnList = _self.editForm.columnList;
        if(columnList == null || columnList.length < 1) {
            _self.$message.error("最少需要新增一列！");
            return;
        }
        var submitParamObj = Object.assign({}, _self.editForm);
        delete submitParamObj.columnList;
        submitParamObj.columnJsonStr = JSON.stringify(columnList);
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/sys/sysExtendTemplate/saveOrUpdate.do',
            data: submitParamObj,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/sysExtendTemplate";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function (_self) {
        window.location.href = "#/sysExtendTemplate";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
        //如果是弹窗模式，关闭弹窗
        if(typeof(sysExtendTemplateView) !== 'undefined' && sysExtendTemplateView.dialogFormVisible) {
            sysExtendTemplateView.dialogFormClose();
        }
    }
};