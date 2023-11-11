<template>
  <avue-data-tabs :option="option1"></avue-data-tabs>
  <avue-data-rotate :option="option2"></avue-data-rotate>
</template>
<script>
export default {
  data () {
    return {
      option1: {
        data: [
          {
            click: function (item) {
              alert(JSON.stringify(item));
            },
            title: '订单总量',
            subtitle: '实时',
            count: 7993,
            allcount: 10222,
            // text: '当前分类总记录数',
            color: 'rgb(27, 201, 142)',
            key: '类'
          },
          {
            click: function (item) {
              alert(JSON.stringify(item));
            },
            title: '当前用户数量',
            subtitle: '实时',
            count: 3112,
            allcount: 10222,
            // text: '当前上传的附件数',
            color: 'rgb(230, 71, 88)',
            key: '附'
          },
          {
            click: function (item) {
              alert(JSON.stringify(item));
            },
            title: '当前商家数量',
            subtitle: '实时',
            count: 908,
            allcount: 10222,
            // text: '评论次数',
            color: 'rgb(178, 159, 255)',
            key: '评'
          },
          
        ]
      },
      option2: {
        span: 6,
        data: [
          {
            // click: function (item) {
            //   alert(JSON.stringify(item));
            // },
            title: '已成交订单',
            count: 12332,
            icon: 'el-icon-warning',
            color: 'rgb(49, 180, 141)',
            href: 'https://avuejs.com',
            target: '_blank'
          },
          {
            click: function (item) {
              alert(JSON.stringify(item));
            },
            title: '完成售后订单',
            count: 33,
            icon: 'el-icon-view',
            color: 'rgb(56, 161, 242)',
            href: 'https://avuejs.com',
            target: '_blank'
          },
          {
            click: function (item) {
              alert(JSON.stringify(item));
            },
            title: '未完成订单',
            count: 2223,
            icon: 'el-icon-setting',
            color: 'rgb(117, 56, 199)',
            href: 'https://avuejs.com',
            target: '_blank'
          },
          {
            click: function (item) {
              alert(JSON.stringify(item));
            },
            title: '新增用户数量',
            count: 2223,
            icon: 'el-icon-upload',
            color: 'rgb(241, 127, 66)',
            href: 'https://avuejs.com',
            target: '_blank'
          },
        ]}
    }
  }
}
</script>