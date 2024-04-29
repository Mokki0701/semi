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

/* ++ 메뉴 체크박스 테스트중.. */

const likeBtn = document.getElementById('likeBtn');
const commentBtn = document.getElementById('commentBtn');
const likeLabel = document.getElementById('likeLabel');
const commentLabel = document.getElementById('commentLabel');

likeBtn.addEventListener('change', function () {
  if (likeBtn.checked) {
    likeLabel.classList.add('label-like');
    commentLabel.classList.remove('label-comment');
  }
});

commentBtn.addEventListener('change', function () {
  if (commentBtn.checked) {
    commentLabel.classList.add('label-comment');
    likeLabel.classList.remove('label-like');
  }
});

// -------------------- 비동기 목록 조회 (인기글, 지난주멤버랭킹)--------------------

// 인기글 공통으로 사용할 fetchBoardList 기능

function fetchBoardList(value) {
  fetch("/popWriteInquiry?popWriteBtn=" + value, {
    method: "GET",
    headers: { "Content-Type": "application/json" }
  })
    .then(response => response.json())
    .then(result => {
      const PbList = document.querySelector("#PbList");
      PbList.innerHTML = "";

      for (let obj of result) {
        const tr = document.createElement("tr");
        const arr = ['boardTitle', 'memberNickname', 'boardWriteDate', 'readCount'];

        for (let key of arr) {
          const td = document.createElement("td");
          if (key === 'boardTitle') {
            const a = document.createElement("a");
            a.href = "board/boardDetail?id=" + obj['boardId'];
            a.innerText = obj[key];
            td.appendChild(a);
          } else {
            td.innerText = obj[key];
          }
          if (key === 'boardTitle') {
            td.classList.add("title");
          }
          if (key === 'boardWriteDate') {
            td.classList.add("writeDate");
          }
          tr.append(td);
          td.classList.add("textCenter");
        }
        PbList.append(tr);
      }
    })
    .catch(error => console.error('Error:', error));
}


// index 페이지 로드 시 실행되는 코드
document.addEventListener("DOMContentLoaded", function () {
  const value = "popDefault"; // 기본값 설정
  fetchBoardList(value);
});

const popWriteBtnContext = document.querySelectorAll(".popWriteBtnContext");
popWriteBtnContext.forEach(btn => {
  btn.addEventListener("click", function (e) {
    const value = e.target.value;
    fetchBoardList(value);
  });
});

// 멤버랭킹 공통 기능
function memberRank(value) {
  fetch("memberRank?rank=" + value,{
    method="GET",
  headers : {"Content-Type": "application/json" }
}).then (response => response.json())
.then(result => {
  const rankList = document.querySelector("rankList");
  rankList.innerHTML="";

  for(let obj of result){
    const tr = document.createElement("tr");
    const arr = ['memberNickname'] ;

    for(let key of arr){
      const td = document.createElement("td");
    }
  }
})
}




// 인기글 클릭시 버튼 색 변경
const popLabels = document.querySelectorAll(".popWriteBtnBox label");

popLabels.forEach(label => {
  label.addEventListener("click", () => {
    // label이 클릭되면 다른 label들의 checkedBtn 클래스 제거
    popLabels.forEach(otherLabel => {
      otherLabel.classList.remove('checkedBtn');
      otherLabel.classList.add('popWriteBtn')
    });
    // 선택된 label의 클래스에 checkedBtn 클래스 추가
    label.classList.add('checkedBtn');
  });
});

// rank 클릭시 버튼 색 변경
const rankBtn = document.querySelectorAll(".rankBtn");

rankBtn.forEach(label => {
  label.addEventListener("click", () => {
    // label이 클릭되면 다른 label들의 checkedBtn 클래스 제거
    rankBtn.forEach(otherLabel => {
      otherLabel.classList.remove('textRed');
    });
    // 선택된 label의 클래스에 checkedBtn 클래스 추가
    label.classList.add('textRed');
  });
});



// 메인페이지에 보여질때







// 비동기로 목록 조회


/* -- 메뉴 체크박스 테스트중.. */







/* ↑ 메인페이지에서 사용합니다 ↑ */
