<template>
  <el-container><el-main id="map"></el-main></el-container>
</template>

<style scoped>
#map {
  width: 400px;
}
</style>

<script lang="ts">
import { defineComponent, onMounted, watch, PropType } from "@vue/runtime-core";

declare global {
  interface Window {
    kakao: any;
    map: any;
  }
}

export default defineComponent({
  props: {
    GetAddress: { type: String },
    GetList: { type: Array as PropType<Array<object>> },
  },

  setup(props) {
    watch(
      () => props.GetAddress,
      () => {
        console.log("getAddress!!");
        changeCenter(props.GetAddress);
      }
    );

    watch(
      () => props.GetList,
      () => {
        console.log("getList!! " + props.GetList?.length);
        displayMarker(props.GetList);
        // window.map.setCenter(
        //   new window.kakao.maps.LatLng(33.450705, 126.570677)
        // );
      }
    );

    // let markers: [number[]];
    const makeOverListener = (map, marker, infowindow) =>
      function () {
        infowindow.open(map, marker);
      };
    const makeOutListener = infowindow =>
      function () {
        infowindow.close();
      };

    const markerPositions1: object[] = [
      { title: "카카오", latlng: [33.450705, 126.570677] },
      { title: "생태연못", latlng: [33.450936, 126.569477] },
    ];

    const latitude = 33.450705;
    const longitude = 126.570677;
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
      });
      console.log(changeCenter("경상북도 구미시 인동6길 26 - 2"));
    };

    const displayMarker = markerList => {
      let geocoder = new window.kakao.maps.services.Geocoder();

      if (markerList.length > 0) {
        // markers.forEach(marker => marker.setMap(null));
      }
      let bounds = new window.kakao.maps.LatLngBounds();
      if (markerList.length > 0) {
        for (let i = 0; i < markerList.length; i++) {
          geocoder.addressSearch(
            markerList[i].address,
            function (result, status) {
              if (status === window.kakao.maps.services.Status.OK) {
                let coords = new window.kakao.maps.LatLng(
                  result[0].y,
                  result[0].x
                );
                let marker = new window.kakao.maps.Marker({ position: coords });
                marker.setMap(window.map);
                bounds.extend(coords);
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
            }
          );
        }
        window.map.setBounds(bounds);
      }
    };
    const changeCenter = address => {
      let geocoder = new window.kakao.maps.services.Geocoder();
      geocoder.addressSearch(address, function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          let coords = new window.kakao.maps.LatLng(
            Number(result[0].y),
            Number(result[0].x)
          );
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
