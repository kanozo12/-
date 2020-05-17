<%@page import="domain.RentVO"%>
<%@page import="domain.BookRentVO"%>
<%@page import="java.util.List"%>
<%@page import="domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="./master/header.jsp"></jsp:include>

<div class="container">
	<h2>회원 리스트</h2>
	<%
		List<BookRentVO> blist = (List<BookRentVO>) request.getAttribute("blist");
		List<RentVO> rlist = (List<RentVO>) request.getAttribute("rlist");
	%>
	<table>
		<tr>
			<th>회원번호</th>
			<th>회원이름</th>
			<th>대여횟수</th>
		</tr>
		<%
			for(RentVO rvo : rlist) {
			request.setAttribute("rvo", rvo);
		%>
		<tr>
			<td>${rvo.custno }</td>
			<td>${rvo.custname }</td>
			<td>${rvo.cnt }</td>
		</tr>
		<%
			}
		%>
		
	</table>
	
	<h2>회원 리스트</h2>
	<table>
		<tr>
			<th>도서번호</th>
			<th>대여횟수</th>
		</tr>

		<%
			for (BookRentVO bvo : blist) {
				request.setAttribute("bvo", bvo);
		%>
		<tr>
			<td>${bvo.bookno }</td>
			<td>${bvo.cnt }</td>
		</tr>
		<%
			}
		%>
	</table>
</div>

<jsp:include page="./master/footer.jsp"></jsp:include>