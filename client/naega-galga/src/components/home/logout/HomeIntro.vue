<template>
  <div class="box background-color-grey flex-column">
    <div class="main-text">온라인으로 집을 구경하고 계약까지.</div>
    <br />
    <div class="sub-text">
      온라인 설명회와 화상 채팅으로 <br />편리하게 집을 구하세요.
    </div>
  </div>

  <div class="box flex-row">
    <div>
      <video
        loop
        autoplay
        muted
        width="350"
        style="box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px; border-radius: 10px"
      >
        <source src="@/assets/video/main-1.mp4" type="video/mp4" />
      </video>
    </div>
    <div>
      <div class="main-text">온라인으로 집을 구경하세요.</div>
      <div class="sub-text">
        원하는 지역으로 검색해 <br />매물을 볼 수 있어요.
      </div>
    </div>
  </div>

  <div class="box flex-row background-color-grey">
    <div>
      <div class="main-text">지도를 이용하세요.</div>
      <div class="sub-text">
        지도를 움직여 해당하는 지역의<br />
        매물 정보를 볼 수 있어요.
      </div>
    </div>
    <div>
      <video
        loop
        autoplay
        muted
        width="550"
        style="
          vertical-align: middle;
          box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px;
          border-radius: 10px;
        "
      >
        <source src="@/assets/video/main-2.mp4" type="video/mp4" />
      </video>
    </div>
  </div>

  <div class="box flex-row">
    <div>
      <video
        loop
        autoplay
        muted
        width="350"
        style="
          vertical-align: middle;
          box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px;
          border-radius: 10px;
        "
      >
        <source src="@/assets/video/main-3.mp4" type="video/mp4" />
      </video>
    </div>
    <div>
      <div class="main-text">판매자에게 문의하세요.</div>
      <div class="sub-text">
        일대일 채팅을 통해 <br />화상 미팅을 예약할 수 있어요.
      </div>
    </div>
  </div>
</template>
<script lang="ts">
export default {
  name: "HomeContent",
};
</script>

<style scoped>
* {
  box-sizing: border-box;
  scroll-behavior: smooth;
}
section {
  height: 100vh;
  overflow: auto;
  scroll-snap-type: y mandatory;
}
.main-text {
  font-size: 35px;
  margin-bottom: 15px;
}
.sub-text {
  font-size: 20px;
  text-align: center;
}

img {
  vertical-align: middle;
}

.box {
  scroll-snap-align: start;
  height: 100vh;
  padding: 0 10vw;
}

.flex-column {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.flex-row {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
}

.background-color-grey {
  background-color: #ececec;
}

.box:nth-child(0) {
  height: calc(100vh-100px);
}
.media {
  box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 15px;
}

.margin-bottom-minus {
  margin-bottom: -10vh;
}
</style>
