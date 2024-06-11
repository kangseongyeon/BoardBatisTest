package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.vo.BoardVO;

public interface IBoardService {
	/**
	 * BoardVO에 담겨진 회원정보를 등록하기 위한 메서드
	 * @param bv 회원정보를 담은 MemberVO 객체
	 * @return 성공하면 1, 실패하면 0 반환됨
	 */
	public int registerBoard (BoardVO bv);
	
	/** 
	 * BoardVO에 담겨진 회원정보를 수정하기 위한 메서드
	 * @param bv 회원정보를 담은 MemberVO 객체
	 * @return 성공하면 1, 실패하면 0 반환됨
	 */
	public int modifyBoard (BoardVO bv);
	
	/**
	 * 해당 게시물의 존재여부를 확인하기 위한 메서드
	 * @param boardNo 존재 여부 확인하기 위한 boardNo
	 * @return 해당 게시물이 존재하면 true, 존재하지 않으면 false 리턴
	 */
	public boolean checkBoard (int boardNo);
	
	/**
	 * 해당 게시물 정보를 삭제하기 위한 메서드
	 * @param boardNo 삭제하고자 하는 게시물 No
	 * @return 삭제 처리가 성공하면 1, 실패하면 0 반환
	 */
	public int removeBoard (int boardNo);
	
	
	/**
	 * 모든 게시물 정보를 가져오기 위한 메서드
	 * @return 모든 게시물정보를 담은 List 객체
	 */
	public List<BoardVO> getTotalBoard();
	
	/**
	 * 회원정보를 검색하기 위한 메서드
	 * @param bv 검색할 회원정보를 담은 BoardVO 객체
	 * @return 검색된 회원정보를 담은 List 객체
	 */
	public List<BoardVO> searchBoard(BoardVO bv);
}
