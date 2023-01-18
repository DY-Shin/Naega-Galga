import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import userRouter from "@/router/userRouter";
import productRouter from "@/router/productRouter";

const usersRouter = userRouter[0];
const joinRouter = userRouter[1];
const loginRouter = userRouter[2];

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: import("@/views/HomeView.vue"),
  },
  usersRouter,
  joinRouter,
  loginRouter,
  productRouter,
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
