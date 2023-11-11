<template>
  <el-button @click="AddInformation()" type="primary" style="margin:10px">添加资讯</el-button>
  <informationItem :Information_props_Data="Information_props_Data"
                   :project_article_Data="project_article_Data"
    ></informationItem>
  
  <TableFrom v-if="addInformationForm" ref="addOrUpdate" @refreshDataList="getDataList"></TableFrom>
</template>
<script>
import informationItem from '@/components/information/informationItem.vue'
import TableFrom from './information-form.vue'
import {fetchList} from '@/api/admin/information.js'
import {mapGetters} from 'vuex'
export default {
  name:"information",
  data(){
    return{
      current:'1',
      Information_props_Data:{
        current_path:'/information',//不太理解
      },
      project_article_Data:{
        informationId:``,
        informationTitle:``,
        informationAuthor:``,
        createDate:``,
        viewNumber:``
      },
      dataListLoading: true,
      addInformationForm:false
    }
  },
  components:{
    informationItem,
    TableFrom
  },
  created(){
    this.getDataList()
  },
  computed: {
      ...mapGetters(['permissions'])
    },
  methods:{
    getDataList(){
      this.dataListLoading = true
        fetchList(
          Object.assign(
            {
              current: this.pageIndex,
              size: this.pageSize
        })).then(response => {
          this.project_article_Data = response.data.data
          this.totalPage = Number(response.data.data.total)
          console.log(response);
        })
        this.dataListLoading = false
    },
    AddInformation(id){
        this.addInformationForm = true
        console.log();
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
          
        })
    },
    getInformationId(){
      console.log(this.informationItem);
    }
}
}
</script>