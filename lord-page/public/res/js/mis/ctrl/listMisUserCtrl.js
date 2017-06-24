/**
 *
 */
(function () {
    app.controller('listMisUserCtrl', function ($scope) {
        $scope.text = '列表啊Controller';
        //AngularJS页面渲染完成之后执行的函数
        $scope.$on('$viewContentLoaded', function(){
            $('select').selected();//异步加载Amaze UI的select时需要重新渲染
            //给表格添加排序功能
            $('#example-r').DataTable({
                bInfo: false,
                dom: 'ti'
            });
        });

        $scope.change = function(x){
            console.log(x);
        }
    });
}());