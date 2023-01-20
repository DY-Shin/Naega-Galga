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
        @keyup.enter="getList()"
      />
    </div>
    <el-scrollbar :style="{ height: 'calc(100vh - 225px)' }">
      <div
        v-for="(item, index) in productList"
        :key="item.floor"
        class="scrollbar-demo-item"
        @click="whereIs(index)"
      >
        <div class="img-content">
          <div class="list-img"></div>
          <div class="list-online-icon" v-if="item.explanationDate != null">
            <div class="text">온라인 설명회</div>
          </div>
        </div>
        <div class="home-info-box">
          <div class="home-area">{{ item.address }}</div>
          <div class="home-price">{{ item.productType }} {{ item.price }}</div>
          <div class="home-info">
            <div v-for="opt in item.options" :key="opt" style="display: inline">
              # {{ opt }}
            </div>
          </div>
        </div>
        <button v-if="item.isWish" id="heart-btn" @click="clickHeart()">
          <img
            src="@/assets/image/icon-heart-filled.png"
            width="20"
            height="20"
          /></button
        ><button v-else id="heart-btn" @click="clickHeart()">
          <img src="@/assets/image/icon-heart.png" width="20" height="20" />
        </button>
      </div>
    </el-scrollbar>
  </el-aside>
</template>

<script lang="ts">
import { ref } from "vue";
import { defineComponent } from "vue";
export default defineComponent({
  setup(_, context) {
    const input = ref("");
    const isFavorite = ref(false);
    const clickHeart = () => {
      isFavorite.value = !isFavorite.value;
    };
    const getList = () => {
      //검색 ->  목록 가져오기
    };
    const { emit } = context;
    const whereIs = index => {
      emit("address", productList[index].address);
      emit("productList", productList);
    };

    interface Product {
      productType: string;
      productName: string;
      price: string;
      managePrice: number;
      floor: string;
      roomSize: number;
      address: string;
      roomDirection: string;
      animal: boolean;
      seller?: string;
      explanationDate: string | null;
      parking: number;
      options: Array<string>;
      isWish: boolean;
    }
    const productList: Product[] = [];
    productList.push({
      productType: "월세",
      productName: "싸피빌라",
      price: "30",
      managePrice: 50000,
      floor: "2층",
      roomSize: 30,
      address: "제주특별자치도 제주시 첨단로 242",
      roomDirection: "남향",
      animal: true,
      seller: "싸피",
      explanationDate: "2023.1.30",
      parking: 0,
      options: ["주방분리", "풀 옵션"],
      isWish: true,
    });
    productList.push({
      productType: "월세",
      productName: "싸피빌라",
      price: "30",
      managePrice: 50000,
      floor: "2층",
      roomSize: 30,
      address: "경상북도 구미시 인동6길 26-2",
      roomDirection: "남향",
      animal: true,
      seller: "싸피",
      explanationDate: "2023.1.30",
      parking: 0,
      options: ["에어컨", "냉장고"],
      isWish: true,
    });
    productList.push({
      productType: "월세",
      productName: "싸피빌라",
      price: "30",
      managePrice: 50000,
      floor: "2층",
      roomSize: 30,
      address: "제주 제주시 첨단로 241",
      roomDirection: "남향",
      animal: true,
      seller: "싸피",
      explanationDate: "2023.1.30",
      parking: 0,
      options: ["베란다", "주방분리", "에어컨", "냉장고"],
      isWish: true,
    });

    return { input, isFavorite, clickHeart, productList, getList, whereIs };
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
  /* margin-left: 35px; */
  margin-top: 85px;
  margin-right: 10px;
  border: none;
  background: none;
}
.home-info-box {
  width: 200px;
  /* padding-left: 10px; */
}
.home-area {
  color: black;
  text-align: left;
  /* margin: 20px 0; */
}
.home-price {
  color: black;
  text-align: left;
  margin: 20px 0;
}
.home-info {
  color: black;
  text-align: left;
  /* margin: 20px 0; */
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
