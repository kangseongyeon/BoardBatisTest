package kr.or.ddit.board;

import java.util.List;
import java.util.Scanner;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.vo.BoardVO;

/*
위의 테이블을 작성하고 게시판을 관리하는
다음 기능들을 구현하시오.

기능 구현하기 ==> 전체 목록 출력, 새글작성, 수정, 삭제, 검색 
 
------------------------------------------------------------

게시판 테이블 구조 및 시퀀스

create table jdbc_board(
    board_no number not null,  -- 번호(자동증가)
    board_title varchar2(100) not null, -- 제목
    board_writer varchar2(50) not null, -- 작성자
    board_date date not null,   -- 작성날짜
    board_content clob,     -- 내용
    constraint pk_jdbc_board primary key (board_no)
);
create sequence board_seq
    start with 1   -- 시작번호
    increment by 1; -- 증가값
		
----------------------------------------------------------

// 시퀀스의 다음 값 구하기
//  시퀀스이름.nextVal
*/

public class BoardMain {
	private Scanner scan;
	private IBoardService boardService;

	public BoardMain() {
		boardService = BoardServiceImpl.getInstance();
		scan = new Scanner(System.in);
	}

	public static void main(String[] args) {
		BoardMain boardObj = new BoardMain();
		boardObj.start();
	}

	public void displayMenu() {
		System.out.println();
		System.out.println("----------------------------------------------");
		System.out.println(" ======= 메뉴 선택 ======= ");
		System.out.println(" 0. 작업 끝");
		System.out.println(" 1. 글 작성");
		System.out.println(" 2. 글 수정");
		System.out.println(" 3. 글 삭제");
		System.out.println(" 4. 글 검색");
		System.out.println(" 5. 전체 목록 출력");
		System.out.println("----------------------------------------------");
		System.out.print("메뉴 선택 >> ");
	}

	public void start() {
		int choice;
		do {
			displayMenu();
			choice = scan.nextInt();
			switch (choice) {
			case 0:
				System.out.println("작업을 마칩니다.");
				break;
			case 1:
				insertBoard();
				break;
			case 2:
				updateBoard();
				break;
			case 3:
				deleteBoard();
				break;
			case 4:
				searchBoard();
				break;
			case 5:
				displayAllBoard();
				break;
			default:
				break;
			}
		} while (choice != 0);
	}

	private void displayAllBoard() {
		System.out.println();
		System.out.println("----------------------------------------------");
		System.out.println("번호 \t 제목 \t 작성자 \t 작성일 \t\t 내용");
		System.out.println("----------------------------------------------");
		
		List<BoardVO> boardList = boardService.getTotalBoard();
		
		if (boardList.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
		} else {
			for (BoardVO bv : boardList) {
				System.out.println(bv.getBoardNo() + "\t" + bv.getBoardTitle() + "\t" + bv.getBoardWriter() + "\t" 
						+ bv.getRegDt() + "\t" + bv.getBoardContent());
			}
		}
		System.out.println("--------------------------------------------------");
		System.out.println("출력 끝...");
	}

	private void searchBoard() {
		scan.nextLine();
		
		System.out.println("검색할 게시글 제목를 입력하세요.");
		System.out.print("게시글 제목 >> ");
		String boardTitle = scan.nextLine().trim();
		
		System.out.print("작성자 >> ");
		String boardWriter = scan.nextLine().trim();
		
		System.out.print("게시글 내용 >> ");
		String boardContent = scan.nextLine().trim();
		
		/////////////////////////////////////////
		
		BoardVO bv = new BoardVO();
		bv.setBoardTitle(boardTitle);
		bv.setBoardWriter(boardWriter);
		bv.setBoardContent(boardContent);
		
		System.out.println();
		System.out.println("----------------------------------------------");
		System.out.println("번호 \t 제목 \t 작성자 \t 작성일 \t\t 내용");
		System.out.println("----------------------------------------------");
		
		List<BoardVO> boardList = boardService.searchBoard(bv);
		
		if (boardList.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
		} else {
			for (BoardVO bv2 : boardList) {
				System.out.println(bv2.getBoardNo() + "\t" + bv2.getBoardTitle() + "\t" + bv2.getBoardWriter() + "\t" 
						+ bv2.getRegDt() + "\t" + bv2.getBoardContent());
			}
		}
		System.out.println("--------------------------------------------------");
		System.out.println("출력 끝...");
		
	}

	private void deleteBoard() {
		System.out.println();
		System.out.println("삭제할 게시글 번호를 입력하세요.");
		System.out.print("게시글 번호 >> ");
		int boardNo = scan.nextInt();
		
		int cnt = boardService.removeBoard(boardNo);
		
		if (cnt > 0) {
			System.out.println("게시글 번호 " + boardNo + " 삭제 성공!!");
			displayAllBoard();
		} else {
			System.out.println("게시글 번호 " + boardNo + " 삭제 실패!!");
		}
	}

	private void updateBoard() {
		boolean isExist = false;
		int boardNo = 0;
		
		do {
			System.out.println();
			System.out.println("수정할 게시글 번호를 입력하세요.");
			System.out.print("게시글 번호 >> ");
			boardNo = scan.nextInt();
			
			isExist = boardService.checkBoard(boardNo);
			
			if (!isExist) {
				System.out.println("게시글 번호가 " + boardNo + "인 게시물은 존재하지 않습니다.");
				System.out.println("다시 입력해주세요.");
			}
		} while (!isExist);
		
		System.out.println();
		
		System.out.print("게시글 제목 >> ");
		String boardTitle = scan.next();
		
		System.out.print("작성자 >> ");
		String boardWriter = scan.next();
		scan.nextLine();

		System.out.print("내용 >> ");
		String boardContent = scan.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setBoardTitle(boardTitle);
		bv.setBoardWriter(boardWriter);
		bv.setBoardContent(boardContent);
		bv.setBoardNo(boardNo);
		
		int cnt = boardService.modifyBoard(bv);
		
		if (cnt > 0) {
			System.out.println("게시글 " + boardNo + "번 수정 성공!!");
			displayAllBoard();
		} else {
			System.out.println("게시글 " + boardNo + "번 수정 실패!!");
		}
	}

	private void insertBoard() {
		scan.nextLine();
		
		System.out.println();
		System.out.println("게시글을 등록합니다.");
		System.out.print("게시글 제목 >> ");
		String boardTitle = scan.next();

		System.out.print("작성자 >> ");
		String boardWriter = scan.next();
		scan.nextLine();

		System.out.print("내용 >> ");
		String boardContent = scan.nextLine();

		BoardVO bv = new BoardVO();
		bv.setBoardTitle(boardTitle);
		bv.setBoardWriter(boardWriter);
		bv.setBoardContent(boardContent);
		
		int cnt = boardService.registerBoard(bv);
		
		if (cnt > 0) {
			System.out.println("게시글  " + boardTitle + " 등록이 완료되었습니다.");
			displayAllBoard();
		} else {
			System.out.println("게시글 " + boardTitle + " 등록을 실패했습니다.");
		}
	}
}
