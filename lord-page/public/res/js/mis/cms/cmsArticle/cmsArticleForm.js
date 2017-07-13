/**
	Desc: 	文章的表单公共方法
	Author: xiaocheng
	Date: 	2017年07月01日 18:09:10
*/
var CmsArticleFromCommon = {
    //表单默认值
    defaultModel: {
        //id//不需要此项
        title: '',		//文章标题
        titleSub: '',		//副标题
        coverImg: '',		//封面
        intro: '',		//文章简介
        authorId: 0,		//作者Id
        authorImg: '',		//作者头像
        authorName: '',		//作者名称
        source: '',		//来源
        sourceUrl: '',		//来源链接
        tags: '',		//文章标签
        tagsJson: '',		//文章标签json
        siteId: 0,		//站点id
        catId: 0,		//分类id
        catName: '',		//分类名称
        catOneId: 0,		//一级分类
        catTwoId: 0,		//二级分类
        catThreeId: 0,		//三级分类
        checkState: '',		//审核状态
        state: '',		//文章状态
        style: '',		//样式
        publishTime: '',		//发布时间
        misUserId: 0,		//添加用户Id
        userId: 0,		//用户Id
        allowComment: true,	//是否允许评论
        orderValue: 0,		//排序
        createTime: '',		//创建时间
        updateTime: '',		//更新时间
        removed: false,	//是否删除
        content:'', //文章PC端内容
        mcontent:'', //文章移动端内容
        articleRefIds:'',//关联文章Id
        articleTags:'',//文章标签
    },
    data: {
		editLoading:false,
        //表单验证规则
        editFormRules: {
            title: [
                {required: true, message: '标题不能为空', trigger: 'blur'},
                {
                    validator: function(rule, value, callback){
                        return commonUtils.formRowIsExist('/api/admin/cms/cmsArticle/isExist.do',"title",rule, value, callback);
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
        cmsArticleState: [],
        //状态
        cmsArticleCheckState: [],
        //选项卡默认显示的窗口
        activeTabName:"article_base",
        //分类数组
        categoryArr:[],
        //标签数组
        tagOptions: [{
            value: 'HTML',
            label: 'HTML'
        }, {
            value: 'CSS',
            label: 'CSS'
        }, {
            value: 'JavaScript',
            label: 'JavaScript'
        }],
        tagValues:[],
        //关联文章列表数据
        refTableData: [],
        searchRefForm:{name:""},
        //多选值
        multipleSelection: [],
        dialogFormVisible:false,
        refArticleIds:[],
    },
    //加载下拉框的数据
    loadSelect: function (_self) {
        //获取下拉框选项
        $.ajax({
            url: '/api/mis/getEnumType.do',
            data: {cls: "cms_CmsArticleState,CheckState"},
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.cmsArticleState = res.data.cms_CmsArticleState;
                _self.cmsArticleCheckState = res.data.CheckState;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });

        //获取文章标签
        $.ajax({
            url: '/api/admin/cms/cmsTags/list.do',
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.tagOptions = res.data.list;
            } else {
                _self.$message.error(res.msg);//提示错误
            }
        });
    },
    //保存事件
    saveAction: function (_self) {
		var formSelf = this;
        //转换称时间戳
		_self.editForm.publishTime = commonUtils.toTimestamp(_self.editForm.publishTime);//发布时间
		_self.editForm.createTime = commonUtils.toTimestamp(_self.editForm.createTime);//创建时间
		_self.editForm.updateTime = commonUtils.toTimestamp(_self.editForm.updateTime);//更新时间
        //保存异步提交
        $.ajax({
            method: "post",
            url: '/api/admin/cms/cmsArticle/save.do',
            data: _self.editForm,
            dataType: "json"
        }).done(function (res, status, xhr) {
            if (res.success) {
                _self.$message.success(res.msg);//保存成功
                window.location.href = "#/cmsArticle";//跳转到列表页面
                formSelf.closeDialogForm();//关闭弹窗
            } else {
                _self.$message.error(res.msg);//提示错误
            }
            _self.loading = false;
        });
    },
    //取消事件
    cancelAction: function () {
        window.location.href = "#/cmsArticle";//跳转到列表页面
        this.closeDialogForm();//关闭弹窗
    },
    //关闭父组件的弹窗
    closeDialogForm:function() {
		//如果是弹窗模式，关闭弹窗
        if(typeof(cmsArticleView) !== 'undefined' && cmsArticleView.dialogFormVisible) {			
			cmsArticleView.dialogFormClose();
		}
    }
};