package kr.or.ddit.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.dsig.dom.DOMValidateContext;

import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.JDBCUtil3;

public class BoardDaoImplWithJDBC implements IBoardDao {
	
	private static IBoardDao boardDao = new BoardDaoImplWithJDBC();
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private BoardDaoImplWithJDBC() {
		
	}
	
	public static IBoardDao getInstance() {
		return boardDao;
	}
	
	@Override
	public int insertBoard(BoardVO bv) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();

			String sql = " INSERT INTO JDBC_BOARD (BOARD_NO, BOARD_TITLE, BOARD_WRITER, BOARD_DATE, BOARD_CONTENT)\r\n " + 
						 " VALUES (BOARD_SEQ.NEXTVAL, ? , ? , SYSDATE, ? ) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bv.getBoardTitle());
			pstmt.setString(2, bv.getBoardWriter());
			pstmt.setString(3, bv.getBoardContent());

			cnt = pstmt.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public int updateBoard(BoardVO bv) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			String sql = " UPDATE JDBC_BOARD\r\n" + 
						 " SET BOARD_TITLE = ?, BOARD_WRITER = ?, BOARD_CONTENT = ?\r\n " + 
						 " WHERE BOARD_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setNString(1, bv.getBoardTitle());
			pstmt.setString(2, bv.getBoardWriter());
			pstmt.setString(3, bv.getBoardContent());
			pstmt.setInt(4, bv.getBoardNo());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public boolean checkBoard(int boardNo) {
		boolean isExist = false;
		try {
			conn = JDBCUtil3.getConnection();

			String sql = " SELECT COUNT(*) AS CNT\r\n " + 
						 " FROM JDBC_BOARD\r\n " + 
						 " WHERE BOARD_NO = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			int cnt = 0;
			while (rs.next()) {
				cnt = rs.getInt(1);
				if (cnt > 0) {
					isExist = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return isExist;
	}

	@Override
	public int deleteBoard(int boardNo) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = " DELETE\r\n " + 
						 " FROM JDBC_BOARD\r\n " + 
						 " WHERE BOARD_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			cnt = pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	
	@Override
	public List<BoardVO> getAllBoard() {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		try {
			conn = JDBCUtil3.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(" SELECT * FROM JDBC_BOARD ");
			
			while (rs.next()) {
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String boardWriter = rs.getString("BOARD_WRITER");
				String boardContent = rs.getString("BOARD_CONTENT");
				LocalDate boardDt = rs.getTimestamp("BOARD_DATE").toLocalDateTime().toLocalDate();
				
				BoardVO bv = new BoardVO();
				bv.setBoardNo(boardNo);
				bv.setBoardTitle(boardTitle);
				bv.setBoardWriter(boardWriter);
				bv.setBoardContent(boardContent);
				bv.setRegDt(boardDt);
				
				boardList.add(bv);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return boardList;
	}

	@Override
	public List<BoardVO> searchBoard(BoardVO bv) {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = " select * from jdbc_board where 1=1 ";
			
			if (bv.getBoardTitle() != null && !bv.getBoardTitle().equals("") ) {
				sql += " and board_title = ? "; 
			}
			if (bv.getBoardWriter() != null && !bv.getBoardWriter().equals("") ) {
				sql += " and board_writer = ? "; 
			}
			if (bv.getBoardContent() != null && !bv.getBoardContent().equals("") ) {
				sql += " and board_content like '%' || ? || '%' "; 
			}
			
			pstmt = conn.prepareStatement(sql);
			
			int paramIndex = 1;
			if (bv.getBoardTitle() != null && !bv.getBoardTitle().equals("") ) {
				pstmt.setString(paramIndex++, bv.getBoardTitle());
			}
			if (bv.getBoardWriter() != null && !bv.getBoardWriter().equals("") ) {
				pstmt.setString(paramIndex++, bv.getBoardWriter());
			}
			if (bv.getBoardContent() != null && !bv.getBoardContent().equals("") ) {
				pstmt.setString(paramIndex++, bv.getBoardContent());
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String boardWriter = rs.getString("BOARD_WRITER");
				String boardContent = rs.getString("BOARD_CONTENT");
				LocalDate boardDt = rs.getTimestamp("BOARD_DATE").toLocalDateTime().toLocalDate();
				
				BoardVO bv2 = new BoardVO();
				bv2.setBoardNo(boardNo);
				bv2.setBoardTitle(boardTitle);
				bv2.setBoardWriter(boardWriter);
				bv2.setBoardContent(boardContent);
				bv2.setRegDt(boardDt);
				
				boardList.add(bv2);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return boardList;
	}
	
}
