const favoriteBtn = document.querySelectorAll(".favoriteBtn");
const bottomName = document.querySelector("#bottomName");


for(let i = 0; i < favoriteBtn.length; i++){

    favoriteBtn[i].addEventListener("click", e=>{

        const bottomMenuName = bottomName.innerText; 
        
        const param = {
            "bottomMenuCode" : bottomMenuCode, 
            "loginMemberNo" : loginMemberNo
        }
        
        if(favoriteCheck == 1){
            fetch("/board/favorite",{
                method : "DELETE",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(param)
            })
            .then(resp => resp.text())
            .then(result =>{
                if(result == 1) favoriteBtn[i].innerText="N";
                favoriteCheck = 0;
            });
            return;
        }
        else{
            fetch("/board/favorite",{
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify(param)
            })
            .then(resp=> resp.text())
            .then(result=>{
                if(result == 1) favoriteBtn[i].innerText="Y";
                favoriteCheck = 1;
            });
        }

    })

}

const numberUnit = document.querySelector("#numberUnit");

numberUnit.addEventListener("change", e=>{

    const selectNumber = numberUnit.options[numberUnit.selectedIndex].value;

    const params = new URL(location.href).searchParams;
    
    const url = window.location.href;;

    location.href = new URL(location.href).pathname+"?limit="+selectNumber;



})


const insertBtn = document.querySelector("#insertBtn");

if(insertBtn != null){
    
    const params = new URL(location.href).searchParams;
    
    const topMenuCode = params.get("topMenuCode");
    const bottomMenuCode = params.get("bottomMenuCode");

    insertBtn.addEventListener("click", e=>{
    
        location.href = `/editBoard/${topMenuCode}/${bottomMenuCode}/insert`;
    
    })

}


const topMenuKey = document.querySelector("#topMenuKey");

topMenuKey.addEventListener("change", e=>{

    const selectTopMenuCode = topMenuKey.options[topMenuKey.selectedIndex].value;

    const bottomMenuKey = document.querySelector("#bottomMenuKey");

    bottomMenuKey.innerHTML = "";

    fetch("/board/selectBottom?topMenuCode="+selectTopMenuCode)
    .then(resp => resp.json())
    .then(bottomList => {

        for(let bottom of bottomList){

            const option = document.createElement("option");

            option.value = bottom['bottomMenuCode'];
            option.innerText = bottom['bottomMenuName'];

            console.log(option);

            bottomMenuKey.append(option);

        }

    })
})







