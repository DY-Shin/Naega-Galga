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
import { useRouter } from "vue-router";
import { useStore } from "vuex";

declare global {
  interface Window {
    kakao: any;
    map: any;
  }
}

export default defineComponent({
  props: {
    getIdx: { type: Number },
    getList: { type: Array },
    getClick: { type: Boolean },
  },

  setup(props) {
    const router = useRouter();
    const store = useStore();

    watch(
      () => props.getClick,
      () => {
        changeCenter(props.getIdx);
      }
    );
    watch(
      () => props.getIdx,
      () => {
        changeCenter(props.getIdx);
      }
    );
    watch(
      () => props.getList,
      () => {
        for (let i = 0; i < overlays.length; i++) {
          overlays[i].setMap(null);
        }

        displayMarker(window.map, props.getList);
      },
      { deep: true }
    );

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
      markers.splice(0);
      overlays.splice(0);

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
          markerList[i].roadAddr,
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
              setOverlay(coords, marker, markerList[i]); // 상세 정보 창 만들어주고
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
      // 모든 마커 범위 포함하도록 지도 범위 재설정
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
      img.src = `${process.env.VUE_APP_API_BASE_URL}api/image/display?path=${product.photo}`;
      img.width = 280;
      img.height = 200;
      img.className = "overlayimg";
      content.appendChild(img);

      let topbox = document.createElement("div");
      topbox.className = "topbox";

      let rooms = document.createElement("div");
      rooms.className = "overlay-rooms";
      rooms.appendChild(document.createTextNode(product.rooms));
      topbox.appendChild(rooms);

      let price = document.createElement("div");
      price.className = "overlay-price";
      price.appendChild(document.createTextNode(product.price));
      topbox.appendChild(price);

      let type = document.createElement("div");
      type.className = "overlay-type";
      type.appendChild(document.createTextNode(product.type));
      topbox.appendChild(type);

      content.appendChild(topbox);

      let bottombox = document.createElement("div");

      let detailbtn = document.createElement("button");
      detailbtn.className = "detailbtn";
      detailbtn.appendChild(document.createTextNode("상세보기"));
      detailbtn.onclick = function () {
        moveToDetail();
      };
      bottombox.appendChild(detailbtn);

      const moveToDetail = () => {
        // 상세보기 페이지 이동
        router.push(`/product/${product.productIndex}`);
      };

      let chatbtn = document.createElement("button");
      chatbtn.className = "chatbtn";
      chatbtn.appendChild(document.createTextNode("문의하기"));

      chatbtn.onclick = function () {
        let productInfo = {
          userIndex: product.sellerIndex,
          userName: product.sellerName,
        };

        store.commit("chatStore/GET_PRODUCT_INFO", productInfo);
        store.commit("chatStore/CHANGE_CHATROOM_STATUS", true);
        store.commit("chatStore/CHANGE_GET_CHAT_CONTENT", true);
        console.log("click");
      };

      bottombox.appendChild(chatbtn);

      let clearbox = document.createElement("div");
      clearbox.className = "clear-box";
      content.appendChild(clearbox);

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
      content.appendChild(closebtnbox);

      content.appendChild(bottombox);

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
  /*원룸*/
  font-size: 20px;
  font-weight: 1000;
  float: left;
  margin: 7px 0 5px 20px;
}
.overlay-type {
  /* 월세 */
  float: right;
  margin: 10px 10px 0 0;
}
.overlay-price {
  /* 100만원 */
  float: right;
  margin: 10px 10px 0 0;
}
.overlay-icon {
  float: right;
  padding: 10px 15px;
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
  float: left;
  padding: 3px 20px;
  margin: 5px 10px 0 15px;
  background: none;
  border: none;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px;
}
.chatbtn {
  cursor: pointer;
  float: left;
  padding: 3px 20px;
  margin: 5px 10px 0 0;
  background: none;
  border: none;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px;
}
</style>
