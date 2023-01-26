<template>
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
        console.log(props.GetList?.length + "!!!!!!!!!!");
      }
    );
    watch(
      () => props.GetList,
      () => {
        console.log("getList!! ");
        // displayMarker(window.map, props.GetList);
      },
      { deep: true }
    );

    const makeOverListener = (map, marker, infowindow) =>
      function () {
        infowindow.open(map, marker);
      };
    const makeOutListener = infowindow =>
      function () {
        infowindow.close();
      };

    const markerPositions1: object[] = [
      {
        address: "부산 동래구 충렬대로 255",
        a: 35.2014786272255,
        b: 129.087166169007,
      },
      {
        address: "경상북도 구미시 인동6길 26-2",
        a: 36.1020372425131,
        b: 128.420294611527,
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
        // displayMarker(markerPositions1);
        displayMarker2(markerPositions1);
      });
    };

    // const displayMarker = markerList => {
    //   let bounds = new window.kakao.maps.LatLngBounds();
    //   if (markerList.length > 0) {
    //     // markers.forEach(marker => marker.setMap(null));
    //   }
    //   for (let i = 0; i < markerList.length; i++) {
    //     let coords = new window.kakao.maps.LatLng(
    //       markerList[i].a,
    //       markerList[i].b
    //     );
    //     console.log("1 " + coords);
    //     let marker = new window.kakao.maps.Marker({
    //       position: coords,
    //     });
    //     // markers.push([markerList[i].a, markerList[i].b]);
    //     marker.setMap(window.map);
    //     bounds.extend(coords);
    //   }
    //   console.log("1!!! " + bounds);
    //   window.map.setBounds(bounds);
    // };
    const displayMarker2 = markerList => {
      let geocoder = new window.kakao.maps.services.Geocoder();
      let bounds = new window.kakao.maps.LatLngBounds();
      for (let i = 0; i < markerList.length; i++) {
        let callback = function (result, status) {
          if (status === window.kakao.maps.services.Status.OK) {
            let coords = window.kakao.maps.LatLng(result[0].y, result[0].x);
            let marker = new window.kakao.maps.Marker({
              position: coords,
            });
            marker.setMap(window.map);
            bounds.extend(coords);
          }
        };

        geocoder.addressSearch("해남군 송지면", callback);
      }
    };

    const changeCenter = address => {
      let geocoder = new window.kakao.maps.services.Geocoder();
      geocoder.addressSearch(address, function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          let coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
          console.log("3 " + coords);
          window.map.setCenter(coords);
          window.map.setLevel(1);
          let marker = new window.kakao.maps.Marker({
            position: coords,
          });
          marker.setMap(window.map);
          let infowindow = new window.kakao.maps.InfoWindow({
            content:
              '<div class="" style="width: 150px; height: 100px; text- align: center; padding: 10px 0; border: 1px solid red"> asd < /div>',
            removable: true,
          });
          window.kakao.maps.event.addListener(
            marker,
            "mouseover",
            makeOverListener(window.map, marker, infowindow)
          );
          window.kakao.maps.event.addListener(
            marker,
            "mouseout",
            makeOutListener(infowindow)
          );
        }
      });
    };

    return {
      markerPositions1,
      makeOverListener,
      makeOutListener,
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
</style>
