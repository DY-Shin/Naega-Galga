<template>
  <el-container><el-main id="map"></el-main></el-container>
</template>

<style scoped>
#map {
  width: 400px;
  /* height: 800px; /
  border-top: 1px solid #73767a;
  / border: 1px solid #73767a;
  border-radius: 0 7px 7px 0; */
}
</style>

<script>
export default {
  name: "KakaoMap",
  props: {
    GetAddress: String,
    GetList: Array,
  },
  data() {
    return {
      markersOpen: [],
      markerPositions1: [
        {
          title: "카카오",
          latlng: [33.450705, 126.570677],
        },
        {
          title: "생태연못",
          latlng: [33.450936, 126.569477],
        },
      ],

      markers: [],
    };
  },
  watch: {
    GetAddress() {
      this.changeCenter(this.GetAddress, this.map);
    },
  },

  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      this.initMap;
    }

    const script = document.createElement("script");
    script.onload = () => kakao.maps.load(this.initMap);
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=eca171c688a86c3acc3456ba72a34e6b
&libraries=services`;

    document.head.appendChild(script);
  },
  methods: {
    makeOverListener(map, marker, infowindow) {
      return function () {
        infowindow.open(map, marker);
      };
    },

    makeOutListener(infowindow) {
      return function () {
        infowindow.close();
      };
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };

      /*global kakao*/
      this.map = new kakao.maps.Map(container, options);
      this.displayMarker();
    },

    displayMarker() {
      // let mapContainer = document.getElementById("map"), // 지도를 표시할 div
      //   mapOption = {
      //     center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
      //     level: 3, // 지도의 확대 레벨
      //   };

      // let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

      if (this.markers.length > 0) {
        this.markers.forEach(marker => marker.setMap(null));
      }
      let bounds = new kakao.maps.LatLngBounds();
      if (this.markerPositions1.length > 0) {
        for (let i = 0; i < this.markerPositions1.length; i++) {
          let marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(
              this.markerPositions1[i].latlng[0],
              this.markerPositions1[i].latlng[1]
            ),
            // image: icon,
          });
          marker.setMap(this.map);
          this.markers.push(marker);
          bounds.extend(
            new kakao.maps.LatLng(
              this.markerPositions1[i].latlng[0],
              this.markerPositions1[i].latlng[1]
            )
          );

          let infowindow = new kakao.maps.InfoWindow({
            content:
              '<div class="" style="width: 150px; height: 100px; text- align: center; padding: 10px 0; border: 1px solid red">asd</div>',
            removable: true,
          });
          kakao.maps.event.addListener(
            marker,
            "mouseover",
            this.makeOverListener(this.map, marker, infowindow)
          );
          kakao.maps.event.addListener(
            marker,
            "mouseout",
            this.makeOutListener(infowindow)
          );
        }
        this.map.setBounds(bounds);
      }
    },

    changeCenter(address, map) {
      let geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          console.log(coords);
          map.setCenter(coords);
          map.setLevel(1);
        }
      });
    },
  },
};
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
