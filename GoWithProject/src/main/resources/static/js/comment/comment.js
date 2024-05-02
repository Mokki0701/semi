const commentAsc = document.querySelector("#commentAsc");
const commentDesc = document.querySelector("#commentDesc");
const commentLi = document.querySelector("#commentLast");
const commentFirst = document.querySelectorAll(".commentFirst");

function selectComment(value){

    
    for(let comment of commentFirst){
        comment.remove();
    }

    commentLi.innerText="";

    fetch("/comment/selectComment?boardNo=" + boardNo + "&checkComment=" + value + "&commentNo")
    .then(res => res.json())
    .then(commentList => {

        const arr = ['commentNo', 'commentContent', 'commentWriteDate', 'commentDelFl', 'memberNickname'];


        for(let comment of commentList){

            const li = document.createElement("li");

 
            
            if(comment['commentDelFl'] == 'Y'){
                const h1 = document.createElement("h1");
                h1.innerText="삭제된 댓글 입니다.";
                commentLi.appendChild(h1);
            }

            else{

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
                button.setAttribute("onclick", `showInsertComment(${comment.commentNo}, this)`);
    
                
                p.appendChild(span1);
                p.appendChild(span2);
                p.appendChild(span3);
                
                div.appendChild(button);
    
                if (loginMemberNo != null && loginMemberNo == comment.memberNo) {
                    const deleteBtn = document.createElement("button");
                    const updateBtn = document.createElement("button");
    
                    deleteBtn.innerText = "삭제!";
                    updateBtn.innerText = "수정!";
    
                    deleteBtn.setAttribute("onclick", `deleteComment(${comment.commentNo})`);
                    updateBtn.setAttribute("onclick", `showUpdateComment(${comment.commentNo}, this)`);
    
                    div.append(updateBtn, deleteBtn);
                }
    
                li.appendChild(p);
                li.appendChild(p2);
                li.appendChild(div);

            }


            commentLi.appendChild(li);
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

    const temp = document.getElementsByClassName("commentInsertContent");

    if (temp.length > 0) { 

        if (confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하시겠습니까?")) {
            temp[0].nextElementSibling.remove(); 
            temp[0].remove(); 

        } else {
            return; 
        }
    }

    const textarea = document.createElement("textarea");
    textarea.classList.add("commentInsertContent");
    btn.parentElement.after(textarea);

    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");


    const insertBtn = document.createElement("button");
    insertBtn.innerText = "등록";
    insertBtn.setAttribute("onclick", "insertChildComment(" + parentCommentNo + ", this)");


    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "insertCancel(this)");

    commentBtnArea.append(insertBtn, cancelBtn);

    textarea.after(commentBtnArea);



}

const insertCancel = (cancelBtn) => {

    cancelBtn.parentElement.previousElementSibling.remove();

    cancelBtn.parentElement.remove();
}

const insertChildComment = (parentCommentNo, btn) => {

    const textarea = btn.parentElement.previousElementSibling;

    if (textarea.value.trim().length == 0) {
        alert("내용 작성 후 등록 버튼을 클릭해 주세요.");
        textarea.focus();
        return;
    }


    /*  */
    const data = {
        "commentContent": textarea.value,
        "boardNo": boardNo,
        "memberNo": loginMemberNo,  
        "parentCommentNo": parentCommentNo
    };

    fetch("/comment/enroll", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data) 
    })

        .then(response => response.text())
        .then(result => {

            if (result > 0) {
                alert("답글이 등록 되었습니다");
                selectComment(1); 

            } else {
                alert("답글 등록 실패");
            }

        })
        .catch(err => console.log(err));



}

const deleteComment = commentNo => {

    if (!confirm("삭제 하시겠습니까?")) return;

    fetch("/comment/delete", {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: commentNo
    })
        .then(resp => resp.text())
        .then(result => {

            if (result > 0) {
                alert("삭제 되었습니다");
                selectComment(1);

            } else {
                alert("삭제 실패");
            }

        })
        .catch(err => console.log(err));



}

let beforeCommentRow;

const showUpdateComment = (commentNo, btn) =>{

    const temp = document.querySelector(".update-textarea");

    if(temp != null){
        if(confirm("수정 중인 댓글이 있습니다. 현재 댓글을 수정 하시겠습니까?")){

            const commentRow = temp.parentElement;

            commentRow.after(beforeCommentRow); 
            commentRow.remove();

        }else{
            return;
        }
    }

    const commentRow = btn.closest("li");

    beforeCommentRow = commentRow.cloneNode(true);

    let beforeContent = commentRow.children[1].innerText;

    commentRow.innerHTML = "";

    const textarea = document.createElement("textarea");
    textarea.classList.add("update-textarea");
    textarea.value = beforeContent;

    commentRow.append(textarea);

    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");

    const updateBtn = document.createElement("button");
    const cancelBtn = document.createElement("button");

    updateBtn.innerText="수정";
    cancelBtn.innerText="취소";

    console.log(commentNo);

    updateBtn.setAttribute("onclick", `updateComment(${commentNo}, this)`);
    cancelBtn.setAttribute("onclick", "updateCancel(this)");

    commentBtnArea.append(updateBtn, cancelBtn);

    commentRow.append(commentBtnArea);
}

const updateCancel = (btn) => {

    if(confirm("취소 하시겠습니까?")){
      const commentRow = btn.closest("li"); 
      commentRow.after(beforeCommentRow); 
      commentRow.remove(); 
    }
  
  }

  const updateComment = (commentNo, btn) =>{

    const textarea = btn.parentElement.previousElementSibling;

    if(textarea.value.trim().length == 0){
        alert("내 용 을 입 력 해 ! ");
        textarea.focus();
        return;
    }

    const data = {
        "commentNo" : commentNo,
        "commentContent" : textarea.value
    }

    fetch("/comment/update",{
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(data)
    })
    .then(resp=> resp.text())
    .then(result=>{

        if(result > 0){
            alert("수정 성공!!!");
            selectComment(1);
        }else{
            alert("수정 실패...")
        }

    })
    .catch(err=>console.log(err));

}












