package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.MemberVO;
import service.MemberService;

public class ListController implements Controller{
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<MemberVO> list = MemberService.instance.memberList();
		
		req.setAttribute("list", list);
		return "list";
	}
}
