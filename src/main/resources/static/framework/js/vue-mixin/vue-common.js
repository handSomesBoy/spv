var commonJsMixin = {
    methods: {
        /**
         * 判断是否登录，没登录刷新当前页，促使Shiro拦截后跳转登录页
         * @param result	ajax请求返回的值
         * @returns {如果没登录，刷新当前页}
         */
        isLogin: function(obj){
            if(obj.data.code=='1101'||obj.data.code=='1102'){
                window.location.reload(true);//刷新当前页
            }
            return true;//返回true
        },
        commonAlert:function (obj) {
            if(obj.data.code=='1000')
            {
                this.$message({
                    showClose: true,
                    message: obj.data.message,
                    type: 'success'
                });
            }else if(obj.data.code=='1101'||obj.data.code=='1102'){
                this.$message({
                    showClose: true,
                    message: obj.data.message,
                    type: 'error'
                });
                setTimeout("window.location.reload(true)",2000);
                return;
                //刷新当前页
            }else {
                this.$message({
                    showClose: true,
                    message: obj.data.message,
                    type: 'error'
                })
                return true;
            }
        },
        //时间格式化
        dateFormat:function(row, column) {
            var date = row[column.property];
            if (date == undefined) {
                return "";
            }
            return moment(date).format("YYYY-MM-DD HH:mm:ss");
        }
    }
}
// 分页查询Mixin
var pageQueryJsMixin = {
    data: {
        //表格当前页数据
        tableData: [],
        //多选数组
        multipleSelection: [],
        //默认每页数据量
        pageSize: 10,
        //默认高亮行数据id
        highlightId: -1,
        //当前页码
        currentPage: 1,
        //查询的页码
        start: 1,
        //默认数据总数
        totalCount: 1000,
        //搜索条件
        condition: {},
    },
    methods: {
        //多选响应
        handleSelectionChange: function (val) {
            this.multipleSelection = val;
        },
        //改变当前点击的行的class，高亮当前行
        tableRowClassName: function (row, index) {
            if (row.id == this.highlightId) {
                return 'info-row';
            }
        },
        //每页显示数据量变更
        handleSizeChange: function (val) {
            this.pageSize = val;
            this.loadData(this.condition, this.currentPage, this.pageSize);
        },
        //页码变更
        handleCurrentChange: function (val) {
            this.currentPage = val;
            this.loadData(this.condition, this.currentPage, this.pageSize);
        },
    }
}