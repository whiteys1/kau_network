// js/mypage.js
window.onload = async function () {
  const token = localStorage.getItem("jwt");
  if (!token) {
    window.location.href = "login.html";
    return;
  }

  const response = await fetch("http://52.79.104.211:8080/api/user/me", {
    headers: { Authorization: "Bearer " + token },
  });

  if (response.ok) {
    const user = await response.json();
    document.getElementById("userInfo").textContent = JSON.stringify(
      user,
      null,
      2
    );
  } else {
    localStorage.removeItem("jwt");
    window.location.href = "login.html";
  }
};

document.getElementById("logoutBtn").onclick = function () {
  localStorage.removeItem("jwt");
  window.location.href = "login.html";
};
