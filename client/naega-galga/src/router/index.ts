import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import userRouter from "@/router/userRouter";
import productRouter from "@/router/productRouter";
import meetingRouter from "@/router/meetingRouter";

const usersRouter = userRouter[0];
const joinRouter = userRouter[1];
const loginRouter = userRouter[2];
const userDeleteRouter = userRouter[3];

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: () => import("@/views/HomeView.vue"),
  },
  usersRouter,
  joinRouter,
  loginRouter,
  userDeleteRouter,
  productRouter,
  meetingRouter,
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
