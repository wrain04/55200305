<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <!-- <el-button v-if="permissions.demo_goods_add" icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button> -->
          <!-- <el-button icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">添加商品</el-button> -->
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                v-if="dataList.length">
            <el-table-column
                    prop="goodsId"
                    header-align="center"
                    align="center"
                    width="110"
                      label="商品编码">
            </el-table-column>
            <el-table-column
                    prop="businessId"
                    header-align="center"
                    align="center"
                    width="100"
                      label="商户ID">
            </el-table-column>
            <el-table-column
                    prop="gradeId"
                    header-align="center"
                    align="center"
                    width="100"
                    label="商品类型">
                    <template v-slot="scope">
                      <p v-if="scope.row.gradeId === '1622126048693526530'">一等品白菜</p>
                      <p v-if="scope.row.gradeId === '1622126136669052930'">二等品白菜</p>
                      <p v-if="scope.row.gradeId === '1622126237978271745'">三等品白菜</p>
                      <p v-if="scope.row.gradeId === '1586993961175216129'">一等品鸭梨</p>
                      <p v-if="scope.row.gradeId === '1622126732276998146'">二等品鸭梨</p>
                      <p v-if="scope.row.gradeId === '1622126771120447489'">二等品鸭梨</p>
                      <p v-if="scope.row.gradeId === '1622126753047191553'">三等品鸭梨</p>
                      <p v-if="scope.row.gradeId === '1622126897452883969'">一等品油柑</p>
                      <p v-if="scope.row.gradeId === '1622126938838081537'">二等品油柑</p>
                      <p v-if="scope.row.gradeId === '1622126967548092418'">三等品油柑</p>
                      <p v-if="scope.row.gradeId === '1622126994815262722'">三等品油柑</p>
                      <p v-if="scope.row.gradeId === '1622127071147401217'">一等品橄榄</p>
                      <p v-if="scope.row.gradeId === '1622127090407645185'">二等品橄榄</p>
                      <p v-if="scope.row.gradeId === '1622127110670327809'">三等品橄榄</p>
                      <p v-if="scope.row.gradeId === '1622127381546868737'">西芹</p>
                      <p v-if="scope.row.gradeId === '1622127435917631489'">药芹</p>
                      <p v-if="scope.row.gradeId === '1622127405320183809'">香芹</p>
                      <p v-if="scope.row.gradeId === '1622127566305959937'">白萝卜</p>
                      <p v-if="scope.row.gradeId === '1622127526678175745'">胡萝卜</p>
                      <p v-if="scope.row.gradeId === '1586993961175216130'">一等品苹果</p>
                      <p v-if="scope.row.gradeId === '1586993961175216136'">二等品苹果</p>
                      <p v-if="scope.row.gradeId === '1586993961175216325'">三等品苹果</p>
                    </template>
            </el-table-column>
            <el-table-column
                    prop="goodsName"
                    header-align="center"
                    align="center"
                      label="商品名">
            </el-table-column>
            <el-table-column
                    prop="stock"
                    header-align="center"
                    align="center"
                      label="库存">
            </el-table-column>
            <el-table-column
                    prop="sales"
                    header-align="center"
                    align="center"
                    width="100"
                      label="商品销量">
            </el-table-column>
            <el-table-column
                    prop="goodsImage"
                    header-align="center"
                    align="center"
                    width="180"
                    label="商品展示图">
                    <template v-slot="scope">
                      <div v-if="isImgUrlReady">
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
                      label="商品介绍">
            </el-table-column>
            <el-table-column
                    prop="status"
                    header-align="center"
                    align="center"
                      label="起售状态">
                      <template v-slot="scope">
                        <p v-if="scope.row.status === 0">未开售</p>
                        <p v-if="scope.row.status === 1">销售中</p>
                        <p v-if="scope.row.status === 2">已售罄</p>
                        <p v-if="scope.row.status === 9">待审核</p>
                      </template>
            </el-table-column>
            <el-table-column
                    prop="updateBy"
                    header-align="center"
                    align="center"
                      label="修改人">
            </el-table-column>
            <el-table-column
                    prop="createBy"
                    header-align="center"
                    align="center"
                      label="创建人"
                      >
            </el-table-column>
            <el-table-column
                    prop="updateTime"
                    header-align="center"
                    align="center"
                      label="修改时间"
                      width="200">
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
              <!-- <el-button  size="small" icon="el-icon-edit" @click="handleUpdate(scope.row.goodsId)">修改</el-button> -->
              <!-- <el-button  size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.goodsId)">删除</el-button> -->
              <el-button v-if="permissions.goods_update" type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.goodsId)">修改</el-button>
              <el-button v-if="permissions.goods_goods_del" type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.goodsId)">删除</el-button>
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
  import {fetchList, delObj} from '@/api/admin/goods'
  import TableForm from './goods-form.vue'
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        isImgUrlReady: false,
        pageIndex: 1,
        pageSize: 5,
        totalPage: 1,
        dataListLoading: true,
        addOrUpdateVisible: true,
        page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条,
        isAsc: false //是否倒序
      },
      }
    },
    components: {
      TableForm
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
          this.dataList = response.data.data.records
          console.log('处理前',this.dataList);
          this.totalPage = Number(response.data.data.total)
          for(var i = 0;i<this.dataList.length;i++){
            const regex = /"([^"]*)"/; // 匹配两个引号之间的内容
            this.dataList[i].goodsImage = this.dataList[i].goodsImage.match(regex)
          }
          this.isImgUrlReady = true;  
          console.log("数据列表",this.dataList); 
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
      // 修改
      handleUpdate(row, index) {
      this.$refs.crud.rowEdit(row, index);
      this.form.password = undefined;
    },
      // 删除
      deleteHandle (id) {
        this.$confirm('是否确认删除该商品?','提示', {
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
