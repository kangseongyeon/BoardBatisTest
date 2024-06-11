package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.board.vo.BoardVO;

public interface IBoardDao {
	/**
	 * BoardVO에 담겨진 회원정보를 DB에 Insert 하기 위한 메서드
	 * @param bv 회원정보를 담은 MemberVO 객체
	 * @return DB 작업이 성공하면 1, 실패하면 0 반환됨
	 */
	public int insertBoard (BoardVO bv);
	
	/** 
	 * BoardVO에 담겨진 회원정보를 DB에 Update 하기 위한 메서드
	 * @param bv 회원정보를 담은 MemberVO 객체
	 * @return DB 작업이 성공하면 1, 실패하면 0 반환됨
	 */
	public int updateBoard (BoardVO bv);
	
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
	public int deleteBoard (int boardNo);
	
	
	/**
	 * DB에 존재하는 모든 게시물 정보를 가져오기 위한 메서드
	 * @return 모든 게시물정보를 담은 List 객체
	 */
	public List<BoardVO> getAllBoard();
	

	/**
	 * 회원정보를 검색하기 위한 메서드
	 * @param bv 검색할 회원정보를 담은 BoardVO 객체
	 * @return 검색된 회원정보를 담은 List 객체
	 */
	public List<BoardVO> searchBoard(BoardVO bv);
	
}
