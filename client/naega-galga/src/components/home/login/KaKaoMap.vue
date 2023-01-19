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
    productList: Array,
  },
  data() {
    return {
      markersOpen: [],
      // map: null,
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
  updated() {
    console.log(this.GetAddress);
    this.searchAddr();
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_MAP_KEY}&libraries=services`;

      document.head.appendChild(script);
    }
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

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
      this.displayMarker();
    },

    displayMarker() {
      let mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
          level: 3, // 지도의 확대 레벨
        };

      let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
      // const imgSrc = require(https://icons8.com/icon/2436/place-marker");
      // const imgSiz = new kakao.maps.markerSize(24, 35);
      // const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSiz);

      let icon = new kakao.maps.MarkerImage(
        "https://cdn.icon-icons.com/icons2/916/PNG/512/Marker_icon-icons.com_71852.png",
        new kakao.maps.Size(60, 65),
        {
          offset: new kakao.maps.Point(16, 34),
          alt: "마커 이미지 예제",
          shape: "poly",
          coords: "1,20,1,9,5,2,10,0,21,0,27,3,30,9,30,20,17,33,14,33",
        }
      );

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
            image: icon,
          });
          marker.setMap(map);
          this.markers.push(marker);
          bounds.extend(
            new kakao.maps.LatLng(
              this.markerPositions1[i].latlng[0],
              this.markerPositions1[i].latlng[1]
            )
          );
          let infowindow = new kakao.maps.InfoWindow({
            content: this.markerPositions1[i].title, // 인포윈도우에 표시할 내용
          });
          kakao.maps.event.addListener(
            marker,
            "mouseover",
            this.makeOverListener(map, marker, infowindow)
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

    searchAddr() {
      let geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(this.GetAddress, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          // 결과값으로 받은 위치를 마커로 표시합니다
          let marker = new kakao.maps.Marker({
            map: this.map,
            position: coords,
          });
          marker.setMap(this.map);
          // 인포윈도우로 장소에 대한 설명을 표시합니다
          let infowindow = new kakao.maps.InfoWindow({
            content:
              '<div style="width:150px;text-align:center;padding:6px 0;">asd</div>',
          });
          infowindow.open(this.map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          this.map.setCenter(coords);
        }
      });
    },
  },
};
</script>
