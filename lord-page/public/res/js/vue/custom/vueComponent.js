/**
 * 级联选择组件
 * 使用例子：<mis-cascader ref="cmpSelect" v-on:cascader-changed="getOptions" cascader-api-url="/api/admin/cms/cmsCategory/getOptions.do"></mis-cascader>
 */
Vue.component('mis-cascader', {
    props: ['cascaderApiUrl'],
    template: '<el-cascader\
                        expand-trigger="hover"\
                        :options="options"\
                        v-model="selectedOptions"\
                        @change="handleChange">\
                    </el-cascader>',
    data: function () {
        return {
            options: [],
            selectedOptions: []
        };
    },
    methods:{
        handleChange:function(value) {
            this.$emit('cascader-changed', value);
        }
    },
    mounted: function() {
        var _self = this;
        //获取级联选择器的数据
        $.ajax({
            url: _self.cascaderApiUrl,
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.options = res.data;
            } else {
                console.error(res.msg);//提示错误
            }
        });
    }
});