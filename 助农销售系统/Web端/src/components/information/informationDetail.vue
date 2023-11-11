<template>
  <div v-if="informationDetail">1</div>
</template>

<script>


export default ({
  name:'informationDetail',
  data() {
    return {
      informationDetail:false
    }
  },
  created() {
    this.start()
  },
  methods: {
    start(){
      console.log("我出现了");
    }
  },
})
</script>

<style >
  .test{
    width: 100px;
    height: 100px;
    background-color: #000;
  }
</style>
