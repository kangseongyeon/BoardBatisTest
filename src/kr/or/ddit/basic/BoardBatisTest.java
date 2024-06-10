package kr.or.ddit.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.or.ddit.board.vo.BoardVO;

public class BoardBatisTest {
	public static void main(String[] args) {
		// Mybatis를 이용하여 DB 작업을 처리하는 작업 순서
		// 1. myBatis의 환경설정 파일을 읽어와 필요한 객체를 생성한다
		
		// sql session 객체를 만들기 전에 SqlSessionFactory가 필요
		// SqlSessionFactory : sql session 객체를 만드는 공장
		SqlSessionFactory sqlSessionFactory = null;
		
		try {
			// 1-1 XML설정 파일 읽어오기
			Charset charset = Charset.forName("UTF-8");	// 설정파일의 인코딩 정보 설정 (한글처리를 위해서...)
			Resources.setCharset(charset);
			
			Reader rd = Resources.getResourceAsReader("config/mybatis-config.xml");
			
			// 1-2. 위에서 읽어온 Reader 객체를 이용하여 SqlSessionFactory 객체를 생성한다.
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(rd);
			
			// 스트림 닫기
			rd.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		// 2. 실행할 SQL문에 맞는 쿼리문을 호출하여 원하는 작업을 수행한다.

		// 2-1. insert 작업 연습
		System.out.println("insert 작업 시작...");
		
		// 1) 저장할 데이터를 VO에 담는다
		BoardVO bv = new BoardVO();
		bv.setBoardTitle("d001");
		bv.setBoardWriter("강성연");
		bv.setBoardContent("내용");
		
		// 2) SqlSession 객체를 이용하여 해당 쿼리문을 실행한다
		SqlSession session = sqlSessionFactory.openSession(false);	// autoCommit 여부 설정 => false 일 경우 commit 필수
		
		try {
			
			// 형식) insert("namespace값.id값", 파라미터객체)
			int cnt = session.insert("boardTest.insertBoard", bv);
			
			if (cnt > 0) {
				System.out.println("insert 작업 성공!");
				session.commit();	// 커밋
			} else {
				System.out.println("insert 작업 실패!!");
			}
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		} finally {
			session.close();	// 커넥션풀에 사용한 커넥션 반납하기
		}
		System.out.println("-----------------------------------------");
		
		// 2-2. update 연습
		System.out.println("update 객체 ");
		bv = new BoardVO();
		bv.setBoardTitle("d001");
		bv.setBoardWriter("강성연2");
		bv.setBoardContent("내용2");
		bv.setBoardNo(1);
		
		session = sqlSessionFactory.openSession(false);
		try {
			int cnt = session.update("boardTest.updateBoard", bv);

			if (cnt > 0) {
				System.out.println("update 작업 성공!");
				session.commit();
			} else {
				System.out.println("update 작업 실패!");
			}
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
		System.out.println("-----------------------------------------");
		
		
		// 2-3) delete 연습
		System.out.println("delete 작업 시작...");
		
		session = sqlSessionFactory.openSession();
		
		try {
			// boardNo 기준으로 함
			int cnt = session.delete("boardTest.deleteBoard", 1);
			
			if (cnt > 0) {
				System.out.println("delete 작업 성공!!");
				session.commit();
			} else {
				System.out.println("delete 작업 실패!!");
			}
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("-----------------------------------------");
		
		
		
		// 2-4) select 연습
		// 1) 응답의 결과가 여러개일 경우...
		System.out.println("select 연습 (결과가 여러 개일 경우...)");
		session = sqlSessionFactory.openSession(true);
		
		try {
			List<BoardVO> boardList = session.selectList("boardTest.selectAllBoard");
			
			for (BoardVO bv2 : boardList) {
				System.out.println("번호 : " + bv2.getBoardNo());
				System.out.println("제목 : " + bv2.getBoardTitle());
				System.out.println("작성자 : " + bv2.getBoardWriter());
				System.out.println("날짜 : " + bv2.getRegDt());
				System.out.println("내용 : " + bv2.getBoardContent());
				
				System.out.println("------------------------------------------------------------");
			}
			System.out.println("전체 게시글 정보 출력 끝...");
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		
		// 2) 응답의 결과가 1개일 경우...
		System.out.println("select 연습 (결과가 한 개일 경우...)");
		session = sqlSessionFactory.openSession(true);
		
		try {
			BoardVO bv3 = session.selectOne("boardTest.getBoard", "d001");
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
}
