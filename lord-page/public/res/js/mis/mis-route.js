/**
	Desc: 	管理后台的路由配置
	Author: xiaocheng
	Date: 	2017年05月08日 15:16:33
*/
var routes = {
    //后台用户
    '/misUser': function () {
        $("#showView").load("/mis/mis/misUser/misUserList.html");
    },
    '/misUser/add': function () {
        $("#showView").load("/mis/mis/misUser/misUserAdd.html");
    },
    '/misUser/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/mis/misUser/misUserEdit.html");
    },
    //后台用户角色
    '/misRole': function () {
        $("#showView").load("/mis/mis/misRole/misRoleList.html");
    },
    '/misRole/add': function () {
        $("#showView").load("/mis/mis/misRole/misRoleAdd.html");
    },
    '/misRole/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/mis/misRole/misRoleEdit.html");
    },
    //系统菜单
    '/misMenu': function () {
        $("#showView").load("/mis/mis/misMenu/misMenuTree.html");
    },
    '/misMenu/add': function () {
        $("#showView").load("/mis/mis/misMenu/misMenuAdd.html");
    },
    '/misMenu/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/mis/misMenu/misMenuEdit.html");
    },
    //文件管理
    '/sysFile': function () {
        $("#showView").load("/mis/sys/sysFile/sysFileList.html");
    },
    '/sysFile/add': function () {
        $("#showView").load("/mis/sys/sysFile/sysFileAdd.html");
    },
    '/sysFile/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/sys/sysFile/sysFileEdit.html");
    },
    //站点管理
    '/sysSite': function () {
        $("#showView").load("/mis/sys/sysSite/sysSiteList.html");
    },
    '/sysSite/add': function () {
        $("#showView").load("/mis/sys/sysSite/sysSiteAdd.html");
    },
    '/sysSite/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/sys/sysSite/sysSiteEdit.html");
    },
    //文章标签
    '/cmsArticle': function () {
        $("#showView").load("/mis/cms/cmsArticle/cmsArticleList.html");
    },
    '/cmsArticle/add': function () {
        $("#showView").load("/mis/cms/cmsArticle/cmsArticleAdd.html");
    },
    '/cmsArticle/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/cms/cmsArticle/cmsArticleEdit.html");
    },
    //文章标签
    '/cmsTags': function () {
        $("#showView").load("/mis/cms/cmsTags/cmsTagsList.html");
    },
    '/cmsTags/add': function () {
        $("#showView").load("/mis/cms/cmsTags/cmsTagsAdd.html");
    },
    '/cmsTags/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/cms/cmsTags/cmsTagsEdit.html");
    },
    //文章分类
    '/cmsCategory': function () {
        $("#showView").load("/mis/cms/cmsCategory/cmsCategoryTree.html");
    },
    '/cmsCategory/add': function () {
        $("#showView").load("/mis/cms/cmsCategory/cmsCategoryAdd.html");
    },
    '/cmsCategory/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/cms/cmsCategory/cmsCategoryEdit.html");
    },
};
var router = Router(routes);
router.init();