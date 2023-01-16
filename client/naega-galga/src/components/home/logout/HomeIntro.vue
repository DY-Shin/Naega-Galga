<template>
  <div class="container-1">
    <div class="main-text">온라인으로 집을 구경하고 계약까지.</div>
    <br />
    <div class="sub-text">
      온라인 설명회와 화상 채팅으로 <br />편리하게 집을 구하세요.
    </div>
  </div>

  <div class="container-2">
    <div style="text-align: center">
      <img
        src="https://cdn.pixabay.com/photo/2016/11/22/19/11/brick-wall-1850095_960_720.jpg"
        alt="img"
        vertical:middle
      />
      <div class="content" style="display: inline-block">
        <div class="main-text-right">온라인으로 집을 구경하세요.</div>
        <div class="sub-text-right">
          원하는 지역으로 검색해 <br />매물을 볼 수 있어요.
        </div>
      </div>
    </div>
  </div>
  <div class="container-3">
    <div style="text-align: center">
      <div class="content" style="display: inline-block">
        <div class="main-text-left">지도를 이용하세요.</div>
        <div class="sub-text-left">
          지도를 움직여 해당하는 지역의<br />
          매물 정보를 볼 수 있어요.
        </div>
      </div>
      <img
        src="https://cdn.pixabay.com/photo/2016/11/22/19/11/brick-wall-1850095_960_720.jpg"
        alt="img"
        vertical:middle
        width="500"
        height="350"
      />
    </div>
  </div>

  <div class="container-4">
    <div style="text-align: center">
      <img
        src="https://cdn.pixabay.com/photo/2016/11/22/19/11/brick-wall-1850095_960_720.jpg"
        alt="img"
        width="450"
        height="600"
        vertical:middle
      />
      <div class="content" style="display: inline-block">
        <div class="main-text-right">판매자에게 문의하세요.</div>
        <div class="sub-text-right">
          판매자는 구매자의 관심 목록을 <br />미리 보고 준비할 수 있어요.
        </div>
      </div>
    </div>
  </div>
  <div class="container-5">
    <div style="text-align: center">
      <div class="content" style="display: inline-block">
        <div class="main-text-left">온라인 설명회에 참여하세요.</div>
        <div class="sub-text-left">
          매물에 온라인 설명회가 예정되어있다면 <br />누구라도 참여할 수 있어요.
        </div>
      </div>
      <img
        src="https://cdn.pixabay.com/photo/2016/11/22/19/11/brick-wall-1850095_960_720.jpg"
        alt="img"
        width="700"
        height="400"
        vertical:middle
      />
    </div>
  </div>
</template>
<script lang="ts">
export default {
  name: "HomeContent",
};
</script>

<style scoped>
.main-text {
  font-size: 50px;
}
.sub-text {
  font-size: 40px;
}
.main-text-right {
  margin-left: 200px;
  font-size: 45px;
}
.sub-text-right {
  margin-left: 200px;
  font-size: 30px;
}
.main-text-left {
  margin-right: 200px;
  font-size: 45px;
}
.sub-text-left {
  margin-right: 200px;
  font-size: 30px;
}
img {
  vertical-align: middle;
}
.container-1 {
  text-align: center;
  padding: 150px 0;
  font-size: 40px;
  background-color: #dedfe0;
}
.container-2 {
  text-align: center;
  padding: 150px 0;
  font-size: 40px;
}
.container-3 {
  text-align: center;
  padding: 150px 0;
  font-size: 40px;
  background-color: #dedfe0;
}
.container-4 {
  text-align: center;
  padding: 100px 0;
  font-size: 40px;
}
.container-5 {
  text-align: center;
  padding: 200px 0;
  font-size: 40px;
  background-color: #dedfe0;
}
.content {
  vertical-align: middle;
}
.content div {
  /* margin-left: 100px; */
  padding: 20px 0;
}
</style>
