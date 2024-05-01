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


const addContent = document.querySelector("#addComment"); 
const commentContent = document.querySelector("#commentContent"); 

addContent.addEventListener("click", e => {

    if (loginMemberNo == null) {
        alert("로그인 후 이용해");
        return;
    }

    if (commentContent.value.trim().length == 0) {
        alert("댓글 입력이나 하고 등록해!");
        commentContent.focus();
        return;
    }

    const data = {
        "commentContent": commentContent.value,
        "boardNo": boardNo,
        "memberNo": loginMemberNo 
    }

    fetch("/comment/enroll", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(resp => resp.text())
        .then(result => {

            if (result > 0) {
                alert("댓글이 등록 되었습니다.");
                commentContent.value = ""; // 작성한 댓글 내용 지우기

                selectComment(1); // 댓글 목록을 다시 조회해서 화면에 출력
            } else {
                alert("댓글 등록 실패");
            }


        })
        .catch(err => console.log(err));


})

const showInsertComment = (parentCommentNo, btn) => {

    // ** 답글 작성 textarea가 한 개만 열릴 수 있도록 만들기 **
    const temp = document.getElementsByClassName("commentInsertContent");

    if (temp.length > 0) { // 답글 작성 textara가 이미 화면에 존재하는 경우

        if (confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하시겠습니까?")) {
            temp[0].nextElementSibling.remove(); // 버튼 영역부터 삭제
            temp[0].remove(); // textara 삭제 (기준점은 마지막에 삭제해야 된다!)

        } else {
            return; // 함수를 종료시켜 답글이 생성되지 않게함.
        }
    }

    // 답글을 작성할 textarea 요소 생성
    const textarea = document.createElement("textarea");
    textarea.classList.add("commentInsertContent");

    // 답글 버튼의 부모의 뒤쪽에 textarea 추가
    // after(요소) : 뒤쪽에 추가
    btn.parentElement.after(textarea);


    // 답글 버튼 영역 + 등록/취소 버튼 생성 및 추가
    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");


    const insertBtn = document.createElement("button");
    insertBtn.innerText = "등록";
    insertBtn.setAttribute("onclick", "insertChildComment(" + parentCommentNo + ", this)");


    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "insertCancel(this)");

    // 답글 버튼 영역의 자식으로 등록/취소 버튼 추가
    commentBtnArea.append(insertBtn, cancelBtn);

    // 답글 버튼 영역을 화면에 추가된 textarea 뒤쪽에 추가
    textarea.after(commentBtnArea);



}



























