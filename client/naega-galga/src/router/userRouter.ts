export default [
  {
    path: "/user",
    component: () => import("@/views/UserView.vue"),
    children: [
      {
        path: "",
        component: () =>
          import("@/components/user/mypage/userInfo/UserInfo.vue"),
      },
      {
        path: "reservation",
        name: "ReservationList",
        component: () =>
          import("@/components/user/mypage/reservation/ReservationList.vue"),
      },
      {
        path: "register",
        name: "RegisterList",
        component: () =>
          import("@/components/user/mypage/register/RegisterList.vue"),
      },
    ],
  },
  {
    path: "/join",
    component: () => import("@/views/JoinView.vue"),
  },
  {
    path: "/login",
    component: () => import("@/views/LoginView.vue"),
  },
  {
    path: "/delete",
    component: () => import("@/views/UserDeleteView.vue"),
  },
];
