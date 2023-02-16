<template>
  <el-aside>
    <div class="search-bar" style="text-align: center">
      <el-input
        class="search-input"
        v-model="input"
        placeholder="검색"
        size="large"
        @keyup.enter="getList"
        style="width: 100%"
      >
        <template #append>
          <el-button :icon="Search" @click="getList" />
        </template>
      </el-input>
    </div>
    <el-scrollbar :style="{ height: 'calc(100vh - 225px)' }">
      <div
        v-for="(item, index) in productList"
        :key="item.productIndex"
        class="scrollbar-demo-item"
      >
        <div @click="whereIs(index)">
          <div class="img-content">
            <img class="list-img" :src="photoList[index]" />
            <div class="list-online-icon" v-if="item.presentation">
              <div class="text">온라인 설명회</div>
            </div>
          </div>
          <div class="home-info-box">
            <div class="home-addr">{{ item.roadAddr }}</div>
            <div class="home-rooms">{{ item.rooms }} {{ item.size }}m²</div>
            <div class="home-price">{{ item.type }} {{ item.price }}</div>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </el-aside>
</template>

<script lang="ts">
import { reactive, ref, defineComponent, onBeforeUpdate } from "vue";
import { Search } from "@element-plus/icons-vue";
import { searchProduct } from "@/api/productApi";

export default defineComponent({
  setup(_, context) {
    const photoList: string[] = [];

    interface Product {
      productIndex: number;
      sellerIndex: number;
      sellerName: string;
      roadAddr: string;
      photo: string;
      price: string;
      type: string;
      size: string;
      rooms: string;
      presentation: boolean;
    }
    let input = ref("");

    const { emit } = context;
    const whereIs = index => {
      emit("addr_idx", index);
    };
    let productList = reactive<Array<Product>>([]);

    const getList = async e => {
      if (e.isComposing || e.keyCode === 229) {
        return;
      }

      const list = await searchProduct(input.value);
      productList.splice(0);
      photoList.splice(0);
      list.data.forEach((product: Product) => productList.push(product));
      for (let i = 0; i < productList.length; i++) {
        let path = productList[i].photo;
        photoList.push(
          `${process.env.VUE_APP_API_BASE_URL}api/image/display?path=${path}`
        );
      }
      console.log(list.data);
      emit("productList", productList);
    };

    return {
      input,
      productList,
      getList,
      whereIs,
      Search,
      onBeforeUpdate,
      photoList,
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
.home-info-box {
  width: 200px;
  float: left;
  margin-top: 45px;
}
.home-addr {
  font-weight: 500;
  color: black;
  text-align: left;
}
.home-price {
  color: black;
  text-align: left;
}
.home-rooms {
  color: black;
  font-size: 20px;
  text-align: left;
  margin: 25px 0;
}
.home-size {
  color: black;
  float: right;
  text-align: left;
}
.text {
  padding: 5px;
}
.img-content {
  margin-left: 10px;
  float: left;
  height: 100%;
  width: 50%;
  align-items: center;
}

.list-img {
  position: relative;
  float: left;
  left: 20px;
  top: 35px;

  width: 200px;
  height: 130px;
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
  align-items: center;
  height: 200px;
  text-align: center;
  color: var(--el-color-primary);
}
.el-aside {
  width: 500px;
  height: 100%;
  border-top: 1px solid #bdbdbd;
}
</style>
