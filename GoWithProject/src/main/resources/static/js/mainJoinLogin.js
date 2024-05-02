/* 강찬혁의 작업용 js */
// 메인페이지, 로그인 페이지, 회원가입 페이지







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




/* ++ 메뉴 체크박스 */

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

        // 'topMenuCode', 'bottomMenuCode', 'boardNo'

        for (let key of arr) {
          const td = document.createElement("td");
          if (key === 'boardTitle') {
            const a = document.createElement("a");
            a.href = "board/" + obj['topMenuCode'] + "/" + obj['bottomMenuCode'] + "/" + obj['boardNo'];
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

// 각 버튼 클릭 시 실행되는 코드
const popWriteBtnContext = document.querySelectorAll(".popWriteBtnContext");
popWriteBtnContext.forEach(btn => {
  btn.addEventListener("click", function (e) {
    const value = e.target.value;
    fetchBoardList(value);
  });
});

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



// 리스트
document.addEventListener("DOMContentLoaded", function () {
  const commentListItems = document.querySelectorAll("#commentList li");
  const groupA = Array.from(commentListItems).slice(0, 5);
  const groupB = Array.from(commentListItems).slice(5, 10);
  const btn = document.getElementById("pirate");
  let btnText = btn.innerText;

  // 페이지 로딩 시 B 그룹 숨기기
  groupB.forEach(item => {
    item.style.display = 'none';
  });

  btn.addEventListener("click", () => {
    if (btnText == '다음') {
      // 다음 버튼을 누를 때마다 A 그룹 숨기고 B 그룹 보이기
      groupA.forEach(i => {
        i.style.display = 'none';
      });
      groupB.forEach(i => {
        i.style.display = 'block';
      });

      // 버튼 텍스트 변경
      btn.innerText = '이전';
      btnText = '이전';
    } else if (btnText == '이전') {
      // 이전 버튼을 누를 때마다 B 그룹 숨기고 A 그룹 보이기
      groupA.forEach(i => {
        i.style.display = 'block';
      });
      groupB.forEach(i => {
        i.style.display = 'none';
      });

      btn.innerText = '다음';
      btnText = '다음';
    }
  });
});

// ----------------------------------------------------------------------- //

// 멤버랭킹조회 공통 기능
// 멤버랭킹조회 공통 기능
function memberRank(value) {
  fetch("/memberRank?rank=" + value, {
    method: "GET",
    headers: { "Content-Type": "application/json" }
  })
    .then(response => response.json())
    .then(result => {
      const mrList = document.querySelector("#mrList");
      mrList.innerHTML = "";

      for (let obj of result) {
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.innerText = obj.memberNickname; 
        tr.appendChild(td); 
        mrList.appendChild(tr); 
      }
    })
    .catch(error => console.error('Error:', error)); 
}

// index 페이지 로드 시 실행되는 코드
document.addEventListener("DOMContentLoaded", function () {
  const value = "memDefault"; // 기본값 설정
  memberRank(value);
});

const rankBtns = document.querySelectorAll(".memRank");

// 각 라디오 버튼에 대해 클릭 이벤트 핸들러 추가
rankBtns.forEach(btn => {
  btn.addEventListener("click", function (e) {
    const value = e.target.value; // 클릭된 라디오 버튼의 value 값 가져오기
    memberRank(value); // 가져온 value 값을 memberRank 함수에 전달하여 실행
  });
});



// 색깔변경
// 인기글 클릭시 버튼 색 변경
const memLabels = document.querySelectorAll(".rankBtn label");

memLabels.forEach(label => {
  label.addEventListener("click", () => {
    // label이 클릭되면 다른 label들의 checkedBtn 클래스 제거
    memLabels.forEach(otherLabel => {
      otherLabel.classList.remove('textRed');
      otherLabel.classList.add('textRed')
    });
    label.classList.add('textRed');
  });
});

// 최대 글자 수 8자로 제한
const maxTextLength8Elements = document.querySelectorAll(".maxTextLength8");

maxTextLength8Elements.forEach(element => {
    const text = element.innerText.trim(); // 텍스트 가져오기
    if (text.length > 8) {
        const truncatedText = text.substring(0, 8) + "..."; // 글자 수 제한
        element.innerText = truncatedText; // 제한된 텍스트로 설정
    }
});








/* 메인 페이지에서 보여질 때 끝 */







// 비동기로 목록 조회


/* -- 메뉴 체크박스 테스트중.. */







/* ↑ 메인페이지에서 사용합니다 ↑ */
