<template>
  <el-container
    style="
      height: 103%;
      border-top: 1px solid #bdbdbd;
      border-left: 1px solid #bdbdbd;
    "
    ><el-main id="map"></el-main
  ></el-container>
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

  setup(props, context) {
    const { emit } = context;

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
        console.log("!!!!!!!!!!!!!!!!!!!!");
        displayMarker(window.map, props.GetList);
        for (let i = 0; i < overlays.length; i++) {
          overlays[i].setMap(null);
        }
      },
      { deep: true }
    );

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

    let overlays: any[] = [];
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
        console.log(markerList[i].addr);
        geocoder.addressSearch(markerList[i].addr, function (result, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === window.kakao.maps.services.Status.OK) {
            let coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
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

            setOverlay(coords, marker, markerList[i]); // 상세 정보 창 만들어주고

            marker.setMap(map);
            markers[num] = marker;

            bounds.extend(coords);
          }
          num++;
          if (num == markerList.length) {
            setBounds(bounds);
          }
        });
      }
    };
    const setBounds = bounds => {
      // 모든 마커 범위 포함하게 지도 범위 재설정
      window.map.setBounds(bounds);
    };
    const setOverlay = (coords, marker, product) => {
      let customOverlay = new window.kakao.maps.CustomOverlay({
        position: coords,
        xAnchor: 0.5,
        yAnchor: 1.2,
      });

      let content = document.createElement("div");
      content.className = "overlaybox";

      let img = document.createElement("img");
      img.src =
        "https://cdn.pixabay.com/photo/2023/01/07/07/16/houses-7702757_1280.jpg";
      img.width = 280;
      img.height = 200;
      img.className = "overlayimg";
      content.appendChild(img);

      let topbox = document.createElement("div");
      topbox.className = "topbox";
      let rooms = document.createElement("div");
      rooms.className = "overlay-rooms";
      rooms.appendChild(document.createTextNode(product.rooms));

      let detailbtn = document.createElement("button");
      detailbtn.className = "detailbtn";
      detailbtn.appendChild(document.createTextNode("상세보기"));

      let chatbtn = document.createElement("button");
      chatbtn.className = "chatbtn";
      chatbtn.appendChild(document.createTextNode("문의하기"));
      chatbtn.onclick = function () {
        emit("chatOpen", true);
      };

      topbox.appendChild(rooms);
      topbox.appendChild(chatbtn);
      topbox.appendChild(detailbtn);

      content.appendChild(topbox);

      let clearbox = document.createElement("div");
      clearbox.className = "clear-box";
      content.appendChild(clearbox);

      let priceinfo = document.createElement("div");
      let type = document.createElement("div");
      type.className = "overlay-type";
      type.appendChild(document.createTextNode(product.type));

      let price = document.createElement("div");
      price.className = "overlay-price";
      price.appendChild(document.createTextNode(product.price));

      let closebtnbox = document.createElement("div");
      closebtnbox.onclick = () => {
        customOverlay.setMap(null);
      };
      let closebtn = document.createElement("img");
      closebtn.className = "overlay-icon";
      closebtn.src = "https://cdn-icons-png.flaticon.com/512/1828/1828665.png";
      closebtn.width = 15;
      closebtn.height = 15;
      closebtnbox.appendChild(closebtn);

      priceinfo.appendChild(type);
      priceinfo.appendChild(price);
      priceinfo.appendChild(closebtnbox);

      content.appendChild(priceinfo);

      window.kakao.maps.event.addListener(marker, "click", function () {
        customOverlay.setMap(window.map);
      });
      overlays.push(customOverlay);
      customOverlay.setContent(content);
    };

    const changeCenter = addr_idx => {
      // 목록에서 선택 시 해당 위치로 지도 중심 이동, 상세 정보 창 열림
      let idx = nums[addr_idx];
      let marker = markers[idx];
      let coords = marker.getPosition();
      overlays[idx].setMap(window.map);
      window.map.setLevel(1);
      window.map.setCenter(coords);
    };

    return {
      markerPositions1,
      changeCenter,
      props,
    };
  },
});
</script>
<style>
.overlaybox {
  width: 280px;
  height: 280px;
  border-radius: 15px;
  background-color: white;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}
.overlay-rooms {
  float: left;
  margin: 10px 20px;
}
.overlay-type {
  float: left;
  padding: 0 20px 0 20px;
}
.overlay-price {
  float: left;
}
.overlay-icon {
  float: right;
  padding: 0 15px;
}
.overlayimg {
  border-radius: 15px 15px 0 0;
}
.content {
  z-index: 1000;
}
.clear-box {
  clear: both;
}
.detailbtn {
  cursor: pointer;
  float: right;
  padding: 3px 10px;
  margin-top: 5px;
  margin-right: 10px;
  background: none;
  border: none;
  border-radius: 3px;
  box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px;
}
.chatbtn {
  cursor: pointer;
  float: right;
  padding: 3px 10px;
  margin-top: 5px;
  margin-right: 10px;
  background: none;
  border: none;
  border-radius: 3px;
  box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px;
}
</style>
