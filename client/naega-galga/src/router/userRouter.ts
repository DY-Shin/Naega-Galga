export default {
  path: "/LoginHome",
  component: import("@/views/UserView.vue"),
  children: [
    {
      path: "",
      component: import("@/components/home/login/LoginHome.vue"),
    },
  ],
};
