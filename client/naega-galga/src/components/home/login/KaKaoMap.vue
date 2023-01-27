<template>
  <!-- <div class="test">
    <div style="">부산 동래구 충렬대로 255</div>
    <div style="">싸피원룸</div>
    <div style="">부산 동래구 충렬대로 255</div>
  </div> -->
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
    GetIdx: { type: Number },
    GetList: { type: Array },
  },

  setup(props) {
    // let markers: number[] = [];
    watch(
      () => props.GetIdx,
      () => {
        console.log(props.GetIdx);
        changeCenter(props.GetIdx);
      }
    );
    watch(
      () => props.GetList,
      () => {
        displayMarker(window.map, props.GetList);
        // console.log(props.GetList);
        // console.log(markers[0].getPosition);
        // console.log(markers[1].getPosition);
        // console.log(markers[2].getPosition);
        for (let i = 0; i < infowindows.length; i++) {
          infowindows[i].close();
        }
      },
      { deep: true }
    );

    const clickListener = (map, marker, infowindow) =>
      function () {
        infowindow.open(map, marker);
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
      });
    };

    let infowindows: any[] = [];
    let markers: any[] = [];
    let nums: any[] = [];

    const displayMarker = (map, markerList) => {
      // 매물 목록 검색 결과 마커 표시함
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
              nums[i] = num;
              let imageSrc =
                  "https://cdn-icons-png.flaticon.com/512/7976/7976202.png",
                imageSize = new window.kakao.maps.Size(40, 40),
                imageOption = { offset: new window.kakao.maps.Point(20, 40) };
              let markerImage = new window.kakao.maps.MarkerImage(
                  imageSrc,
                  imageSize,
                  imageOption
                ),
                markerPosition = coords;

              let marker = new window.kakao.maps.Marker({
                map: map,
                position: markerPosition,
                image: markerImage,
              });

              setInfoWindow(coords, marker, markerList[i]); // 상세 정보 창 만들어주고

              marker.setMap(map);
              markers[num] = marker;

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
    const setBounds = bounds => {
      // 모든 마커 범위 포함하게 지도 범위 재설정
      window.map.setBounds(bounds);
    };
    const setInfoWindow = (coords, marker, product) => {
      // 상세 정보 창 만들어서 배열에 넣음 클릭 시 열림 이벤트 등록
      // const move = () => {
      //   console.log("!!");
      //   alert("asd");
      // };
      // const move = () => {
      //   console.log("!!");
      //   alert("!!!");
      // };
      let infowindow = new window.kakao.maps.InfoWindow({
        content: `<button @click="${console.log(
          "!!"
        )}">내가갈가[家]</button><div style="padding:5px;">${product.address} ${
          product.type
        } <br><a href="/" style="color:blue" target="_blank">${
          product.address
        }</a> <a href="https://map.kakao.com/link/to/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">길찾기</a></div>`,
        // content: `<div
        // style="
        // width: 300px;
        // height: 100px;
        // text-align: center;
        // padding: 10px 0;
        // border: 1px solid white">${product.address} ${product.type}
        // <button style="border:none;
        // background: whitesmoke;
        // cursor:pointer"
        // @click="location.href='/'">hi</button>`,
        removable: true,
      });

      window.kakao.maps.event.addListener(
        marker,
        "click",
        clickListener(window.map, marker, infowindow)
      );
      infowindows.push(infowindow);
    };

    const changeCenter = addr_idx => {
      console.log(nums);
      // 목록에서 선택 시 해당 위치로 지도 중심 이동, 상세 정보 창 열림
      let idx = nums[addr_idx];
      let marker = markers[idx];
      let coords = marker.getPosition();

      window.map.setCenter(coords);
      window.map.setLevel(1);
      infowindows[idx].open(
        window.map,
        new window.kakao.maps.Marker({
          position: coords,
        })
      );
      // setInfoWindow(marker.getPosition(), marker);
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
