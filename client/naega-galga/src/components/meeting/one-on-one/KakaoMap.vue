<template>
  <div id="map"></div>
</template>

<script lang="ts">
import { onMounted, PropType, toRefs, watch } from "vue";
import { MapCenterLatLng } from "@/types/MapTypes";
declare global {
  interface Window {
    kakao: any;
    map: any;
  }
}

export default {
  props: {
    centerLatLng: {
      type: Object as PropType<MapCenterLatLng>,
      x: Number,
      y: Number,
    },
    needPositionTracking: {
      type: Boolean,
    },
  },
  setup(props, { emit }) {
    const { centerLatLng, needPositionTracking } = toRefs(props);

    onMounted(() => {
      if (window.kakao && window.kakao.maps) {
        initMap();
        return;
      }
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
          center: new window.kakao.maps.LatLng(
            36.1020372425131,
            128.420294611527
          ),
        };
        window.map = new window.kakao.maps.Map(container, options);
      });
    };

    const options = {
      enableHighAccuracy: true,
      timeout: 10000,
      maximumAge: 0,
    };

    function changeKakaoMapCenter(coords) {
      const kakaoCoords = new window.kakao.maps.LatLng(coords.y, coords.x);
      window.map.setCenter(kakaoCoords);
      window.map.setLevel(5);
      const marker = new window.kakao.maps.Marker({
        position: kakaoCoords,
      });

      marker.setMap(window.map);
    }

    //판매자용
    function changeMapCenterWithMyPosition(position) {
      const coords = position.coords;

      //지도 중심 이동
      changeKakaoMapCenter({ x: coords.longitude, y: coords.latitude });
      emit("changeSellerPosition", { x: coords.longitude, y: coords.latitude });
    }

    function getPositionError(error) {
      console.log(error);
    }

    //판매자는 자신의 위치
    if (needPositionTracking) {
      navigator.geolocation.watchPosition(
        changeMapCenterWithMyPosition,
        getPositionError,
        options
      );
    }
    //구매자는 props 기준으로 map 그림
    else {
      watch(
        () => centerLatLng.value,
        () => {
          changeKakaoMapCenter(centerLatLng.value);
        }
      );
    }
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 100%;
}
</style>
