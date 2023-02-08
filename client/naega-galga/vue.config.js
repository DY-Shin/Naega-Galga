const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
  transpileDependencies: true,
  productionSourceMap: false,
  css: {
    loaderOptions: {
      sass: {
        additionalData: `
                  @import "@/assets/css/reset.scss";
                `,
      },
    },
  },
});
