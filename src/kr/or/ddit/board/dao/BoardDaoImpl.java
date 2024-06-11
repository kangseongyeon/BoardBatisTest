package kr.or.ddit.board.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.MyBatisUtil;

public class BoardDaoImpl implements IBoardDao {
	
	private static IBoardDao boardDao = new BoardDaoImpl();
	
	public BoardDaoImpl() {
		
	}
	
	public static IBoardDao getInstance() {
		return boardDao;
	}
	

	@Override
	public int insertBoard(BoardVO bv) {

		SqlSession session = MyBatisUtil.getSqlSession();

		int cnt = 0;
		try {
			cnt = session.insert("board.insertBoard", bv);

			if (cnt > 0) {
				session.commit();
			}

		} catch (PersistenceException ex) {
			session.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return cnt;
	}

	@Override
	public int updateBoard(BoardVO bv) {
		SqlSession session = MyBatisUtil.getSqlSession();

		int cnt = 0;
		try {
			cnt = session.update("board.updateBoard", bv);

			if (cnt > 0) {
				session.commit();
			}

		} catch (PersistenceException ex) {
			session.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return cnt;
	}

	@Override
	public int deleteBoard(int boardNo) {
		SqlSession session = MyBatisUtil.getSqlSession();

		int cnt = 0;
		try {
			cnt = session.update("board.deleteBoard", boardNo);

			if (cnt > 0) {
				session.commit();
			}

		} catch (PersistenceException ex) {
			session.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return cnt;
	}

	@Override
	public boolean checkBoard(int boardNo) {
		boolean isExist = false;

		SqlSession session = MyBatisUtil.getSqlSession();

		try {
			int cnt = session.selectOne("board.checkBoard", boardNo);

			if (cnt > 0) {
				isExist = true;
			}

		} catch (PersistenceException ex) {
			session.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return isExist;
	}

	@Override
	public List<BoardVO> getAllBoard() {
		List<BoardVO> memList = new ArrayList<BoardVO>();

		SqlSession session = MyBatisUtil.getSqlSession(true);

		try {
			memList = session.selectList("board.selectAllBoard");
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return memList;
	}

	@Override
	public List<BoardVO> searchBoard(BoardVO bv) {
		List<BoardVO> memList = new ArrayList<BoardVO>();

		SqlSession session = MyBatisUtil.getSqlSession(true);

		try {
			memList = session.selectList("board.searchBoard", bv);
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return memList;
	}

}
