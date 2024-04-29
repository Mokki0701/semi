/* 선택된 이미지 미리보기 */
// img 태그 4개
const previewList = document.getElementsByClassName("preview");
//input 태그 
const inputImageList = document.getElementsByClassName("inputImg");
//삭제 버튼
const deleteImageList = document.getElementsByClassName("del-img");
//백업 이미지
const backupInputList = new Array(inputImageList.length);

/* 백업 리스트 만들기 -------------------------------------------- */

changeFn = (inputImage,order)=>{
  const maxSize = 1024 * 1024 * 10;
  const file = inputImage.files[0];


  // 파일 선택 후 취소해서 파일이 없는 경우
  if(file == undefined){
    console.log("파일 선택 취소됨");

    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp);
    inputImage.remove();
    inputImage = temp;

    inputImage.addEventListener("change", e => {
      changeFn(e.target,order); //재귀함수 호출
    })
    return;
  }

  // 파일 크기가 최대크기 초과
  if(file.size > maxSize){
    alert("10MB 이하 이미지를 선택해주세요");

    if(backupInputList[order]==undefined || backupInputList[order].value == ''){
    inputImage.value="";
    return;
    }

    //이전 정상 선택 -> 다음 선택에서 이미지 크기 초과

    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp);
    inputImage.remove();
    inputImage = temp;

    //백업본 이벤트 다시 추가
    inputImage.addEventListener("change",e=>{
      changeFn(e.target,order);
    })
    return;
  }

  // 선택된 이미지 미리보기
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.addEventListener("load",e=>{
    const url = e.target.result;

    previewList[order].src = url;
    backupInputList[order] = inputImage.cloneNode(true);
  });
};




for(let i=0; i< inputImageList.length; i++){

  inputImageList[i].addEventListener("change", e =>{
    changeFn(e.target,i);
  });


  // x버튼 클릭시
  deleteImageList[i].addEventListener("click",()=>{
    previewList[i].src ="";
    inputImageList[i].value ="";
    backupInputList[i].value="";
  })
}


/**유효성 검사 -------------------------------------------- */
const form = document.querySelector("#boardFrm");

form.addEventListener("submit",e=>{

  const boardTitle = document.querySelector("[name='boardTitle']");
  const boardContent = document.querySelector("[name='boardContent']");

  if(boardTitle.value.trim().length == 0){
    alert("제목을 입력해 주세요");
    boardTitle.focus();
    e.preventDefault();
    return;
  }
  if(boardContent.value.trim().length==0){
    alert("내용을 입력해 주세요");
    boardContent.focus();
    e.preventDefault();
    return;
  }
})

// select 태그 게시판에 맞춰 보이게 하기 ---------------------------------------------------------


const topMenu = document.querySelector("#topMenu");
const bottomMenu2 = document.querySelector("#bottomMenu2");

bottomMenu.disabled = true;

if(topMenu!=null){
  topMenu.addEventListener("change", search);
};


function search() {
    //selectedIndex : 현재 선택된 옵션의 인덱스 반환
    const topMenuCode = topMenu.options[topMenu.selectedIndex].value;
    const bottomMenu = document.querySelector("#bottomMenu");
    bottomMenu.innerHTML = "";
  
    if(topMenu.value == "선택") return;

    fetch("/editBoard/bottomCode?topMenuCode="+topMenuCode)
    .then(resp => resp.json())
    .then(bottomList =>{
      console.log(bottomList);

      if(bottomList.length == 0){
        bottomMenu.disabled = true;
        return;
      }

      bottomMenu.disabled = false;

      for(let bottom of bottomList){
        const opt = document.createElement("option");
        opt.value = bottom['bottomMenuCode'];
        opt.innerText = bottom['bottomMenuName'];
        bottomMenu.append(opt);
      }
    })
}






















