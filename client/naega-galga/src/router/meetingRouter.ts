export default {
  path: "/meeting",
  component: () => import("@/views/MeetingView.vue"),
  children: [
    {
      path: "one-on-one/:id",
      component: () =>
        import("@/components/meeting/one-on-one/OneOnOneMeeting.vue"),
    },
    {
      path: "one-on-many/:id",
      component: () =>
        import("@/components/meeting/one-on-many/OneOnManyMeeting.vue"),
    },
  ],
};
