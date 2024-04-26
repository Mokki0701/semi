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


