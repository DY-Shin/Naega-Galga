export default [
  {
    path: "/user",
    component: import("@/views/UserView.vue"),
    children: [
      {
        path: "",
        component: import("@/components/user/mypage/UserInfo.vue"),
      },
      {
        path: "reservation",
        name: "ReservationList",
        component: import(
          "@/components/user/mypage/reservation/ReservationList.vue"
        ),
      },
      {
        path: "wish",
        name: "WishList",
        component: import("@/components/user/mypage/wish/WishList.vue"),
      },
      {
        path: "register",
        name: "RegisterList",
        component: import("@/components/user/mypage/register/RegisterList.vue"),
      },
    ],
  },
  {
    path: "/join",
    component: import("@/views/JoinView.vue"),
  },
  {
    path: "/login",
    component: import("@/views/LoginView.vue"),
  },
];
