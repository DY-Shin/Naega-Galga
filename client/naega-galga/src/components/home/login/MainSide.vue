<template>
  <el-aside style="height: 100%">
    <!-- search bar start -->
    <div class="search-bar" style="display: inline-flex">
      <div class="search-icon">
        <img
          class="search-icon"
          src="https://cdn.pixabay.com/photo/2017/01/13/01/22/magnifying-glass-1976105_960_720.png"
          width="30"
          height="30"
        />
      </div>
      <el-input
        class="search-input"
        v-model="input"
        placeholder=" 검색"
        clearable
      />
    </div>
    <el-scrollbar :style="{ height: 'calc(100vh - 225px)' }">
      <div v-for="item in 10" :key="item" class="scrollbar-demo-item">
        <div class="img-content">
          <div class="list-img"></div>
          <div class="list-online-icon">
            <div class="text">온라인 설명회</div>
          </div>
        </div>
        <div>
          <div class="home-area">경상북도 구미시</div>
          <div class="home-price">월세 30</div>
          <div class="home-info"># 주방 분리 # 풀 옵션</div>
        </div>
        <button v-if="isFavorite" id="heart-btn" @click="clickHeart()">
          <img
            src="@/assets/image/icon-heart.png"
            width="20"
            height="20"
          /></button
        ><button v-else id="heart-btn" @click="clickHeart()">
          <img
            src="@/assets/image/icon-heart-filled.png"
            width="20"
            height="20"
          />
        </button>
      </div>
    </el-scrollbar>
  </el-aside>
</template>

<script lang="ts">
import { ref } from "vue";
import { defineComponent } from "vue";
export default defineComponent({
  props: {
    summaryValue: {
      type: Object,
      value: {
        type: String,
        price: String,
        floor: String,
        managePrice: Number,
        explanationDate: [String, null],
        seller: String,
        isFavorite: Boolean,
      },
    },
  },
  setup() {
    const input = ref("");
    const isFavorite = ref(false);
    const clickHeart = () => {
      isFavorite.value = !isFavorite.value;
    };
    return { input, isFavorite, clickHeart };
  },
});
</script>

<style scoped>
.el-input {
  --el-input-text-color: var(--el-text-color-regular);
  --el-input-border: var(--el-border);
  --el-input-hover-border: var(--el-border-color-hover);
  --el-input-focus-border: var(--el-text-color-regular);
  --el-input-transparent-border: 0 0 0 1px transparent inset;
  --el-input-border-color: var(--el-border-color);
  --el-input-border-radius: var(--el-border-radius-base);
  --el-input-bg-color: var(--el-fill-color-blank);
  --el-input-icon-color: var(--el-text-color-placeholder);
  --el-input-placeholder-color: var(--el-text-color-placeholder);
  --el-input-hover-border-color: var(--el-border-color-hover);
  --el-input-clear-hover-color: var(--el-text-color-secondary);
  --el-input-focus-border-color: var(--el-text-color-regular);
}
.search-input {
  width: 450px;
  padding: 10px;
}

#heart-btn {
  /* margin-right: 10px; */
  margin-left: 35px;
  margin-top: 85px;
  border: none;
  background: none;
}
.home-area {
  color: black;
  text-align: left;
  margin: 20px 0;
}
.home-price {
  color: black;
  text-align: left;
  margin: 20px 0;
}
.home-info {
  color: black;
  text-align: left;
  margin: 20px 0;
}
.text {
  padding: 5px;
}
.img-content {
  margin-left: 10px;
  float: left;
  height: 100%;
  width: 50%;
}
.home-name {
  float: right;
}
.list-img {
  position: relative;
  left: 20px;
  top: 40px;
  text-align: center;
  padding: 60px 0;
  width: 200px;
  height: auto;
  background-color: #d9d9d9;
  border-radius: 5px;
}

.list-online-icon {
  /* 온라인 설명회 아이콘 */
  color: white;
  position: relative;
  width: 150px;
  height: 30px;
  top: -95px;
  left: 80px;
  background-color: #ff4444;
  border-radius: 7px;
  box-shadow: 1px 2px 2px 2px rgb(147, 147, 147);
}
.scrollbar-demo-item {
  border-bottom: 1px solid #575757;
  display: flex;
  align-items: center;
  height: 200px;
  text-align: center;
  color: var(--el-color-primary);
}
.search-bar::-webkit-scrollbar-track {
  background-color: #575757;
}
.search-bar {
  width: 100%;
  /* height: 360px; */
  border-bottom: 2px solid #73767a;
}
.search-icon {
  /* display: inline; */
  padding: 5px 0 5px 5px;
}

.el-aside {
  width: 500px;
  height: 100%;
  border-top: 1px solid #73767a;
  border-right: 1px solid #73767a;
  /* border-left: 1px solid #73767a; */
  /* border: 1px solid #73767a;
  border-radius: 7px 0 0 7px; */
}
</style>
