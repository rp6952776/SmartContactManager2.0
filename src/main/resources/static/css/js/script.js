console.log("Script loaded");

// Get the current theme from local storage or default to "light"
let currentTheme = getTheme();

// Wait for the DOM to be fully loaded before attaching event listeners
document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

// Function to handle theme change logic
function changeTheme() {
  // Apply the current theme to the webpage
  changePageTheme(currentTheme, "");

  // Get the theme change button element
  const changeThemeButton = document.querySelector("#theme_change_button");

  // If the button exists, attach the click event listener
  if (changeThemeButton) {
    changeThemeButton.addEventListener("click", (event) => {
      let oldTheme = currentTheme;
      console.log("Change theme button clicked");

      // Toggle between dark and light themes
      if (currentTheme === "dark") {
        currentTheme = "light";
      } else {
        currentTheme = "dark";
      }

      console.log(currentTheme);

      // Apply the new theme
      changePageTheme(currentTheme, oldTheme);
    });
  }
}

// Save the theme in local storage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// Retrieve the theme from local storage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}

// Change the theme on the current page
function changePageTheme(theme, oldTheme) {
  // Update the theme in local storage
  setTheme(currentTheme);

  // Remove the old theme class from the <html> element, if any
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }

  // Add the new theme class to the <html> element
  document.querySelector("html").classList.add(theme);

  // Change the text of the theme change button, if it exists
  const changeThemeButton = document.querySelector("#theme_change_button");

  if (changeThemeButton) {
    const buttonSpan = changeThemeButton.querySelector("span");

    // Check if the span element exists inside the button
    if (buttonSpan) {
      buttonSpan.textContent = theme === "light" ? "Dark" : "Light";
    }
  }
}



console.log("script running")