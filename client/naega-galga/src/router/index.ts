import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import userRouter from "@/router/userRouter";
import productRouter from "@/router/productRouter";

const joinRouter = userRouter[1];

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: import("@/views/HomeView.vue"),
  },
  userRouter[0],
  joinRouter,
  productRouter,
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
