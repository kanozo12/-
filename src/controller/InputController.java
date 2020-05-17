package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.MemberVO;
import service.MemberService;

public class InputController implements Controller {

	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getMethod().equalsIgnoreCase("post")) {

			// post 회원가입
			return postProcess(req, res);
		} else if (req.getMethod().equalsIgnoreCase("get")) {
			// get 수정
			return getProcess(req, res);
		}
		return "notfount";
	}

	private String postProcess(HttpServletRequest req, HttpServletResponse res) {
		MemberService ms = MemberService.instance;
		int custno = Integer.parseInt(req.getParameter("custno"));
		String custname = req.getParameter("custname");
		Date joindate = Date.valueOf(req.getParameter("joindate"));
		System.out.println(joindate);
		String grade = req.getParameter("grade");
		String address = req.getParameter("address");
		String msg = "";
		boolean error = false;

		if (custname.length() > 3) {
			msg += "고객 이름은 3글자 이하여야합니다.<br>";
			error = true;
		}
		if (error) {
			req.setAttribute("msg", msg);
			return "input";
		}

		MemberVO m = new MemberVO();
		m.setCustno(custno);
		m.setCustname(custname);
		m.setJoindate(joindate);
		m.setGrade(grade);
		m.setAddress(address);

		int n = 0;
		try {
			n = ms.memberInsert(m);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (n > 0) {
			// 회원가입 성공
			return "redirect::/list";
		} else {
			// 회원가입 실패
			int no = ms.getMaxCustNo();
			req.setAttribute("no", no);
			return "input";
		}
	}
	
	private String getProcess(HttpServletRequest req, HttpServletResponse res) {
	
		MemberService ms = MemberService.instance;
		int no = ms.getMaxCustNo();
		req.setAttribute("no", no);
		return "input";
	}
}
