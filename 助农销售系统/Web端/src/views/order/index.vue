<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-button v-if="permissions.demo_shoppingcar_add" icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading">
            <el-table-column
                    prop="orderId"
                    header-align="center"
                    align="center"
                    width="200"
                      label="订单编号">
            </el-table-column>
            <el-table-column
                    prop="consignee"
                    header-align="center"
                    align="center"
                    width="100"
                      label="用户名称">
            </el-table-column>
            <el-table-column
                    prop="amount"
                    header-align="center"
                    align="center"
                      label="数量">
            </el-table-column>
            <el-table-column
                    prop="price"
                    header-align="center"
                    align="center"
                      label="价格">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    header-align="center"
                    align="center"
                    width="120"
                      label="联系电话">
            </el-table-column>
            <el-table-column
                    prop="timeExpire"
                    header-align="center"
                    align="center"
                    width="200"
                      label="订单创建时间">
            </el-table-column>
            <el-table-column
                    prop="status"
                    header-align="center"
                    align="center"
                    width="100"
                      label="订单状态">
            </el-table-column>
            <el-table-column
                    prop="updateTime"
                    header-align="center"
                    align="center"
                    width="200"
                      label="订单完成时间">
            </el-table-column>
            
          <el-table-column
                  header-align="center"
                  align="center"
                  label="操作"
                  width="200">
            <template #="scope">
              <el-button   size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.orderId)">修改</el-button>
              <el-button   size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.shoppingCarId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div> 
      <div>
        <el-pagination
                @size-change="sizeChangeHandle"
                @current-change="currentChangeHandle"
                :current-page.sync="pageIndex"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="pageSize"
                :total="totalPage"
                background
                layout="sizes, prev, pager, next, jumper,total">
        </el-pagination>
      </div>
      <!-- 弹窗, 新增 / 修改 -->
      <table-form v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></table-form>
    </basic-container>
  </div>
</template>

<script>
  import {fetchList, delObj,test} from '@/api/admin/shoppingcar'
  import TableForm from './shoppingcar-form.vue'
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 1,
        dataListLoading: true,
        addOrUpdateVisible: true
      }
    },
    components: {
      TableForm
    },
    created () {
      this.getDataList()
      this.martest()
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        fetchList(
          Object.assign(
            {
              current: this.pageIndex,
              size: this.pageSize
        })).then(response => {
          this.dataList = response.data.data.records
          this.totalPage = Number(response.data.data.total)
          console.log(response);
        })
        this.dataListLoading = false
      },
      martest(){
         test(
          Object.assign(
            {
              current:this.pageIndex,
              size:this.pageSize
            })).then(response => {
              console.log('查查',response);
            })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        // this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        this.$confirm('是否确认删除ID为' + id, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          return delObj(id)
        }).then(data => {
          this.$message.success('删除成功')
          this.getDataList()
        }).catch(() => {})
      }
    }
  }
</script>
