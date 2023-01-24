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
["GetAddress"];

declare global {
  interface Window {
    kakao: any;
    map: any;
  }
}

export default defineComponent({
  props: { GetAddress: { type: String } },

  setup(props) {
    watch(
      () => props.GetAddress,
      () => {
        console.log("getAddress!!");
        changeCenter(props.GetAddress);
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
      mapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=eca171c688a86c3acc3456ba72a34e6b
&libraries=services`;

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
        displayMarker(window.map, markerPositions1);
      });
    };
    const displayMarker = (map, markerList) => {
      console.log(markerList.length + "!!!");
      if (markerList.length > 0) {
        // markers.forEach(marker => marker.setMap(null));
      }
      let bounds = new window.kakao.maps.LatLngBounds();
      if (markerList.length > 0) {
        for (let i = 0; i < markerList.length; i++) {
          let marker = new window.kakao.maps.Marker({
            position: new window.kakao.maps.LatLng(
              markerList[i].latlng[0],
              markerList[i].latlng[1]
            ),
          });
          marker.setMap(map);
          bounds.extend(
            new window.kakao.maps.LatLng(
              markerList[i].latlng[0],
              markerList[i].latlng[1]
            )
          );

          let infowindow = new window.kakao.maps.InfoWindow({
            content:
              '<div class="" style="width: 150px; height: 100px; text- align: center; padding: 10px 0; border: 1px solid red"> asd < /div>',
            removable: true,
          });
          window.kakao.maps.event.addListener(
            marker,
            "mouseover",
            makeOverListener(map, marker, infowindow)
          );
          window.kakao.maps.event.addListener(
            marker,
            "mouseout",
            makeOutListener(infowindow)
          );
        }
        map.setBounds(bounds);
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
