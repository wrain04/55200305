<template>
  <div class="Article_Content">
    <section>
      <div id="content">
        <div >
          <p class="article_title">{{informationList.informationTitle}}</p>
          <p class="article_author">作者：{{informationList.informationAuthor}}</p>
          <p class="article_text_message">发布时间:{{informationList.releaseTime}} 点击数：{{informationList.viewNumber}}</p>
          <a-divider />
          <p>{{informationList.info}}</p>
          <img :src="informationList.cover" alt="">
          <p class="article_text_content" v-html="this.article_text_content">
          </p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";
import qs from "qs";
export default {
  name:"informationDetail",
  props:{

  },
  data() {
    return {
      informationList:[],
      article_id:this.$route.query.id,
      article_text_title:"",
      article_text_message:"",
      article_text_content:''
    }
  },
  created(){
    this.get_article_data()
  },
  methods:{
    get_article_data(){
      axios({
        url:`/admin/informationApi/` + this.article_id,
        method:"get",
        transformRequest:[
          function(data){
            data = qs.stringify(data);
            return data;
          }
        ],
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
        dataType: "json",
      })
        .then((res) => {
          console.log("连接成功"); // 这里多打印一句提示，只是为了更直观一点
          console.log(res); // res 是后端回传的数据，如果连接成功，可以把res打印出来。
          this.informationList = res.data.data
      }).catch(function(err){
        console.log("连接失败");
      })
    }
  }
}
</script>

<style scoped>
.article_title{
  font-size: 30px;
  font-weight: bold;
}

.article_author{
  color: #409eff;
}

.article_text_message{
  font-size: 16px;
  color: #2f3438
}
</style>