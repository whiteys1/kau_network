// js/login.js
document
  .getElementById("loginForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const email = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://52.79.104.211:8080/auth/login", {
      method: "POST",
      headers: {
        accept: "*/*",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
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
