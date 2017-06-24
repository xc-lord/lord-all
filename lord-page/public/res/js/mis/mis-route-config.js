app.config(["$stateProvider","$urlRouterProvider","$locationProvider",routeFn]);
function routeFn($stateProvider,$urlRouterProvider, $locationProvider) {
    //$urlRouterProvider.otherwise("/listMisUser");
    $stateProvider
        .state("listMisUser",{
            url:"/listMisUser",
            templateUrl:"/mis/user/listMisUser.html",
            controller:"listMisUserCtrl",
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("/res/js/mis/ctrl/listMisUserCtrl.js");
                }]
            }
        })
        .state("addMisUser",{
            url:"/addMisUser",
            templateUrl:"/mis/user/addMisUser.html",
            controller:"addMisUserCtrl",
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("/res/js/mis/ctrl/addMisUserCtrl.js");
                }]
            }
        })
        .state("updateMisUser",{
            url:"/updateMisUser",
            templateUrl:"/mis/user/updateMisUser.html",
            controller:"updateMisUserCtrl",
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("/res/js/mis/ctrl/updateMisUserCtrl.js");
                }]
            }
        });


}