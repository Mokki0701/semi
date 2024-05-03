const findNickname = document.querySelector("#findNickname");
const findTel = document.querySelector("#findTel");
const findBtn = document.querySelector("#findBtn").addEventListener("click", e=>{

    const arr = {
        "findNickname" : findNickname.value,
        "findTel" : findTel.value
    }

    fetch("/member/findId", {

        method : "POST",
        headers : {"Content-Type":"application/json"},
        body : JSON.stringify(arr)

    })
    .then(resp => resp.text())
    .then(result=>{

        alert(result);

        window.close();
    })


});










