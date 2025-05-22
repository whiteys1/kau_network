document
  .getElementById("signupForm")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const name = document.getElementById("name").value;
    const phoneNumber = document.getElementById("phoneNumber").value;
    const address = document.getElementById("address").value;

    const response = await fetch("http://52.79.104.211:8080/auth/signup", {
      method: "POST",
      headers: {
        accept: "*/*",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password, name, phoneNumber, address }),
    });

    const data = await response.json();

    if (response.ok && data.registered) {
      document.getElementById(
        "signupResult"
      ).innerHTML = `<span style="color:green;">회원가입 성공!<br>환영합니다, ${data.name}님</span>`;
    } else {
      document.getElementById(
        "signupResult"
      ).innerHTML = `<span style="color:red;">회원가입 실패: ${
        data.message || "오류"
      }</span>`;
    }
  });
