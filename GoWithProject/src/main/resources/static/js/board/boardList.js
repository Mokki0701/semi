const favoriteBtn = document.querySelectorAll(".favoriteBtn");
// const bottomName = document.querySelector("#bottomName");


for(let i = 0; i < favoriteBtn.length; i++){

    favoriteBtn.addEventListener("click", e=>{
    
        // const bottomMenuName = bottomName.innerText; 
    
        const param = {
            // "bottomMenuName" : bottomMenuName,  이게 아니라 bottomMenuCode 가 와야한다.
            "loginMemberNo" : loginMemberNo,
            "myFavorite" : myFavorite
        }
    
        fetch("/board/favorite",{
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(param)
        })
        .then(resp => resp.text())
        .then(result => {
            console.log(result);

            if(result == 1){

                if(myFavorite == 'Y'){
                    /* 색을 뺀다 */
                    return;
                }

                favoriteBtn[i].append() /* 색을 추가 한다. */
                return;
            }
        })
    
    });

}

const numberUnit = document.querySelector("#numberUnit");

numberUnit.addEventListener("change", e=>{

    const selectNumber = numberUnit.options[numberUnit.selectedIndex].innerText;

    const params = new URL(location.href).searchParams;
    
    const topMenuCode = params.get("topMenuCode");
    const bottomMenuCode = params.get("bottomMenuCode");
    
    location.href = `/` + topMenuCode + `/` + bottomMenuCode + `?limit=` + selectNumber;

})














