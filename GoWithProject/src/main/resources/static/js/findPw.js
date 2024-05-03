const findEmail = document.querySelector("#findEmail");
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
const authKey = document.querySelector("#authKey");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

let checkAuthKey = false;


sendAuthKeyBtn.addEventListener("click", ()=>{

    checkAuthKey = false;

    fetch("/member/findPw",{
        method : "POST",
        headers : {"Content-Type":"application/json"},
        body : findEmail.value
    })
    .then(resp=>{
        return resp.text();
    })
    .then(result =>{

        if(result == 1){
            alert("발송 성공!!!");
        }
        else{
            alert("발송 실패...");
        }
    })


})

// ------------------------------------------------------------------------------------------------------

checkAuthKeyBtn.addEventListener("click", e=>{

    checkAuthKey = false;

    // 이거 fetch 비동기로 보낼때 param값으로 authKey말고도 이메일도 보내야 되겠네
    const param = {
        "authKey" : authKey.value,
        "memberEmail" : findEmail.value
    }

    fetch("/member/matchAuthKey",{
        method : "POST",
        headers : {"Content-Type":"application/json"},
        body : JSON.stringify(param)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            alert("인증 성공!!!");
            checkAuthKey = true;
            return;
        }
        alert("인증 실패...");
    })


});

// ------------------------------------------------------------------------------------------------------

// 인증 성공하면 checkAuthKey 버튼 true인지 체크하고 true면 팝업 창 닫고, 비밀번호 재설정 칸으로 가기

const searchPw = document.querySelector("#searchPw");

searchPw.addEventListener("click", e=>{

    if(checkAuthKey == true){

        location.href = `member/resetPw?memberEmail` + findEmail.value; // 이거 동기로 Post로 어떻게 보냈지?

        // 기존 팝업창은 끄고 싶은데
    }

    checkAuthKey = false;

})



















