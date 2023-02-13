export default {
  path: "/product",
  name: "product",

  component: () => import("@/views/ProductView.vue"),
  children: [
    {
      path: ":id",
      component: () => import("@/components/product/detail/ProductDetail.vue"),
    },
    {
      path: "add",
      component: () => import("@/components/product/input/ProductInput.vue"),
    },
  ],
};
