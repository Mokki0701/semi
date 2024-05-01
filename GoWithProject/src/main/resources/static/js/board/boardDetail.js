function getQueryParameter(url, name) {
  const queryString = url.split('?').slice(1).join('?');
  const queryParams = queryString.split(/[?&]/);

  for (const param of queryParams) {
      const [key, value] = param.split('=');
      if (key === name) {
          return value;
      }
  }
  return null; // 해당 매개변수가 없는 경우
}


const updateBtn = document.querySelector("#updateBtn");
const currentURL = window.location.href;
const limit = getQueryParameter(currentURL, "limit");
const cp = getQueryParameter(currentURL, "cp");



if(updateBtn != null){

  http://localhost/board/5/18/221?limit=10&cp=2
  updateBtn.addEventListener("click",()=>{

  location.href = `/editBoard/${topMenuCode}/${bottomMenuCode}/${boardNo}/update?limit=${limit}&cp=${cp}` 
})

}


/* 선택된 이미지 미리보기 */

//input 태그 
const inputImageList = document.getElementsByClassName("inputImg");
//삭제 버튼
const deleteImageList = document.getElementsByClassName("del-img");
//x버튼 눌러져 삭제된 이미지 순서 저장
const deleteOrder = new Set();

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

    previewsList[order].src = url;
    backupInputList[order] = inputImage.cloneNode(true);

    deleteOrder.delete(order);
  });
};




for(let i=0; i< inputImageList.length; i++){

  inputImageList[i].addEventListener("change", e =>{
    changeFn(e.target,i);
  });


  // x버튼 클릭시
  deleteImageList[i].addEventListener("click",()=>{

    if(previewsList[i].getAttribute("src") != null
      && previewsList[i].getAttribute("src")!=""){

        if(orderList.includes(i)){
          deleteOrder.add(i);
        }
      }
    previewsList[i].src ="";
    inputImageList[i].value ="";
    backupInputList[i].value= "";
  })
}


/**유효성 검사 -------------------------------------------- */
const form = document.querySelector("#boardFrm");

if(form != null){

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
    document.querySelector("[name='deleteOrder']").value
    = Array.from(deleteOrder);
    document.querySelector("[name='querystring']").value = location.search;
  })
  
}





