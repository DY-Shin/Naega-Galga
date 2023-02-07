const { defineConfig } = require("@vue/cli-service");
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin

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
  configureWebpack: {
    plugins: [new BundleAnalyzerPlugin()]
  },
});
