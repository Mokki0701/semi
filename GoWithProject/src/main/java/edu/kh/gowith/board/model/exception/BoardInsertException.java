package edu.kh.gowith.board.model.exception;

public class BoardInsertException extends RuntimeException{

	public BoardInsertException() {
		super("게시글 삽입 중 예외 발생");
	}
	
	public BoardInsertException(String message) {
		super(message);
	}
	
	
}
