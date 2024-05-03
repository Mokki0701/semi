const findEmail = document.querySelector("#findEmail");
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
const authKey = document.querySelector("#authKey");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");
const resetPw = document.querySelector("#resetPw");

let checkAuthKey = false;


sendAuthKeyBtn.addEventListener("click", ()=>{

    checkAuthKey = false;

    fetch("/member/authMailSend",{
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
            
            const div = document.createElement("div");

            const input = document.createElement("input");
            const button = document.createElement("button");

            div.appendChild(input);
            div.appendChild(button);

            resetPw.appendChild(div);

            button.addEventListener("click", e=>{

                const param = {
                    "memberEmail" : findEmail.value,
                    "password" : input.value
                }

                fetch("member/resetPw",{

                    method : "POST",
                    headers : {"Content-Type":"application/json"},
                    body : JSON.stringify(param)

                })
                .then(resp=> resp.text())
                .then(result=>{

                    if(result > 0){
                        alert("비밀번호 재설정 성공!!!");
                        window.close();
                    }
                    else{
                        alert("비밀번호 재설정 실패...");
                        window.close();
                    }


                })

            })




            return;
        }
        alert("인증 실패...");
    })


});

// ------------------------------------------------------------------------------------------------------





















