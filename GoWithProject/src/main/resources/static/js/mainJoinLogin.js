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

    if (inputEmail.value.trim().length == 0 ) {
      alert("이메일을 입력하세요");
      inputEmail.value = ""; 
      inputEmail.focus(); 
      return;
    }
    if( !EmailregExp.test(inputEmail.value) ){
      alert("naver, gmail, hanmail 형식만 가입 가능합니다");
      inputEmail.value = ""; 
      inputEmail.focus(); 
      return;
    }


    // 아이디 검사 
    if (inputId.value.trim().length == 0 ) {
      alert("아이디를 입력하세요");
      inputId.innerText = "";
      inputId.focus();
      return;
    }
    if(inputId.value.trim().length >= 9){
      alert("아이디는 8자를 초과할 수 없습니다.");
      inputId.innerText = "";
      inputId.focus();
      return;
    }

    // 비밀번호 검사
    if (inputPw.value.trim().length == 0 ) {
      alert("비밀번호를 입력하세요");
      inputPw.innerText = "";
      inputPw.focus();
      return;
    }
    if( inputPw.value.trim().length >= 12){
      alert("비밀번호는 11자를 초과할 수 없습니다");
      inputPw.innerText = "";
      inputPw.focus();
      return;
    }

    // 전부 통과한 경우 메일 발송 + 카운트다운 시작
    function countDown() {
      countDownInterval = setInterval(updateCountdown, 10); // 1초마다 ㄱㄱ
    }
    function updateCountdown(){
      countdownValue -= 1 ;
      document.querySelector("#count").innerText = countdownValue; 
      if(countdownValue == 0 ){
        alert("이 거북이 자식");
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












/* ↑ 메인페이지에서 사용합니다 ↑ */