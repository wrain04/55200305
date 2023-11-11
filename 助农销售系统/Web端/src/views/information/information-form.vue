<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    append-to-body
    :close-on-click-modal="false"
    @close="closeDialog()"
    v-model="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="资讯id" prop="informationId" v-if="dataForm.id">
        <el-input v-model="dataForm.informationId" placeholder="资讯id" disabled></el-input>
    </el-form-item>
    <el-form-item label="作者" prop="author">
        <el-input v-model="dataForm.author" placeholder="作者"></el-input>
    </el-form-item>
    <el-form-item label="标题" prop="title">
        <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="文本" prop="text">
        <el-input v-model="dataForm.text" placeholder="文本"></el-input>
    </el-form-item>
    <el-form-item label="图片" prop="image">
        <el-input v-model="dataForm.image" placeholder="图片"></el-input>
    </el-form-item>
    <el-form-item label="logo" prop="logo">
        <el-input v-model="dataForm.logo" placeholder="logo"></el-input>
    </el-form-item>
    <el-form-item label="浏览量" prop="viewNum">
        <el-input v-model="dataForm.viewNum" placeholder="浏览量"></el-input>
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
    import {getObj, addObj, putObj} from '@/api/admin/informationsc.js'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          informationId: '',
          author: '',
          title: '',
          text: '',
          image: '',
          logo: '',
          viewNum: '',
          updateBy: '',
          createBy: '',
          updateTime: '',
          createTime: '',
        },
        dataRule: {
          author: [
            { required: true, message: '作者不能为空', trigger: 'blur' }
          ],

          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],

          text: [
            { required: true, message: '文本不能为空', trigger: 'blur' }
          ],

          image: [
            { required: true, message: '图片不能为空', trigger: 'blur' }
          ],

          logo: [
            { required: true, message: 'logo不能为空', trigger: 'blur' }
          ],

          viewNum: [
            { required: true, message: '浏览量不能为空', trigger: 'blur' }
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
            if (this.dataForm.informationId) {
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
