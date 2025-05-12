/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{js,jsx,ts,tsx}"],
    theme: {
      extend: {
        colors: {
          navy: "#1E2A44",
          gold: "#FFD700",
          darkGray: "#2D3748",
          lightGray: "#A0AEC0",
        },
        fontFamily: {
          inter: ["Inter", "sans-serif"],
        },
      },
    },
    plugins: [],
  };