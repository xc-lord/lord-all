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
    '/misRoleRight': function () {
        $("#showView").load("/mis/mis/misMenu/misMenuRight.html");
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
    //菜单权限
    '/misMenuRight': function () {
        $("#showView").load("/mis/mis/misMenuRight/misMenuRightList.html");
    },
    '/misMenuRight/add': function () {
        $("#showView").load("/mis/mis/misMenuRight/misMenuRightAdd.html");
    },
    '/misMenuRight/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/mis/misMenuRight/misMenuRightEdit.html");
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
    //文章
    '/cmsArticle': function () {
        pageParam = Object.assign({}, {showEditPage: true});
        $("#showView").load("/mis/cms/cmsArticle/cmsArticleList.html");
    },
    '/cmsArticle/add': function () {
        $("#showView").load("/mis/cms/cmsArticle/cmsArticleAdd.html");
    },
    '/cmsArticle/add/:paramStr': function (paramStr) {
        getRouterParam(paramStr);//设置参数
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
    //Excel分类
    '/excelCategory': function () {
        $("#showView").load("/mis/excel/excelCategory/excelCategoryList.html");
    },
    '/excelCategory/add': function () {
        $("#showView").load("/mis/excel/excelCategory/excelCategoryAdd.html");
    },
    '/excelCategory/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/excel/excelCategory/excelCategoryEdit.html");
    },
    //Excel模板
    '/excelTemplate': function () {
        $("#showView").load("/mis/excel/excelTemplate/excelTemplateList.html");
    },
    '/excelTemplate/add': function () {
        pageParam = {};
        $("#showView").load("/mis/excel/excelTemplate/excelTemplateEdit.html");
    },
    '/excelTemplate/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/excel/excelTemplate/excelTemplateEdit.html");
    },
    //Excel导入记录
    '/excelImportRecord/:templateId': function (templateId) {
        pageParam = Object.assign({}, {templateId: templateId});//设置页面参数
        $("#showView").load("/mis/excel/excelImportRecord/excelImportRecordList.html");
    },
    '/excelImportRecord/:templateId/add': function (templateId) {
        pageParam = Object.assign({}, {templateId: templateId});//设置页面参数
        $("#showView").load("/mis/excel/excelImportRecord/excelImportRecordEdit.html");
    },
    '/excelImportRecord/:templateId/edit/:id': function (templateId, id) {
        pageParam = Object.assign({}, {templateId: templateId, id: id});//设置页面参数
        $("#showView").load("/mis/excel/excelImportRecord/excelImportRecordEdit.html");
    },
    '/excelImportRecord/:templateId/viewData/:id': function (templateId, id) {
        pageParam = Object.assign({}, {templateId: templateId, id: id});//设置页面参数
        $("#showView").load("/mis/excel/excelImportRecord/excelDataList.html");
    },
    //页面管理
    '/adsPage': function () {
        $("#showView").load("/mis/ads/adsPage/adsPageList.html");
    },
    '/adsPage/add': function () {
        $("#showView").load("/mis/ads/adsPage/adsPageAdd.html");
    },
    '/adsPage/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/ads/adsPage/adsPageEdit.html");
    },
    //广告位管理
    '/adsSpace/tree/:pageId': function (pageId) {
        pageParam = Object.assign({}, {pageId: pageId});//设置页面参数
        $("#showView").load("/mis/ads/adsSpace/adsSpaceTree.html");
    },
    '/adsSpace/add': function () {
        $("#showView").load("/mis/ads/adsSpace/adsSpaceAdd.html");
    },
    '/adsSpace/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/ads/adsSpace/adsSpaceEdit.html");
    },
    //广告位的元素管理
    '/adsElement/list/:spaceId': function (spaceId) {
        pageParam = Object.assign({}, {spaceId: spaceId});//设置页面参数
        $("#showView").load("/mis/ads/adsElement/adsElementList.html");
    },
    '/adsElement/add': function () {
        $("#showView").load("/mis/ads/adsElement/adsElementAdd.html");
    },
    '/adsElement/edit/:id': function (id) {
        pageParam = Object.assign({}, {id: id});//设置页面参数
        $("#showView").load("/mis/ads/adsElement/adsElementEdit.html");
    },
};
var router = Router(routes);
router.init();

function getRouterParam(paramStr) {
    var param = {};
    var paramArr = paramStr.split("-");
    for(var index in paramArr) {
        var p = paramArr[index];
        p = p.split("_");
        param[p[0]] = p[1];
    }
    pageParam = Object.assign({}, param);//设置页面参数
    return param;
}