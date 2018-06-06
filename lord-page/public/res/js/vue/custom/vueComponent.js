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
 * 地址组件
 * provinceId\cityId\countyId\townId 默认选中的省市县镇Id数组
 * location-changed 选中地址回调函数
 * 使用列子：
 * <mis-location
 *   :province-id="editForm.provinceId"
 *   :city-id="editForm.cityId"
 *   :county-id="editForm.countyId"
 *   :town-id="editForm.townId"
 *   @location-changed="locationChanged">
 */
Vue.component('mis-location', {
    props:{
        provinceId:Number,
        cityId:Number,
        countyId:Number,
        townId:Number,
        editMode:{
            type: String,
            default: 'Add'
        }
    },
    template: '<el-cascader\
                :options="locationOptions"\
                @change="locationChange"\
                @active-item-change="locationItemChange"\
                :props="locationProps"\
                size="small"\
                v-model="selectedLocations"\
                style="width:400px"\
                    ></el-cascader>',
    data: function () {
        return {
            message: "地址组件",
            loadTimes:0,
            locationProps:{
                value: 'id',
                label:'name',
                children: 'children'
            },
            locationLoadArr:[],
            locationOptions:[],
            selectedLocations:[]
        };
    },
    methods:{
        loadLocation:function() {
            var _self = this;
            var params = {};
            if(_self.provinceId) {
                params = {
                    provinceId: _self.provinceId,
                    cityId: _self.cityId,
                    countyId: _self.countyId,
                    townId: _self.townId
                };
            }
            $.ajax({
                url: '/api/admin/sys/sysDistrict/getDistrict.do',
                data: params,
                dataType: "json"
            }).done(function (res) {
                if (res.success) {
                    _self.loadTimes++;
                    _self.locationOptions = res.data;
                } else {
                    _self.$message.error(res.msg);//提示错误
                }
            });
        },
        loadDefaultValue:function() {
            var locationArr = [];
            if(this.provinceId) {
                locationArr.push(this.provinceId);
            }
            if(this.cityId) {
                locationArr.push(this.cityId);
            }
            if(this.countyId) {
                locationArr.push(this.countyId);
            }
            if(this.townId) {
                locationArr.push(this.townId);
            }
            this.selectedLocations = locationArr;
        },
        locationChange:function(location) {
            this.$emit('location-changed', location);
        },
        locationItemChange:function(location) {
            var _self = this;
            var locationSize = location.length;
            var locationId = location[locationSize-1];
            if(!_self.locationLoadArr.contains(locationId) && locationSize<4) {
                $.ajax({
                    url: '/api/admin/sys/sysDistrict/listDistrict.do',
                    data: {parentId: locationId},
                    dataType: "json"
                }).done(function (res) {
                    if (res.success) {
                        _self.locationLoadArr.push(locationId);//保存已经加载过的地址
                        _self.setLocation(_self, _self.locationOptions, locationId, res.data, locationSize)
                    } else {
                        _self.$message.error(res.msg);//提示错误
                    }
                });
            }
        },
        setLocation:function(_self, options, locationId, resData, locationSize) {
            for(var i=0;i<options.length;i++) {
                var opt = options[i];
                //查询子区域
                if(opt.children && opt.children.length>0) {
                    _self.setLocation(_self, opt.children, locationId, resData, locationSize);
                }
                if(opt.id == locationId) {
                    if(locationSize == 3) {
                        for(var j=0;j<resData.length;j++) {
                            delete resData[j].children;
                        }
                    }
                    if(resData.length < 1) {
                        opt.children = null;
                    } else {
                        opt.children = resData;
                    }
                }
            }
        }
    },
    watch:{
        provinceId:function(newVal, oldVal) {
            if(this.loadTimes < 1) {
                this.loadLocation();
                this.loadDefaultValue();
            }
        }
    },
    mounted: function() {
        var _self = this;
        if(_self.editMode == 'Add')
            _self.loadLocation();
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

/**
 * 扩展属性组件
 * <mis-extend-attr edit-mode="Edit" entity-code="eduSchool" :entity-id="editForm.id" ref="eduSchoolExtendAttr"></mis-extend-attr>
 */
Vue.component('mis-extend-attr', {
    props: {
        entityId: Number,
        entityCode: String,
        editMode:String
    },
    data: function () {
        return {
            extendData: {}
        }
    },
    template: '<el-form label-width="120px" ref="extendForm">\
                    <template v-for="item in extendData.columnList">\
                        <el-form-item v-if="item.inputType == \'Input\'" :label="item.name">\
                            <el-input v-model="item.val" auto-complete="off" size="small"></el-input>\
                        </el-form-item>\
                        <el-form-item v-if="item.inputType == \'DatePicker\'" :label="item.name">\
                            <el-date-picker type="datetime"\
                                placeholder="选择日期和时间" size="small"\
                                v-model="item.val">\
                            </el-date-picker>\
                        </el-form-item>\
                        <el-form-item v-if="item.inputType == \'Switch\'" :label="item.name">\
                            <el-switch\
                            v-model="item.val"\
                            active-color="#13ce66"\
                            inactive-color="#ff4949">\
                            </el-switch>\
                        </el-form-item>\
                    </template>\
                </el-form>',
    methods:{
        //加载扩展属性
        loadExtendDetails:function() {
            var _self = this;
            $.ajax({
                url: '/api/admin/sys/getExtendDetails.do',
                data:{entityCode:_self.entityCode,entityId:_self.entityId},
                dataType: "json"
            }).done(function (res) {
                if (res.success) {
                    _self.extendData = res.data;
                } else {
                    _self.$message.error(res.msg);//提示错误
                }
            });
        },
        saveAction:function(entityId) {
            var _self = this;
            _self.extendData.entityId = entityId;//设置ID
            if(!_self.extendData.columnList)
                return true;
            var success = false;
            $.ajax({
                async:false,
                method: "post",
                url: '/api/admin/sys/saveExtendDetails.do',
                data: {extendJson:JSON.stringify(_self.extendData)},
                dataType: "json"
            }).done(function (res, status, xhr) {
                if (res.success) {
                    //_self.$message.success(res.msg);//保存成功
                    success = true;
                } else {
                    _self.$message.error(res.msg);//提示错误
                }
            });
            return success;
        }
    },
    watch:{
        entityId:function(newVal, oldVal) {
            this.loadExtendDetails();
        }
    },
    mounted: function() {
        if(this.editMode == 'Add') {
            this.loadExtendDetails();
        }
    }
});

/**
 * 扩展内容组件
 * <mis-extend-content edit-mode="Edit" entity-code="eduSchool" :entity-id="editForm.id" ref="eduSchoolExtendContent" content-name="学校简介"></mis-extend-content>
 */
Vue.component('mis-extend-content', {
    props: {
        entityId: Number,
        entityCode: String,
        contentName: String,
        editMode:String
    },
    data: function () {
        var contentId = commonUtils.uuid();
        return {
            extendData: {},
            ueElement:{},
            contentId:contentId
        }
    },
    template: '<el-form label-width="120px" ref="extendContentForm">\
                    <el-form-item :label="contentName">\
                        <script :id="contentId" type="text/plain"></script>\
                    </el-form-item>\
               </el-form>',
    methods:{
        //加载扩展属性
        loadExtendContent:function() {
            var _self = this;
            if(this.editMode == 'Add') {
                return;
            }
            $.ajax({
                url: '/api/admin/sys/getExtendContent.do',
                data:{entityCode:_self.entityCode,entityId:_self.entityId},
                dataType: "json"
            }).done(function (res) {
                if (res.success) {
                    var content = "";
                    if(res.data && commonUtils.isNotEmpty(res.data.content))
                        content = res.data.content;
                    _self.ueElement = UE.getEditor(_self.contentId, {
                        initialFrameHeight: 500,
                        initialContent: content
                    });
                    _self.extendData = res.data;
                } else {
                    _self.$message.error(res.msg);//提示错误
                }
            });
        },
        saveAction:function(entityId) {
            var _self = this;
            var success = false;
            var content = _self.ueElement.getContent();

            $.ajax({
                async:false,
                method: "post",
                url: '/api/admin/sys/sysExtendContent/saveOrUpdate.do',
                data: {entityId:entityId,entityCode:_self.entityCode,content:content,id:_self.extendData.id},
                dataType: "json"
            }).done(function (res, status, xhr) {
                if (res.success) {
                    //_self.$message.success(res.msg);//保存成功
                    success = true;
                } else {
                    _self.$message.error(res.msg);//提示错误
                }
            });
            return success;
        }
    },
    watch:{
        entityId:function(newVal, oldVal) {
            this.loadExtendContent();
        }
    },
    mounted: function() {
        if(this.editMode == 'Add') {
            this.ueElement = UE.getEditor(this.contentId, {initialFrameHeight: 500});
            this.loadExtendContent();
        }
    }
});