<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                v-if="dataList.length">
            <el-table-column
                    prop="businessId"
                    header-align="center"
                    align="center"
                    width="110"
                      label="商户ID">
            </el-table-column>
            <el-table-column
                    prop="storeName"
                    header-align="center"
                    align="center"
                    width="100"
                      label="商户名称">
            </el-table-column>
            <el-table-column
                    prop="goodsId"
                    header-align="center"
                    align="center"
                    width="100"
                    label="商品ID">
            </el-table-column>
            <el-table-column
                    prop="goodsName"
                    header-align="center"
                    align="center"
                      label="商品名称">
            </el-table-column>
            <el-table-column
                    prop="gradeId"
                    header-align="center"
                    align="center"
                    width="200"
                      label="商品等级">
            </el-table-column>
            <el-table-column
                    prop="status"
                    header-align="center"
                    align="center"
                    width="100"
                      label="状态">
                      <template v-slot="scope">
                        <p v-if="scope.row.status === 9">待审核</p>
                        <p v-if="scope.row.status === 0">审核通过</p>
                        <p v-if="scope.row.status === 8">审核不通过</p>
                      </template>
            </el-table-column>
            <el-table-column
                    prop="goodsImage"
                    header-align="center"
                    align="center"
                    width="180"
                    label="商品图片">
                    <template v-slot="scope">
                      <div>
                      <el-image style="width: 100px; height: 100px"
                        :src="scope.row.goodsImage[1]"
                        alt="" 
                         />
                      </div>
                    </template> 
            </el-table-column>
            <el-table-column
                    prop="description"
                    header-align="center"
                    align="center"
                    width="300"
                      label="商户简介">
            </el-table-column>
            <el-table-column
                    prop="stock"
                    header-align="center"
                    align="center"
                    width="100"
                      label="库存">
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    header-align="center"
                    align="center"
                      label="创建时间"
                      width="200">
            </el-table-column>
          <el-table-column
                  header-align="center"
                  align="center"
                  width="200"
                  label="操作">
            <template #="scope">
              <el-button  size="small" type="success" icon="el-icon-check" circle @click="passaudit(scope.row)"></el-button>
              <el-button  size="small" type="danger" icon="el-icon-close" circle @click="unpassaudit(scope.row)"></el-button>
              <!-- <el-button v-if="permissions.goods_update" type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.businessId)">修改</el-button> -->
              <!-- <el-button v-if="permissions.goods_goods_del" type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.businessId)">删除</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div>
        <el-pagination
                @size-change="sizeChangeHandle"
                @current-change="currentChangeHandle"
                :current-page.sync="pageIndex"
                :page-sizes="[5, 10, 20, 50, 100]"
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
  import {fetchList, delObj,getObj,putObj} from '@/api/admin/goodsAudit'
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 5,
        totalPage: 1,
        dataListLoading: true,
        addOrUpdateVisible: true,
        page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条,
        isAsc: false, //是否倒序,
      },
        // 修改状态之后的数据
        passauditList:{
          businessId:'',
          lockFlag:''
        }
      }
    },
    created () {
      this.getDataList()
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
          this.dataListLoading = false
          console.log(response,'response');
          this.dataList = response.data.data.records
          console.log('待审核',this.dataList);
          this.totalPage = Number(response.data.data.total)
          for(var i = 0;i<this.dataList.length;i++){
            const regex = /"([^"]*)"/; // 匹配两个引号之间的内容
            this.dataList[i].goodsImage = this.dataList[i].goodsImage.match(regex)
          }
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
      passaudit(row) {
        getObj().then(()=>{
          console.log('拿到什么',row.goodsId);
          this.passauditList.goodsId = row.goodsId
          this.passauditList.status = 0
          row.status = 0
          console.log(this.passauditList,'发送什么');
          if(this.passauditList.goodsId) {
            putObj(this.passauditList).then(res => {
              this.$notify({
                   title:'提示',
                   message: '修改成功',
                   type: 'success'
        });
            })
          
          }
          this.getDataList()
        })
      },
      unpassaudit(row){
        getObj().then(()=>{
          console.log('拿到什么',row.goodsId);
          this.passauditList.goodsId = row.goodsId
          this.passauditList.status = 8
          row.status = 8
          console.log(this.passauditList,'发送什么');
          if(this.passauditList.goodsId) {
            putObj(this.passauditList).then(res => {
              this.$notify({
                title:'提示',
                   message: '修改成功',
                   type: 'success'
        });
            })
          
          }
          this.getDataList()
        })
      },
      // 修改
      handleUpdate(row, index) {
      this.$refs.crud.rowEdit(row, index);
      this.form.password = undefined;
    },
      // 删除
      deleteHandle (id) {
        this.$confirm('是否确认删除钙商品?','提示', {
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
