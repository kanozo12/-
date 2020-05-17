<%@page import="java.util.List"%>
<%@page import="domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="./master/header.jsp"></jsp:include>

<div class="container">
	<h2>회원 리스트</h2>
	<%
		List<MemberVO> list = (List<MemberVO>) request.getAttribute("list");
	%>
	<table>
		<tr>
			<th>회원번호</th>
			<th>회원이름</th>
			<th>가입일자</th>
			<th>고객등급</th>
			<th>주소</th>
			<th>기능</th>
		</tr>

		<%
			for (MemberVO m : list) {
				request.setAttribute("m", m);
		%>
		<tr>
			<td><%=m.getCustno()%></td>
			<td><%=m.getCustname()%></td>
			<td><%=m.getJoindate()%></td>
			<td><%=m.getGrade()%></td>
			<td><%=m.getAddress()%></td>
			<td><a href="/update?custno=${m.custno}" class="btn">수정</a></td>
		</tr>
		<%
			}
		%>
	</table>
</div>

<jsp:include page="./master/footer.jsp"></jsp:include>