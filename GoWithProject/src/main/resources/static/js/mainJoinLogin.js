/* 강찬혁의 작업용 js */
// 메인페이지, 로그인 페이지, 회원가입 페이지

/* ↓ 회원가입 페이지에서 사용합니다. ↓ */

const join = document.querySelector("#join");
const joinBtn = document.querySelector("#joinBtn");
if (join != null) {

  const inputEmail = document.querySelector("#inputEmail");
  const inputId = document.querySelector("#inputId");
  const inputPw = document.querySelector("#inputPw");

  joinBtn.addEventListener("click", () => {

    /* 유효성검사 */

    // 이메일 검사(naver, hanmail, gmail만 가능)
    const EmailregExp = /^(?:[a-zA-Z0-9._%+-]+@(?:naver\.com|gmail\.com|hanmail\.net))$/;

    if (inputEmail.value.trim().length == 0) {
      alert("이메일을 입력하세요");
      inputEmail.value = "";
      inputEmail.focus();
      return;
    }
    if (!EmailregExp.test(inputEmail.value)) {
      alert("naver, gmail, hanmail 형식만 가입 가능합니다");
      inputEmail.value = "";
      inputEmail.focus();
      return;
    }


    // 아이디 검사 
    if (inputId.value.trim().length == 0) {
      alert("아이디를 입력하세요");
      inputId.innerText = "";
      inputId.focus();
      return;
    }
    if (inputId.value.trim().length >= 9) {
      alert("아이디는 8자를 초과할 수 없습니다.");
      inputId.innerText = "";
      inputId.focus();
      return;
    }

    // 비밀번호 검사
    if (inputPw.value.trim().length == 0) {
      alert("비밀번호를 입력하세요");
      inputPw.innerText = "";
      inputPw.focus();
      return;
    }
    if (inputPw.value.trim().length >= 12) {
      alert("비밀번호는 11자를 초과할 수 없습니다");
      inputPw.innerText = "";
      inputPw.focus();
      return;
    }

    // 전부 통과한 경우 메일 발송 + 카운트다운 시작
    function countDown() {
      countDownInterval = setInterval(updateCountdown, 10); // 1초마다 ㄱㄱ
    }
    function updateCountdown() {
      countdownValue -= 1;
      document.querySelector("#count").innerText = countdownValue;
      if (countdownValue == 0) {
        alert("이 거북이 녀석");
        clearInterval(countDownInterval);
      }
    }


  });



}

/* ↑ 회원가입페이지에서 사용합니다 ↑ */





/* ↓ 메인페이지에서 사용합니다 ↓ */

// 빠른 로그인
const quickLoginBtn = document.querySelectorAll(".quickLogin");
if (quickLoginBtn != null) {

  quickLoginBtn.forEach((item, index) => {

    item.addEventListener("click", e => {

      const email = item.innerText;

      // get방식 요청
      location.href = "/member/quickLogin?memberEmail=" + email;

    })

  });
}

/* 일반 로그인 */
// 쿠키 얻어오기
const getCookie = (key) => {
  const cookies = document.cookie;

  const cookieList = cookies.split("; ")
    .map(el => { return el.split("=") });

  const obj = {};
  for (let i = 0; i < cookieList.length; i++) {
    const k = cookieList[i][0];
    const v = cookieList[i][1];
    obj[k] = v;
  }
  console.log(cookies);

  return obj[key]
}

// 아이디 저장버튼
const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");
if (loginEmail != null) {
  const saveId = getCookie("saveId");
  if (saveId != undefined) {
    loginEmail.value = saveId;
    document.querySelector("input[name='saveId']").checked = true;

  }
}


/* 이멜, 비번중 하나라도 미작성시 로그인 시도 못하게 */



const loginForm = document.querySelector("#loginForm");
const inputPw = document.querySelector("#loginForm input[name='memberPw']");

// 제출이될때(버튼이 눌렸을 때)
const loginBtn = document.querySelector("#loginBtn");
if (loginBtn != null) {
  loginBtn.addEventListener("click", e => {
    const inputPwCheck = inputPw.value.trim();
    const inputEmailCheck = loginEmail.value.trim();

    if (inputPwCheck.length === 0 && inputEmailCheck.length === 0) {
      e.preventDefault();
      alert("로그인을 위해 회원 정보를 입력해 주세요");
      loginEmail.focus();
      return;
    }
    if (inputEmailCheck.length === 0 && inputPwCheck.length != 0) {
      e.preventDefault();
      alert("회원 이메일을 입력해 주세요");
      loginEmail.focus();
      return;
    }
    if (inputPwCheck.length === 0 && inputEmailCheck.length != 0) {
      e.preventDefault();
      alert("회원 비밀번호를 입력해 주세요");
      inputPw.focus();
      return;
    }

  });
}


// 테스트용
// 홈버튼 클릭시 alert
const mainLogo = document.querySelector("#mainLogo");
mainLogo.addEventListener("click", () => {
  alert("잘 되는지 확인");
});









/* ↑ 메인페이지에서 사용합니다 ↑ */