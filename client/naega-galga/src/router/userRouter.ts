export default [
  {
    path: "/user",
    component: import("@/views/UserView.vue"),
    children: [
      // {
      //   path: "login",
      //   component: import("@/views/UserView.vue"),
      // },
    ],
  },
  {
    path: "/join",
    component: import("@/views/JoinView.vue"),
  },
];
