<template>
  <el-aside>
    <!-- search bar start -->
    <div class="search-bar" style="text-align: center">
      <el-input
        class="search-input"
        v-model="input"
        placeholder="검색"
        size="large"
        @keyup.enter="getList()"
        style="width: 100%"
      >
        <template #append>
          <el-button :icon="Search" @click="getList()" />
        </template>
      </el-input>
    </div>
    <el-scrollbar :style="{ height: 'calc(100vh - 225px)' }">
      <div
        v-for="(item, index) in productList"
        :key="item.index"
        class="scrollbar-demo-item"
      >
        <div class="right-box" @click="whereIs(index)">
          <div class="img-content">
            <div class="list-img"></div>
            <!-- <div class="list-online-icon" v-if="item.explanationDate != null">
              <div class="text">온라인 설명회</div>
            </div> -->
          </div>
          <div class="home-info-box">
            <div class="home-addr">{{ item.addr }}</div>
            <!-- <div class="home-rooms">{{ item.rooms }} {{ item.size }}평</div> -->
            <div class="home-price">{{ item.price }}</div>
          </div>
        </div>

        <button v-if="productList[index].bed" id="heart-btn">
          <img
            src="@/assets/image/icon-heart-filled.png"
            width="20"
            height="20"
            @click="clickHeart(index)"
          /></button
        ><button v-else id="heart-btn">
          <img
            src="@/assets/image/icon-heart.png"
            width="20"
            height="20"
            @click="clickHeart(index)"
          />
        </button>
      </div>
    </el-scrollbar>
  </el-aside>
</template>

<script lang="ts">
import { reactive, ref, defineComponent, onBeforeUpdate } from "vue";
import { Search } from "@element-plus/icons-vue";
import { SearchProduct } from "@/api/productApi";

export default defineComponent({
  setup(_, context) {
    interface Product {
      index: number;
      addr: string;
      roadAddr: string;
      price: string;
      photo: string;
      airConditioner: boolean;
      fridge: boolean;
      washingMachine: boolean;
      gasStove: boolean;
      induction: boolean;
      microWave: boolean;
      desk: boolean;
      wifi: boolean;
      closet: boolean;
      bed: boolean;
    }
    let input = ref("");

    const isFavorite = ref(false);
    const clickHeart = index => {
      wishList[index] = !wishList[index];
    };

    // const beforeInput = ref("");
    const { emit } = context;
    const whereIs = index => {
      emit("addr_idx", index);
    };
    let productList = reactive<Array<Product>>([]);
    let wishList: boolean[] = reactive([]);
    wishList.push(true);
    wishList.push(false);
    wishList.push(true);

    const getList = async () => {
      console.log(input.value);
      const list = await SearchProduct(input.value);
      list.data.forEach((product: Product) => productList.push(product));
      console.log(productList);
      console.log(productList + "!!");
      for (let i = 0; i < productList.length; i++) {
        console.log(productList[i].addr);
      }
      emit("productList", productList);
      //검색 ->  목록 가져오기
      // if (input.value === beforeInput.value) {
      //   beforeInput.value = input.value;
      //   return;
      // }

      // beforeInput.value = input.value;
    };

    return {
      input,
      isFavorite,
      clickHeart,
      productList,
      getList,
      whereIs,
      Search,
      onBeforeUpdate,
      wishList,
    };
  },
});
</script>

<style scoped>
.search-bar {
  width: 100%;
  height: 80px;
  border-bottom: 1px solid #bdbdbd;
}

.search-input {
  width: 450px;
  padding: 20px;
}

#heart-btn {
  position: relative;
  border: none;
  background: none;
  bottom: 60px;
  /* padding-bottom: 200px; */
  padding-left: 400px;
}
.home-info-box {
  height: 100px;
  padding: 40px 0;
  /* width: 200px; */
}
.home-addr {
  font-weight: 500;
  color: black;
  text-align: left;
  /* margin: 20px 0; */
}
.home-price {
  color: black;
  text-align: left;
  /* margin: 20px 0; */
}
.home-rooms {
  color: black;
  font-size: 20px;
  text-align: left;
  margin: 15px 0;
}
.home-size {
  color: black;
  float: right;
  /* font-size: 20px; */
  text-align: left;
  /* margin: 15px 0; */
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

.right-box {
  /* float: left; */
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
  border-top: 1px solid #bdbdbd;
  /* display: flex; */
  align-items: center;
  height: 200px;
  text-align: center;
  color: var(--el-color-primary);
}

.el-aside {
  width: 500px;
  height: 100%;
  border-top: 1px solid #bdbdbd;
  /* border-right: 1px solid #bdbdbd; */
  /* border-left: 1px solid #73767a; */
  /* border: 1px solid #73767a;
  border-radius: 7px 0 0 7px; */
}
</style>
