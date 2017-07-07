/**
 * 级联选择组件
 * api-url 调用的api地址
 * default-value 级联选择的默认值，数组
 * default-one-value 级联选择的默认值，单个值
 * cascader-changed 级联选择的选项变更时间
 * can-select-all 可否选择任意项
 * 使用例子：
 * <mis-cascader ref="cmpSelect" api-url="/api/admin/cms/cmsCategory/getOptions.do" :default-value="selectValue" v-on:cascader-changed="getOptions" :can-select-all="true"></mis-cascader>
 */
Vue.component('mis-cascader', {
    props: {
        apiUrl:String,
        placeholder:{
            type:String,
            default:function (){
                return "搜索";
            }
        },
        defaultValue:{
            type:Array,
            default:function (){
                return [];
            }
        },
        defaultOneValue: String,
        canSelectAll:{
            type:Boolean,
            default:false
        },
    },
    template: '<el-cascader\
                        expand-trigger="hover"\
                        :options="options"\
                        :placeholder="placeholder"\
                        filterable\
                        :change-on-select="selectAll"\
                        v-model="selectedOptions"\
                        @change="handleChange">\
                    </el-cascader>',
    data: function () {
        return {
            options: [],
            selectedOptions: this.defaultValue,
            selectAll:this.canSelectAll,//是否可以选择任意项
            optionMap:{},//单个数值对应的数值列表
        };
    },
    methods:{
        handleChange:function(value) {
            this.$emit('cascader-changed', value);
        },
        getOpitonMap: function(optionMap, data, parentArr) {
            var _self = this;
            if(!parentArr) {
                parentArr = [];
            }
            var parent = [].concat(parentArr);//复制数组，不复制引用
            for(var o in data) {
                var obj = data[o];
                parent.push(obj.value);
                optionMap[obj.value] = parent;
                if(obj.children) {
                    _self.getOpitonMap(optionMap, obj.children, parent);
                }
                parent = [].concat(parentArr);//复制数组，不复制引用
            }
        }
    },
    mounted: function() {
        var _self = this;
        if(!_self.apiUrl) {
            return;
        }
        //获取级联选择器的数据
        $.ajax({
            url: _self.apiUrl,
            dataType: "json"
        }).done(function (res) {
            if (res.success) {
                _self.options = res.data;
                _self.optionMap = {};
                _self.getOpitonMap(_self.optionMap,res.data);
                if(_self.defaultOneValue) {
                    _self.selectedOptions = _self.optionMap[_self.defaultOneValue];
                }
            } else {
                console.error(res.msg);//提示错误
            }
        });
    }
});