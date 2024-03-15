/** @type {import('tailwindcss').Config} */
module.exports = {
  
  content: ["./src/**/*.{html,js,jsx}", "./node_modules/react-tailwindcss-select/dist/index.esm.js"],
  theme: {
    extend: {
    }
},
  plugins: [require("tailwindcss"), require("autoprefixer")],
};
