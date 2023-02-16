module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    "plugin:vue/vue3-essential",
    "eslint:recommended",
    "@vue/typescript/recommended",
    "plugin:prettier/recommended",
  ],
  parserOptions: {
    ecmaVersion: 2020,
  },
  rules: {
    "for-direction": "warn",
    "no-unused-vars": "error",
    "arrow-body-style": ["error", "as-needed"],
    curly: "error",
    "default-case-last": "error",
    "func-name-matching": ["error", "always"],
    "no-continue": "error",
    "no-else-return": "error",
    "no-var": "error",
    "require-await": "warn",
    "prettier/prettier": [
      "error",
      {
        endOfLine: "auto",
      },
    ],
  },
};
