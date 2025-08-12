const config = {
  printWidth: 80,
  tabWidth: 2,
  semi: true,
  singleQuote: true,
  jsxSingleQuote: true,
  trailingComma: 'all',
  arrowParens: 'always',
  bracketSpacing: true,
  useTabs: false,
  htmlWhitespaceSensitivity: 'ignore',

  importOrder: [],
  importOrderSeparation: true,
  importOrderSortSpecifiers: true,
  importOrderSideEffects: false,

  plugins: ['@trivago/prettier-plugin-sort-imports'],
};

export default config;
