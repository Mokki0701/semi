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

// console.log(limit);
// console.log(cp);



if(updateBtn != null){

  http://localhost/board/5/18/221?limit=10&cp=2
updateBtn.addEventListener("click",()=>{

  location.href = `/editBoard/${topMenuCode}/${bottomMenuCode}/${boardNo}/update?limit=${limit}&cp=${cp}` 
})

}





