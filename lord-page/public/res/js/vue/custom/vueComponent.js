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

/**
 * Lookup组件
 * api-url 调用的api地址
 *
 */
Vue.component('mis-lookup', {
    props: {
        apiUrl: String,
        namekey: String,
        valuekey: String,
        coloums: String,
        placeholder: String
    },
    template: '<div style="display:inline-block;">\
        <el-dialog v-model="dialogVisible">\
            <el-row type="flex" justify="space-between" style="padding-bottom:20px;">\
                <el-col :span="21">\
                    <el-input placeholder="输入搜索内容"></el-input>\
                </el-col>\
                <el-col :span="3">\
                    <el-button style="float:right;" type="primary" icon="search" @click="">查询</el-button>\
                </el-col>\
            </el-row>\
            <el-row>\
                <el-table :data="tableData" highlight-current-row @current-change="handleCurrentRow">\
                    <el-table-column label="单选" width="100">\
                        <template scope="scope">\
                            <el-radio v-model="currentRow" :label="scope.row">&nbsp;</el-radio>\
                        </template>\
                    </el-table-column>\
                    <el-table-column v-for="column in tableColumns"\
                        :prop="column.attr"\
                        :label="column.title">\
                    </el-table-column>\
                </el-table>\
            </el-row>\
            <el-row style="margin-top: 20px" type="flex" justify="end">\
                <el-col :span="6">\
                    <el-button type="success" icon="check" @click="handleConfirm">确定</el-button>\
                </el-col>\
                <el-col :span="18">\
                    <el-pagination\
                        style="float: right"\
                        @size-change="handlePaginationChange"\
                        @current-change="handleCurrentPagination"\
                        :current-page="4"\
                        :page-sizes="[10, 20, 50, 100]"\
                        :page-size="100"\
                        layout="total, sizes, prev, pager, next, jumper"\
                        :total="500">\
                    </el-pagination>\
                </el-col>\
            </el-row>\
        </el-dialog>\
        <el-input :value="currentRow[namekey]" \
            :name="currentRow[valuekey]|toString" \
            :placeholder="placeholder" \
            :disabled="true"> \
            <el-button slot="append" icon="search" @click="showDialog"></el-button>\
        </el-input>\
    </div>',
    data: function() {
        return {
            dialogVisible: false,
            search: '',
            currentRow: {},
            tableColumns: [],
            tableData: []
        };
    },
    methods: {
        showDialog: function() {
            this.dialogVisible = true;
        },
        handleConfirm: function() {
            var self = this;

            self.dialogVisible = false;
            self.$emit('confirm', self.currentRow);
        },
        handleCurrentRow: function(row) {
            var self = this;

            self.currentRow = row;
        },
        handlePaginationChange: function(num) {
            console.log('每页'+num+'条');
        },
        handleCurrentPagination: function(num) {
            console.log('当前页：'+num);
        }
    },
    filters: {
        toString: function(num) {
            if ('[object Number]' === Object.prototype.toString.call(num)) {
                return num.toString();
            } else {
                return num;
            }
        }
    },
    mounted: function() {
        var self = this;

        var res = {
            "code": 1,
            "msg": "查询成功",
            "success": true,
            "data": {
                "totalRows": 1,
                "pageSize": 10,
                "page": 1,
                "totalPage": 1,
                "startRow": 0,
                "list": [{
                    "id": 1,
                    "realName": "洪建强1",
                    "nickName": "阿强1",
                    "sex": 0,
                    "remark": "阿强备注1"
                }, {
                    "id": 2,
                    "realName": "何旖曈1",
                    "nickName": "奕方1",
                    "sex": 1,
                    "remark": "奕方备注1"
                }, {
                    "id": 3,
                    "realName": "洪建强2",
                    "nickName": "阿强2",
                    "sex": 0,
                    "remark": "阿强备注2"
                }],
                "needPaging": false,
                "hasPrev": false,
                "hasNext": false
            }
        };

        self.tableColumns = JSON.parse(self.coloums);
        self.tableData = res.data.list;
    }
});