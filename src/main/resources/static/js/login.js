// js/login.js
document
  .getElementById("loginForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
   
    const response = await fetch("http://52.79.104.211:8080//api/auth/signin", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });

    const data = await response.json();

    if (response.ok && data.accessToken) {
      localStorage.setItem("jwt", data.accessToken);
      window.location.href = "mypage.html";
    } else {
      document.getElementById("loginError").textContent =
        data.message || "로그인 실패";
    }
  });
