const commentAsc = document.querySelector("#commentAsc");
const commentDesc = document.querySelector("#commentDesc");
const commentLi = document.querySelector("#commentLast");
const commentFirst = document.querySelectorAll(".commentFirst");

function selectComment(value){

    
    for(let comment of commentFirst){
        comment.remove();
    }

    commentLi.innerText="";

    fetch("/comment/selectComment?boardNo=" + boardNo + "&checkComment=" + value)
    .then(res => res.json())
    .then(commentList => {


        const arr = ['commentNo', 'commentContent', 'commentWriteDate', 'commentDelFl', 'memberNickname'];


        for(let comment of commentList){

            console.log(comment['commentWriteDate']);

            if(comment['commentDelFl'] == 'Y'){
                const h1 = document.createElement("h1");
                h1.innerText="삭제된 댓글 입니다.";
                commentLi.appendChild(h1);
                continue;
            }

            const p = document.createElement("p");
            const span1 = document.createElement("span");
            const span2 = document.createElement("span");
            const span3 = document.createElement("span");

            span1.innerText=comment['memberNickname'];
            span2.innerText=comment['memberRank'];
            span3.innerText=comment['commentWriteDate'];

            const p2 = document.createElement("p");
            p2.innerHTML=comment['commentContent'];

            const div = document.createElement("div");
            const button = document.createElement("button");
            button.innerText="답글";

            p.appendChild(span1);
            p.appendChild(span2);
            p.appendChild(span3);

            div.appendChild(button);

            commentLi.appendChild(p);
            commentLi.appendChild(p2);
            commentLi.appendChild(div);

        }

    });


}

commentAsc.addEventListener("click", e=>{
    selectComment(1);
})

commentDesc.addEventListener("click", e=>{
    selectComment(2);
})
































