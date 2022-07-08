<template>
  <div style="padding: 10px;width: 100%">
    <div style=" padding-left:5px; padding-top:7px; padding-bottom:5px">
      <el-breadcrumb
          style="font-size: 90%; "
          separator-icon="ArrowRight"
      >
        <el-breadcrumb-item :to="{ path: '/' }">主页</el-breadcrumb-item>
        <el-breadcrumb-item>充电桩定位</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-card style="margin: 10px 0">
      <label>当前位置:</label>
      <div  style="margin-top: 20px;margin-bottom: 7px">
        <div id="mapContainer" style="height: 500px"></div>
      </div>
      <a href="http://map.zjut.edu.cn/mobile/#/home/" target="_blank" rel="noopener noreferrer">此处前往校园地图（内网）>></a>
    </el-card>

  </div>
</template>

<script>
import request from "@/utils/request";
let map
export default {
  name: 'QueryPile',
  components: {},
  data(){
    return{

    }
  },
  methods: {
    init() {
      map = new AMap.Map("mapContainer", {
        resizeEnable: true,//设置地图可缩放
        zoom: 16,//设置地图的层级
        center: [120.040386,30.228503],//设置地图中心点  更多配置项参照高德官网的配置
      });
      AMap.plugin('AMap.Geolocation', function() {
        var geolocation = new AMap.Geolocation({
          enableHighAccuracy: true,//是否使用高精度定位，默认:true
          timeout: 10000,          //超过10秒后停止定位，默认：5s
          buttonPosition:'RB',    //定位按钮的停靠位置
          buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
          zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点

        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition(function(status,result){
          if(status === 'complete'){
            onComplete(result)
          }else{
            onError(result)
          }
        });
      });
      //解析定位结果
      function onComplete(data) {
        document.getElementById('status').innerHTML='定位成功'
        var str = [];
        str.push('定位结果：' + data.position);
        str.push('定位类别：' + data.location_type);
        if(data.accuracy){
          str.push('精度：' + data.accuracy + ' 米');
        }//如为IP精确定位结果则没有精度信息
        str.push('是否经过偏移：' + (data.isConverted ? '是' : '否'));
        document.getElementById('result').innerHTML = str.join('<br>');
      }
      //解析定位错误信息
      function onError(data) {
        document.getElementById('status').innerHTML='定位失败'
        document.getElementById('result').innerHTML = '失败原因排查信息:'+data.message;
      }
    },

  },

  created() {

  },

  mounted() {
    this.init()
  },

  destroyed() {
    if(!!this.map){
      this.map.destroy();
    }
  }
}
</script>

<style scoped>

</style>