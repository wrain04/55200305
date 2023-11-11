<template>
  <el-dialog
    :title="!dataForm.id ? '修改' : '新增'"
    append-to-body
    :close-on-click-modal="false"
    @close="closeDialog()"
    v-model="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="品质等级" prop="gradeId" v-if="dataForm.id">
        <el-input v-model="dataForm.gradeId" placeholder="品质等级"></el-input>
    </el-form-item>
    <el-form-item label="商品名" prop="goodsName">
        <el-input v-model="dataForm.goodsName" placeholder="商品名"></el-input>
    </el-form-item>
    <el-form-item label="商品ID" prop="goodsId">
        <el-input v-model="dataForm.goodsId" placeholder="商品ID" disabled></el-input>
    </el-form-item>
    <el-form-item label="商品分类" prop="gradeId">
       <el-cascader v-slot="scope"
                    v-model="dataForm.gradeId"
                    :options="options"
                    :props="{ expandTrigger: 'hover' }"
                    :show-all-levels="false"
                    @change="handleChange"
                    ></el-cascader>
        <!-- <el-input v-model="dataForm.gradeId" placeholder="品质等级" disabled></el-input> -->
    </el-form-item>
    <el-form-item label="库存" prop="stock">
        <el-input v-model="dataForm.stock" placeholder="库存"></el-input>
    </el-form-item>
    <el-form-item label="商品销量" prop="sales">
        <el-input v-model="dataForm.sales" placeholder="商品销量"></el-input>
    </el-form-item>
    <el-form-item label="起售状态" prop="status">
      <el-select v-model="dataForm.status"
                 placeholder="请选择">
          <el-option v-for="item in StatusOptions"
                     :key="item.value"
                    :label="item.label"
                    :value="item.value"></el-option>
      </el-select>
        <!-- <el-input v-model="dataForm.status" placeholder="起售状态"></el-input> -->
    </el-form-item>
    <el-form-item label="商品展示图" prop="images">
        <el-input v-model="dataForm.images" placeholder="商品展示图"></el-input>
    </el-form-item>
    <el-form-item label="商品介绍" prop="description">
        <el-input type="textarea" :row="3" v-model="dataForm.description" placeholder="商品介绍"></el-input>
    </el-form-item>
    
    <el-form-item label="修改人" prop="updateBy" v-if="dataForm.id">
        <el-input v-model="dataForm.updateBy" placeholder="修改人" disabled></el-input>
    </el-form-item>
    <el-form-item label="创建人" prop="createBy" v-if="dataForm.id">
        <el-input v-model="dataForm.createBy" placeholder="创建人" disabled></el-input>
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
    import {getObj, addObj, putObj} from '@/api/admin/goods.js'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          userId: '',
          businessId: '',
          gradeId: '',
          goodsName: '',
          stock: '',
          sales: '',
          imageList: '',
          description: '',
          status: '',
          updateBy: '',
          createBy: '',
          updateTime: '',
          createTime: '',
        },
        StatusOptions:[{
          value:0,
          label:'未开售'
        },{
          value:1,
          label:'销售中'
        },{
          value:2,
          label:'已售罄'
        },],
        options:[{
          value:'1586993961175216129',
          label:'蔬菜',
          children:[{
            value:'1622126048693526530',
            label:'白菜',
          },{
            value:'1622102957259063297',
            label:'芹菜'
          },{
            value:'1622104628211056642 ',
            label:'萝卜',
          }]
        },{
          value:'1586993984378105857',
          label:'水果',
          children:[{
            value:'1586993961175216131',
            label:'苹果',
          },{
            value:'1586993984378105859',
            label:'梨',
          },{
          value:'1586993984378105857',
          label:'热带水果',
        }]
        },],
        dataRule: {
          businessId: [
            { required: true, message: '商家id不能为空', trigger: 'blur' }
          ],

          gradeId: [
            { required: true, message: '等级id不能为空', trigger: 'blur' }
          ],

          goodsName: [
            { required: true, message: '商品名不能为空', trigger: 'blur' }
          ],

          stock: [
            { required: true, message: '库存不能为空', trigger: 'blur' }
          ],

          sales: [
            { required: true, message: '商品销量不能为空', trigger: 'blur' }
          ],

          // goodsImage: [
          //   { required: true, message: '商品展示图不能为空', trigger: 'blur' }
          // ],

          description: [
            { required: true, message: '商品介绍不能为空', trigger: 'blur' }
          ],

          status: [
            { required: true, message: '起售状态不能为空', trigger: 'blur' }
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
                console.log("修改之前",this.dataForm);
                console.log("触发");
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.canSubmit = false;
            if (this.dataForm.goodsId) {
                putObj(this.dataForm).then(data => {
                    this.$notify.success('修改成功')
                    this.visible = false
                    this.$emit('refreshDataList')
                    console.log("提交了什么",this.dataForm);
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
      },
      handleChange(value) {
        const selectedValue = value[value.length - 1];
          console.log(selectedValue); // 输出选中的值
          this.dataForm.gradeId = selectedValue
    }
    }
  }
</script>
