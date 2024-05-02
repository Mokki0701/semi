const tagRankField = document.querySelector("#tagRankField");

function tagRank() {
    fetch("/tag/select", {
      method: "GET",
      headers: { "Content-Type": "application/json" }
    })
    .then(resp=> resp.json())
    .then(tagList =>{
        const tr = document.createElement("tr");

        for(let tag of tagList){
            const td = document.createElement("td");
            td.innerText = tag.tagName;

            tr.appendChild(td);
        }

        tagRankField.appendChild(tr);

    })
    .catch(error => console.error('Error:', error)); 
      
  }
  
  // index 페이지 로드 시 실행되는 코드
  document.addEventListener("DOMContentLoaded", function () {
    tagRank();
  });









