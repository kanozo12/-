package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.BookRentVO;
import domain.MemberVO;
import domain.RentVO;
import util.JDBCUtil;

public class MemberService {
	public static MemberService instance = new MemberService();

	private MemberService() {

	}

	public List<MemberVO> memberList() {
		Connection conn = JDBCUtil.getConnection();

		String sql = "SELECT custno, custname, joindate, decode(grade, 'A', 'VIP', 'B', '일반', 'C', '직원') grade, address FROM mem_tbl_book";
		ResultSet rs = null;
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberVO m = new MemberVO();
				m.setCustno(rs.getInt("custno"));
				m.setCustname(rs.getString("custname"));
				m.setJoindate(rs.getDate("joindate"));
				m.setGrade(rs.getString("grade"));
				m.setAddress(rs.getString("address"));

				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원정보를 가져오는중 오류 발생");
		} finally {
			JDBCUtil.close(rs);

			JDBCUtil.close(conn);
		}

		return list;
	}

	public int getMaxCustNo() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int no = 0;

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT MAX(custno) + 1 custno FROM mem_tbl_book");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				no = rs.getInt("custno"); // 회원번호 마지막 + 1
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(conn);
		}
		return no;
	}

	public int memberInsert(MemberVO m) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int n = 0;

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO mem_tbl_book VALUES(?, ?, ?, ? , ?)");
			pstmt.setInt(1, m.getCustno());
			pstmt.setString(2, m.getCustname());
			pstmt.setDate(3, m.getJoindate());
			pstmt.setString(4, m.getGrade());
			pstmt.setString(5, m.getAddress());

			n = pstmt.executeUpdate();

			if (n > 0) {
				conn.commit(); // 반드시 해줘야 들어감
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원가입중 오류가 발생했습니다.");
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(conn);
		}

		return n;
	}

	public MemberVO getUpdateMember(int custno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO m = null;

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(
					"SELECT custno, custname, joindate, grade, address FROM mem_tbl_book WHERE custno = ?");
			pstmt.setInt(1, custno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				m = new MemberVO();
				m.setCustno(rs.getInt("custno"));
				m.setCustname(rs.getString("custname"));
				m.setJoindate(rs.getDate("joindate"));
				m.setGrade(rs.getString("grade"));
				m.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(conn);
		}
		return m;
	}

	public List<RentVO> userRentList() {
		List<RentVO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT A.custno, A.custname, B.cnt " + 
					"FROM (SELECT custno, count(*) AS cnt FROM rent_tbl_book GROUP BY custno) B, mem_tbl_book A "+ 
					"WHERE A.custno = B.custno " + 
					"ORDER BY cnt DESC, A.custno");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RentVO vo = new RentVO();
				vo.setCustno(rs.getInt("custno"));
				vo.setCustname(rs.getString("custname"));
				vo.setCnt(rs.getInt("cnt"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(conn);
		}
		return list;
	}

	public List<BookRentVO> bookRentList() {
		List<BookRentVO> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(
					"SELECT bookno, COUNT(custno) AS cnt FROM rent_tbl_book GROUP BY bookno ORDER BY cnt DESC");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BookRentVO vo = new BookRentVO();
				vo.setBookno(rs.getInt("bookno"));
				vo.setCnt(rs.getInt("cnt"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(conn);
		}
		return list;
	}

	public int memberUpdate(MemberVO m) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int n = 0;

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(
					"UPDATE mem_tbl_book SET custname = ?, joindate = ?, grade = ?, address = ? WHERE custno = ?");
			pstmt.setInt(5, m.getCustno());
			pstmt.setString(1, m.getCustname());
			pstmt.setDate(2, m.getJoindate());
			pstmt.setString(3, m.getGrade());
			pstmt.setString(4, m.getAddress());

			n = pstmt.executeUpdate();

			if (n > 0) {
				conn.commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(conn);
		}
		return n;
	}
}
