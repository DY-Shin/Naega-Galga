import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import userRouter from "@/router/userRouter";
import productRouter from "@/router/productRouter";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: import("@/views/HomeView.vue"),
  },
  userRouter[0],
  userRouter[1],
  productRouter,
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
