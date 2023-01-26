<template>
  <div class="test">
    <div style="">부산 동래구 충렬대로 255</div>
    <div style="">싸피원룸</div>
    <div style="">부산 동래구 충렬대로 255</div>
  </div>
  <el-container><el-main id="map"></el-main></el-container>
</template>

<style scoped>
#map {
  width: 400px;
}
</style>

<script lang="ts">
import { defineComponent, onMounted, watch } from "@vue/runtime-core";

declare global {
  interface Window {
    kakao: any;
    map: any;
  }
}

export default defineComponent({
  props: {
    GetAddress: { type: String },
    GetList: { type: Array },
  },

  setup(props) {
    // let markers: number[] = [];
    watch(
      () => props.GetAddress,
      () => {
        changeCenter(props.GetAddress);
      }
    );
    watch(
      () => props.GetList,
      () => {
        displayMarker(window.map, props.GetList);
        console.log(infowindows.length);
        for (let i = 0; i < infowindows.length; i++) {
          infowindows[i].close();
        }
      },
      { deep: true }
    );

    const clickListener = (map, marker, infowindow) =>
      function () {
        infowindow.open(map, marker);
        infowindows.push(infowindow);
      };

    const markerPositions1: object[] = [
      {
        address: "경상북도 구미시 인동6길 26-2",
        a: 36.1020372425131,
        b: 128.420294611527,
      },
      {
        address: "부산 동래구 충렬대로 255",
        a: 35.2014786272255,
        b: 129.087166169007,
      },
    ];

    const latitude = 36.1020372425131;
    const longitude = 128.420294611527;
    onMounted(() => {
      const mapScript = document.createElement("script");
      mapScript.async = true;
      mapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_MAP_KEY}&libraries=services`;

      document.head.appendChild(mapScript);

      mapScript.addEventListener("load", initMap);
      return () => mapScript.removeEventListener("load", initMap);
    });
    const initMap = () => {
      window.kakao.maps.load(() => {
        const container = document.getElementById("map");
        const options = {
          center: new window.kakao.maps.LatLng(latitude, longitude),
        };
        window.map = new window.kakao.maps.Map(container, options);
        // displayMarker(window.map, markerPositions1);
      });
    };
    const setBounds = bounds => {
      window.map.setBounds(bounds);
    };
    let infowindows: any[] = [];

    let markers: any[] = [];

    const displayMarker = (map, markerList) => {
      if (markers.length > 0) {
        for (let i = 0; i < markers.length; i++) {
          markers[i].setMap(null);
        }
      }
      let num = 0;
      let bounds = new window.kakao.maps.LatLngBounds();
      let geocoder = new window.kakao.maps.services.Geocoder();
      for (let i = 0; i < markerList.length; i++) {
        geocoder.addressSearch(
          markerList[i].address,
          function (result, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === window.kakao.maps.services.Status.OK) {
              let coords = new window.kakao.maps.LatLng(
                result[0].y,
                result[0].x
              );
              let imageSrc =
                  "https://cdn-icons-png.flaticon.com/512/2776/2776067.png",
                imageSize = new window.kakao.maps.Size(50, 50),
                imageOption = { offset: new window.kakao.maps.Point(25, 45) };
              let markerImage = new window.kakao.maps.MarkerImage(
                  imageSrc,
                  imageSize,
                  imageOption
                ),
                markerPosition = coords;

              // 결과값으로 받은 위치를 마커로 표시합니다
              let marker = new window.kakao.maps.Marker({
                map: map,
                position: markerPosition,
                image: markerImage,
              });

              setInfoWindow(coords, marker, "set");

              marker.setMap(map);
              markers.push(marker);
              bounds.extend(coords);
            }
            num++;
            if (num == markerList.length) {
              setBounds(bounds);
            }
          }
        );
      }
    };

    const changeCenter = address => {
      let geocoder = new window.kakao.maps.services.Geocoder();
      geocoder.addressSearch(address, function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          let coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
          window.map.setCenter(coords);
          window.map.setLevel(1);
          let marker = new window.kakao.maps.Marker({
            position: coords,
          });
          marker.setMap(window.map);
          markers.push(marker);

          setInfoWindow(coords, marker, "click");
        }
      });
    };

    const setInfoWindow = (coords, marker, type) => {
      let infowindow = new window.kakao.maps.InfoWindow({
        content:
          '<div  style="width: 250px; height: 100px; text- align: center; padding: 10px 0; border: 1px solid red"> asd < /div>',
        removable: true,
      });
      window.kakao.maps.event.addListener(
        marker,
        "click",
        clickListener(window.map, marker, infowindow)
      );
      console.log(type);
      if (type == "click") {
        infowindow.open(
          window.map,
          new window.kakao.maps.Marker({
            position: coords,
          })
        );
        infowindows.push(infowindow);
      }
    };

    return {
      markerPositions1,
      clickListener,
      changeCenter,
      props,
    };
  },
});
</script>
<style scoped>
.info-window {
  border: 1px solid red;
  width: 150px;
  height: 200px;
  text-align: center;
  padding: 6px 0;
}
.test {
  text-align: center;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;

  border: 1px solid rgb(203, 203, 203);
  border-radius: 25px;
  width: 300px;
  height: 250px;
}
</style>
