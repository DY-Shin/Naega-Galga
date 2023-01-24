<template>
  <el-aside style="height: 100%">
    <!-- search bar start -->
    <div class="search-bar" style="display: inline-flex">
      <el-input
        class="search-input"
        v-model="input"
        placeholder="검색"
        @keyup.enter="getList()"
      >
        <template #append>
          <el-button :icon="Search" @click="getList()" />
        </template>
      </el-input>
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
import { reactive, ref, defineComponent, onBeforeUpdate } from "vue";
import { Search } from "@element-plus/icons-vue";

export default defineComponent({
  setup(_, context) {
    let input = ref("");

    const isFavorite = ref(false);
    const clickHeart = () => {
      isFavorite.value = !isFavorite.value;
    };

    const searchWord = ref("");
    let productList: Product[] = reactive([]);

    const getList = () => {
      //검색 ->  목록 가져오기
      if (input.value === searchWord.value) {
        searchWord.value = input.value;
        return;
      }

      productList.splice(0);
      productList.push({
        productType: "월세",
        productName: "싸피빌라",
        price: "30",
        managePrice: 50000,
        floor: "2층",
        roomSize: 30,
        address: "부산 동래구 충렬대로 255",
        roomDirection: "남향",
        animal: true,
        seller: "싸피",
        explanationDate: null,
        parking: 0,
        options: ["주방분리", "풀 옵션"],
        isWish: false,
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
        address: "경북 안동시 경동로 1375 안동대학교",
        roomDirection: "남향",
        animal: true,
        seller: "싸피",
        explanationDate: null,
        parking: 0,
        options: ["베란다", "주방분리", "에어컨", "냉장고"],
        isWish: true,
      });
      emit("productList", productList);
      searchWord.value = input.value;
    };
    const { emit } = context;
    const whereIs = index => {
      emit("address", productList[index].address);
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

    return {
      input,
      isFavorite,
      clickHeart,
      productList,
      getList,
      whereIs,
      Search,
      searchWord,
      onBeforeUpdate,
    };
  },
});
</script>

<style scoped>
.search-bar {
  width: 100%;
  height: 80px;
  border-bottom: 2px solid #73767a;
}

.search-input {
  width: 450px;
  padding: 20px;
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
