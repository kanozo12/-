package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BookRentVO;
import domain.RentVO;
import service.MemberService;

public class RentController implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		MemberService ms = MemberService.instance;
		
		List<RentVO> rlist = ms.userRentList();
		List<BookRentVO> blist = ms.bookRentList();
		
		req.setAttribute("rlist", rlist);
		req.setAttribute("blist", blist);
		
		return "rent";
	}
}
