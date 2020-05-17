package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.MemberVO;
import service.MemberService;

public class UpdateController implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getMethod().equalsIgnoreCase("POST")) {
			return processUpdateService(req, res);
		} else if (req.getMethod().equalsIgnoreCase("GET")) {
			return processUpdateView(req, res);
		}

		return "redirect::/notfound";
	}

	private String processUpdateService(HttpServletRequest req, HttpServletResponse res) {
		int custno = Integer.parseInt(req.getParameter("custno"));
		String custname = req.getParameter("custname");
		Date joindate = Date.valueOf(req.getParameter("joindate"));
		System.out.println(joindate);
		String grade = req.getParameter("grade");
		String address = req.getParameter("address");

		MemberVO m = new MemberVO();
		m.setCustno(custno);
		m.setCustname(custname);
		m.setJoindate(joindate);
		m.setGrade(grade);
		m.setAddress(address);

		MemberService ms = MemberService.instance;
		int n = 0;
		try {
			n = ms.memberUpdate(m);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		if(n > 0) {
			return "redirect::/list";
		} else {
			return "update";
		}
	}
	
	private String processUpdateView(HttpServletRequest req, HttpServletResponse res) {
		int custno = Integer.parseInt(req.getParameter("custno"));
		MemberService ms = MemberService.instance;
		MemberVO m = ms.getUpdateMember(custno);
		req.setAttribute("m", m);
		return "update";
	}
}
