package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.dao.BoardDaoImpl;
import kr.or.ddit.board.dao.BoardDaoImplWithJDBC;
import kr.or.ddit.board.vo.BoardVO;

public class BoardServiceImpl implements IBoardService {
	private static IBoardService boardService = new BoardServiceImpl();
	
	private IBoardDao boardDao;
	
	private BoardServiceImpl() {
		boardDao = BoardDaoImpl.getInstance();
	}
	
	public static IBoardService getInstance() {
		return boardService;
	}
	
	@Override
	public int registerBoard(BoardVO bv) {
		int cnt = boardDao.insertBoard(bv);
		return cnt;
	}

	@Override
	public int modifyBoard(BoardVO bv) {
		int cnt = boardDao.updateBoard(bv);
		return cnt;
	}

	@Override
	public boolean checkBoard(int boardNo) {
		return boardDao.checkBoard(boardNo);
	}

	@Override
	public int removeBoard(int boardNo) {
		int cnt = boardDao.deleteBoard(boardNo);
		return cnt;
	}


	@Override
	public List<BoardVO> getTotalBoard() {
		List<BoardVO> boardList = boardDao.getAllBoard();
		return boardList;
	}

	@Override
	public List<BoardVO> searchBoard(BoardVO bv) {
		return boardDao.searchBoard(bv);
	}
	
}
