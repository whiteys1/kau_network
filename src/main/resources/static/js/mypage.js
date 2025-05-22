window.onload = async function () {
  const token = localStorage.getItem("jwt");
  if (!token) {
    window.location.href = "login.html";
    return;
  }

  const response = await fetch("http://52.79.104.211:8080/auth/me", {
    headers: {
      accept: "*/*",
      Authorization: "Bearer " + token,
    },
  });

  if (response.ok) {
    const user = await response.json();
    document.getElementById("userInfo").innerHTML = `
      <ul>
        <li><b>이름:</b> ${user.name}</li>
        <li><b>이메일:</b> ${user.email}</li>
        <li><b>전화번호:</b> ${user.phoneNumber}</li>
        <li><b>주소:</b> ${user.address}</li>
      </ul>
    `;
  } else {
    localStorage.removeItem("jwt");
    window.location.href = "login.html";
  }
};

document.getElementById("logoutBtn").onclick = function () {
  localStorage.removeItem("jwt");
  window.location.href = "login.html";
};
