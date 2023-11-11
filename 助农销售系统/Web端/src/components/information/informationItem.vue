<template>
  <el-card shadow="hover" class="information-card" 
  v-for="item in project_article_Data"
  :key="item.informationId"
  @click="to_information_detail(item.informationId)">
    <div >
      <a class="article-title">{{item.informationTitle}}</a>
      <el-button v-if="weight > 0" class="article-icon" type="text">置顶</el-button>
      <!-- <span class="pull-right article-count">
        <i class="icon-comment"></i>&nbsp;{{commentCounts}}
      </span> -->
      <span class="pull-right article-count">
        <i class="icon-comment"></i>浏览数：&nbsp;{{item.viewNumber}}
      </span>
    </div>
    <div class="article-description">
      {{item.informationTitle}}
    </div>
    <div class="article-footer">
      <span class="article-author">
        <i class="icon-author"></i>作者：&nbsp;<el-tag>{{item.informationAuthor}}</el-tag>
      </span>

      <!-- <el-tag v-for="t in tags" :key="t.tagName" size="mini" type="success">{{t.tagName}}</el-tag> -->

      <span class="pull-right article-count">
        <i class="icon-time"></i>发布日期：&nbsp;{{item.createTime}}
      </span>
    </div>
  </el-card>
</template>

<script>
import informationDetail from '@/components/information/informationDetail.vue'
export default {
  name:'informationItem',
  components:{
    informationDetail
  },
  props:{
    project_article_Data:Array,
    Information_props_Data:Object,
  },
  data() {
    return {
      informationDetail:false
    }
  },
  methods:{
    to_information_detail(id){
      this.$router.push({
        path:`${this.Information_props_Data.current_path}/informationDetail`,
        query:{id:id},
      })
    }
  }
}
</script>

<style scoped>
.article-title{
  font-weight: 600;
}

.article-icon{
  padding: 3px 8px;
}

.article-count{
  color: #2b90d9;
  padding-left: 14px;
  font-size: 13px;
}

.pull-right{
  float: right;
}

.article-description{
  font-size: 13px;
  line-height: 24px;
  margin-bottom: 10px;
}

.article-author{
  color: #2b90d9;
  padding-right: 18px;
  font-size: 13px;
}

.el-tag{
  margin-left: 6px;
  color: #ffffff;
  background-color: #2b90d9;
}

a:hover{
  cursor: pointer;
}

.information-card{
  margin: 10px;
  cursor: pointer;
}
</style>