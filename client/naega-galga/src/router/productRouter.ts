export default {
  path: "/product",
  component: import("@/views/ProductView.vue"),
  children: [
    {
      path: ":id",
      component: import("@/components/product/detail/ProductDetail.vue"),
    },
  ],
};
