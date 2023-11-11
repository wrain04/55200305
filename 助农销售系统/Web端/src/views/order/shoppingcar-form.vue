<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    append-to-body
    :close-on-click-modal="false"
    @close="closeDialog()"
    v-model="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="购物车id" prop="orderId" v-if="dataForm.id">
        <el-input v-model="dataForm.orderId" placeholder="购物车id" disabled></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="商家id" prop="businessId">
        <el-input v-model="dataForm.businessId" placeholder="商家id"></el-input>
    </el-form-item>
    <el-form-item label="商品id" prop="goodsId">
        <el-input v-model="dataForm.goodsId" placeholder="商品id"></el-input>
    </el-form-item>
    <el-form-item label="数量" prop="amount">
        <el-input v-model="dataForm.amount" placeholder="数量"></el-input>
    </el-form-item>
    <el-form-item label="价格" prop="price">
        <el-input v-model="dataForm.price" placeholder="价格"></el-input>
    </el-form-item>
    <el-form-item label="修改人" prop="updateBy" v-if="dataForm.id">
        <el-input v-model="dataForm.updateBy" placeholder="修改人" disabled></el-input>
    </el-form-item>
    <el-form-item label="创建人" prop="createBy" v-if="dataForm.id">
        <el-input v-model="dataForm.createBy" placeholder="创建人" disabled></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTime" v-if="dataForm.id">
        <el-input v-model="dataForm.updateTime" placeholder="修改时间" disabled></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime" v-if="dataForm.id">
        <el-input v-model="dataForm.createTime" placeholder="创建时间" disabled></el-input>
    </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/admin/shoppingcar'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          shoppingCarId: '',
          userId: '',
          businessId: '',
          goodsId: '',
          amount: '',
          price: '',
          updateBy: '',
          createBy: '',
          updateTime: '',
          createTime: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],

          businessId: [
            { required: true, message: '商家id不能为空', trigger: 'blur' }
          ],

          goodsId: [
            { required: true, message: '商品id不能为空', trigger: 'blur' }
          ],

          amount: [
            { required: true, message: '数量不能为空', trigger: 'blur' }
          ],

          price: [
            { required: true, message: '价格不能为空', trigger: 'blur' }
          ],

        }
      }
    },
    methods: {
      init (id) {
        this.visible = true;
        this.canSubmit = true;
        this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
            if (id) {
            getObj(id).then(response => {
                this.dataForm = response.data.data
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.canSubmit = false;
            if (this.dataForm.orderId) {
                putObj(this.dataForm).then(data => {
                    this.$notify.success('修改成功')
                    this.visible = false
                    this.$emit('refreshDataList')
                }).catch(() => {
                    this.canSubmit = true;
                });
            } else {
                addObj(this.dataForm).then(data => {
                    this.$notify.success('添加成功')
                    this.visible = false
                    this.$emit('refreshDataList')
                }).catch(() => {
                    this.canSubmit = true;
                });
            }
          }
        })
      },
      //重置表单
      closeDialog() {
          this.$refs["dataForm"].resetFields()
      }
    }
  }
</script>
