


let statusCheck = -1;

/* 회원 정보 수정 페이지 */
const info = document.querySelector("#info"); 


info.addEventListener("submit", e => {

  const memberNickname = document.querySelector("#memberNickname");
  const memberTel      = document.querySelector("#memberTel");
  const memberAddress  = document.querySelectorAll("[name='memberAddress']");
  const newPw = document.querySelector("#newPw"); 
  const newPwConfirm = document.querySelector("#newPwConfirm"); 

  

  // 닉네임 유효성 검사
  if(memberNickname.value.trim().length == 0){
    alert("닉네임을 입력해 주세요.");
    e.preventDefault(); 
    return;
  }

  // 정규식에 맞지 않으면
  let regExp = /^[가-힣\w\d]{2,10}$/;
  if( !regExp.test(memberNickname.value) ){
    alert("닉네임이 유효하지 않습니다");
    e.preventDefault(); 
    return;
  }

  // 전화 번호 유효성 검사
  if(memberTel.value.trim().length == 0){
    alert("전화번호를 입력해 주세요.");
    e.preventDefault(); 
    return;
  }

  // 정규식에 맞지 않으면
  regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
  if( !regExp.test(memberTel.value) ){
    alert("전화번호가 유효하지 않습니다");
    e.preventDefault(); 
    return;
  }
  

  // 주소 유효성 검사
  const addr0 = memberAddress[0].value.trim().length == 0; 
  const addr1 = memberAddress[1].value.trim().length == 0; 
  const addr2 = memberAddress[2].value.trim().length == 0; 
  const result1 = addr0 && addr1 && addr2; 
  const result2 = !(addr0 || addr1 || addr2); 

  if(  !(result1 || result2)  ){
    alert("주소를 모두 작성 또는 미작성 해주세요.")
    e.preventDefault(); 
    return;
  }


  //비밀번호변경
  let str; 

  if(newPw.value.trim().length > 0 || newPwConfirm.value.trim().length > 0 ){

    if( newPw.value.trim().length == 0 ) str = "새 비밀번호를 입력 해주세요";
    else if( newPwConfirm.value.trim().length == 0 ) str = "새 비밀번호 확인을 입력 해주세요";
    
    if(str != undefined){ 
      alert(str);
      e.preventDefault();
      return;
    }

    regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    if( !regExp.test(newPw.value) ){ 
      alert("새 비밀번호가 유효하지 않습니다");
      e.preventDefault();
      return;
    }
    

    if( newPw.value != newPwConfirm.value){
      alert("새 비밀번호가 일치하지 않습니다");
      e.preventDefault();
      return;
    }
  }
      

  document.querySelector("#statusCheck").value = statusCheck;


});




// ---------------------------------------------------------------------------------

/* 탈퇴 유효성 검사 */
const secession = document.querySelector("#secession");

if(secession != null){

  secession.addEventListener("submit", e => {
 
    const memberPw = document.querySelector("#memberPw");
    const agree = document.querySelector("#agree");

    if(memberPw.value.trim().length == 0){
      alert("비밀번호를 입력해 주세요");
      e.preventDefault();
      return;
    }
    if( !agree.checked ){ 
      alert("약관에 동의해주세요");
      e.preventDefault();
      return;
    }
    if( !confirm("정말 탈퇴 하시겠습니까?") ){ 
      alert("취소 되었습니다");
      e.preventDefault();
      return;
    }

  })


  
}










// ----------------------------------------------------------------------------------

/* 프로필 이미지 추가/변경/삭제 */

let backupInput;


const profileImg = document.querySelector("#profileImg"); 
let imageInput = document.querySelector("#imageInput"); 
const deleteImage = document.querySelector("#deleteImage"); 

const changeImageFn = e => {

  const maxSize =  1024 * 1024 * 5;

  console.log("e.target", e.target); 

  console.log("e.target.value", e.target.value); 
  console.log("e.target.files", e.target.files); 
  console.log("e.target.files[0]", e.target.files[0]); 

  const file = e.target.files[0];

  if(file == undefined){
    console.log("파일 선택 후 취소됨");

    const temp = backupInput.cloneNode(true);

    imageInput.after(backupInput);
    imageInput.remove();
    imageInput = backupInput;
    imageInput.addEventListener("change", changeImageFn);

    backupInput = temp;

    return;
  }
  
  if(file.size > maxSize){
    alert("5MB 이하의 이미지 파일을 선택해 주세요.");

    if(statusCheck == -1){
      imageInput.value = '';
    
    } else{ 
      const temp = backupInput.cloneNode(true);

      imageInput.after(backupInput);
      imageInput.remove();
      imageInput = backupInput;
      imageInput.addEventListener("change", changeImageFn);

      backupInput = temp;
    }
    return;
  }





  // ------------- 선택된 이미지 미리보기 ----------------
  const reader = new FileReader();

  reader.readAsDataURL(file); 
  reader.addEventListener("load", e => {

    const url = e.target.result; 
    profileImg.setAttribute("src", url);
    
    statusCheck = 1;
    backupInput = imageInput.cloneNode(true);

  });

}

imageInput.addEventListener("change", changeImageFn);

// ------------ x버튼 클릭 시 기본 이미지로 변경 ----------------

deleteImage.addEventListener("click", () => {

  profileImg.src = "/images/user.png";
  imageInput.value = '';
  backupInput = undefined; 
  statusCheck = 0;
});






//--------------------------------------------------------------회원탈퇴페이지로

var deleteBtn = document.querySelector(".delete-submit");

deleteBtn.addEventListener("click",function(){

  var url = "/myPage/delete";
  window.location.href = url;

});
